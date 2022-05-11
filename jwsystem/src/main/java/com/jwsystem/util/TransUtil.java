package com.jwsystem.util;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.dto.MajorDTO;
import com.jwsystem.dto.RequestDTO;
import com.jwsystem.dto.TimepartDTO;
import com.jwsystem.entity.affair.BuildingPO;
import com.jwsystem.entity.affair.ClassroomPO;
import com.jwsystem.entity.college.CollegePO;
import com.jwsystem.entity.college.MajorPO;
import com.jwsystem.entity.course.CoursepartPO;
import com.jwsystem.entity.course.RelaCourseMajorPO;
import com.jwsystem.entity.course.RelaCourseStudentPO;
import com.jwsystem.entity.course.TimepartPO;
import com.jwsystem.entity.request.*;
import com.jwsystem.entity.user.StudentPO;
import com.jwsystem.entity.user.TeacherPO;
import com.jwsystem.service.MajorServiceMP;
import com.jwsystem.service.ReqRelaCourseMajorServiceMP;
import com.jwsystem.service.StudentServiceMP;
import com.jwsystem.service.TimepartServiceMP;
import com.jwsystem.service.impl.*;
import com.jwsystem.vo.ClassroomVO;
import com.jwsystem.vo.CourseVO;
import com.jwsystem.vo.ReqStudentVO;
import com.jwsystem.vo.UserVO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jwsystem.entity.course.CoursepartPO.GENERAL;
import static com.jwsystem.entity.course.RelaCourseStudentPO.SELECTED;

//将数据库内存储的课程信息转换为CourseVO对象的工具类
@Data
@Component
public class TransUtil {

    @Lazy
    @Autowired
    CollegeServiceImpMP collegeServiceImpMP;

    @Lazy
    @Autowired
    TeacherServiceImpMP teacherServiceImpMP;

    @Lazy
    @Autowired
    ClassroomServiceImpMP classroomServiceImpMP;

    @Lazy
    @Autowired
    BuildingServiceImpMP buildingServiceImpMP;

    @Lazy
    @Autowired
    StudentServiceMP studentServiceMP;

    @Lazy
    @Autowired
    MajorServiceMP majorServiceMP;

    @Lazy
    @Autowired
    CoursepartServiceImpMP coursepartServiceImpMP;

    @Lazy
    @Autowired
    TimepartServiceMP timepartServiceMP;

    @Lazy
    @Autowired
    RelaCourseStudentServiceImpMP relaCourseStudentServiceImpMP;

    @Lazy
    @Autowired
    RelaCourseMajorServiceImpMP relaCourseMajorServiceImpMP;

    @Lazy
    @Autowired
    ReqRelaCourseMajorServiceMP reqRelaCourseMajorServiceMP;


    //CoursepartDTO和TimepartDTO转换成CourseVO
    public CourseVO transToVO(CoursepartDTO coursePart, List<TimepartDTO> timepartDTOList,boolean needSelect,boolean isReq){

        CourseVO VO = new CourseVO();

        VO.setCourseId(coursePart.getRelationId());
        VO.setCourseName(coursePart.getCourseName());
        VO.setCourseNum(coursePart.getCourseNum());
        VO.setCollegeName(coursePart.getCollegeName());
        VO.setClassHours(coursePart.getClassHours());
        VO.setCredits(coursePart.getCredits());
        VO.setTeacherNum(coursePart.getTeacherNum());
        VO.setTeacherName(coursePart.getTeacherName());
        VO.setCourseInfo(coursePart.getCourseInfo());
        VO.setBuilding(timepartDTOList.get(0).getBuilding());
        VO.setRoomNum(timepartDTOList.get(0).getRoomNum());
        VO.setCapacity(coursePart.getCapacity());
        VO.setYear(coursePart.getYear());
        VO.setSemester(coursePart.getSemester());
        VO.setIsGeneral(coursePart.getIsGeneral());

        //设置时间部分
        int[][] time = new int[7][];
        for (TimepartDTO t:
                timepartDTOList) {
            int i = t.getWeekday();
            //转字符串为int数组
            String[] s = t.getSection().split(" ");
            time[i] = new int[s.length];
            for(int cnt=0; cnt<s.length; cnt++){
                time[i][cnt]=Integer.parseInt(s[cnt]);
            }
        }
        for(int i=0;i<7;i++){
            if(time[i]==null) time[i] = new int[0];
        }
        VO.setTimes(time);

        //needSelect为true，表示需要查询可选专业和已选人数
        if(needSelect){
            if(isReq && !coursePart.getIsGeneral().equals(GENERAL)){
                //是将申请的课程转换为VO，不需要查询已选人数，只需要找出可选专业即可
                List<ReqRelaCourseMajorPO> courseMajorPOS = reqRelaCourseMajorServiceMP.list(Wrappers.lambdaQuery(ReqRelaCourseMajorPO.class)
                        .eq(ReqRelaCourseMajorPO::getRequestId,coursePart.getRelationId()));
                //创建对应数量的二维数组
                String[][] majors = new String[courseMajorPOS.size()][2];
                int i=0;
                for (ReqRelaCourseMajorPO r:
                        courseMajorPOS) {
                    //根据majorid查询对应的学院名和专业名
                    MajorDTO m = majorServiceMP.selectMajorById(r.getMajorId());
                    majors[i][0]=m.getCollegeName();
                    majors[i][1]=m.getName();
                    i++;
                }
                VO.setMajors(majors);
            }
            else{
                //从relaCourseStudent表里查出已选人数（注意不要已修读的，即status为已选）
                //返回list就行
                List<RelaCourseStudentPO> courseStudentPOS = relaCourseStudentServiceImpMP.list(Wrappers.lambdaQuery(RelaCourseStudentPO.class)
                        .eq(RelaCourseStudentPO::getCourseId,coursePart.getRelationId())
                        .eq(RelaCourseStudentPO::getStatus,SELECTED));
                VO.setSelected(Integer.toString(courseStudentPOS.size()));

                //不是通选课，根据courseId查询可选专业
                if(!coursePart.getIsGeneral().equals(GENERAL)){
                    List<RelaCourseMajorPO> courseMajorPOS = relaCourseMajorServiceImpMP.list(Wrappers.lambdaQuery(RelaCourseMajorPO.class)
                            .eq(RelaCourseMajorPO::getCourseId,coursePart.getRelationId()));
                    //创建对应数量的二维数组
                    String[][] majors = new String[courseMajorPOS.size()][2];
                    int i=0;
                    for (RelaCourseMajorPO r:
                            courseMajorPOS) {
                        //根据majorid查询对应的学院名和专业名
                        MajorDTO m = majorServiceMP.selectMajorById(r.getMajorId());
                        majors[i][0]=m.getCollegeName();
                        majors[i][1]=m.getName();
                        i++;
                    }
                    VO.setMajors(majors);
                }
            }
        }

        return VO;
    }

    //根据课程id获得课程VO变量
    public CourseVO getCourseById(int courseId ,boolean needSelect){
        CoursepartDTO coursePart = CpPOtoCpDTO(coursepartServiceImpMP.getById(courseId));
        List<TimepartDTO> timepartDTOList = timepartServiceMP.selectAllTimepartByCourseId(courseId);
        return transToVO(coursePart,timepartDTOList,needSelect,false);
    }

    //CoursepartDTO转换成CoursepartPO
    public CoursepartPO CpDTOtoCpPO(CoursepartDTO coursepartDTO){

        if(coursepartDTO == null) return null;

        CoursepartPO coursepartPO = new CoursepartPO(
                coursepartDTO.getRelationId(),
                coursepartDTO.getCourseNum(),
                coursepartDTO.getCourseName(),
                coursepartDTO.getClassHours(),
                coursepartDTO.getCredits(),
                coursepartDTO.getCourseInfo(),
                coursepartDTO.getCapacity(),
                coursepartDTO.getYear(),
                coursepartDTO.getSemester(),
                coursepartDTO.getIsGeneral(),
                null,
                coursepartDTO.getTeacherNum()
        );

        //根据学院名获得collegeid，放入CoursepartPO对象中

        CollegePO collegePO = collegeServiceImpMP.selectCollegeByName(coursepartDTO.getCollegeName());
        Integer collegeId = collegePO.getCollegeId();// TODO: 2022/4/30 根据学院名查询学院id ;
        coursepartPO.setCollegeId(collegeId);

        return coursepartPO;
    }

    //CoursepartDTO转换成ReqCoursepartPO
    public ReqCoursepartPO CpDTOtoReqCpPO(CoursepartDTO coursepartDTO){

        if(coursepartDTO == null) return null;

        ReqCoursepartPO reqCoursepartPO = new ReqCoursepartPO(
                coursepartDTO.getRelationId(),
                coursepartDTO.getCourseNum(),
                coursepartDTO.getCourseName(),
                coursepartDTO.getClassHours(),
                coursepartDTO.getCredits(),
                coursepartDTO.getCourseInfo(),
                coursepartDTO.getCapacity(),
                coursepartDTO.getIsGeneral(),
                coursepartDTO.getYear(),
                coursepartDTO.getSemester(),
                null,
                coursepartDTO.getTeacherNum()
        );

        //根据学院名获得collegeid，放入ReqCoursepartPO对象中

        CollegePO collegePO = collegeServiceImpMP.selectCollegeByName(coursepartDTO.getCollegeName());
        Integer collegeId = collegePO.getCollegeId();// TODO: 2022/4/30 根据学院名查询学院id ;
        reqCoursepartPO.setCollegeId(collegeId);

        return reqCoursepartPO;
    }

    //CoursepartPO转换成CoursepartDTO
    public CoursepartDTO CpPOtoCpDTO(CoursepartPO coursepartPO){

        if(coursepartPO == null) return null;

        CoursepartDTO coursepartDTO = new CoursepartDTO(
                coursepartPO.getCourseId(),
                coursepartPO.getCourseName(),
                coursepartPO.getCourseNum(),
                coursepartPO.getClassHours(),
                coursepartPO.getCredits(),
                coursepartPO.getCourseInfo(),
                null,
                coursepartPO.getTeacherNum(),
                null,
                coursepartPO.getCapacity(),
                coursepartPO.getYear(),
                coursepartPO.getSemester(),
                coursepartPO.getIsGeneral()
        );

        //根据学院id查询学院名字并且插入
        CollegePO collegePO = collegeServiceImpMP.getById(coursepartPO.getCollegeId());
        String collegeName = collegePO.getName();// TODO: 2022/4/30 根据学院id查询学院名;
        coursepartDTO.setCollegeName(collegeName);

        //根据老师工号查询老师姓名并且插入
        UserVO tea = teacherServiceImpMP.selectUserByNumber(coursepartPO.getTeacherNum());
        String teacherName = tea.getName();// TODO: 2022/4/30 根据老师工号查询老师名;
        coursepartDTO.setTeacherName(teacherName);

        return coursepartDTO;
    }

    //ReqCoursepartPO转换成CoursepartDTO
    public CoursepartDTO ReqCpPOtoCpDTO(ReqCoursepartPO reqCoursepartPO){

        if(reqCoursepartPO == null) return null;

        CoursepartDTO coursepartDTO = new CoursepartDTO(
                reqCoursepartPO.getRequestId(),
                reqCoursepartPO.getCourseName(),
                reqCoursepartPO.getCourseNum(),
                reqCoursepartPO.getClassHours(),
                reqCoursepartPO.getCredits(),
                reqCoursepartPO.getCourseInfo(),
                null,
                reqCoursepartPO.getTeacherNum(),
                null,
                reqCoursepartPO.getCapacity(),
                reqCoursepartPO.getYear(),
                reqCoursepartPO.getSemester(),
                reqCoursepartPO.getIsGeneral()
        );

        //根据学院id查询学院名字并且插入
        CollegePO collegePO = collegeServiceImpMP.getById(reqCoursepartPO.getCollegeId());
        String collegeName = collegePO.getName();// TODO: 2022/4/30 根据学院id查询学院名;
        coursepartDTO.setCollegeName(collegeName);

        //根据老师工号查询老师姓名并且插入
        UserVO tea = teacherServiceImpMP.selectUserByNumber(reqCoursepartPO.getTeacherNum());
        String teacherName = tea.getName();// TODO: 2022/4/30 根据老师工号查询老师名;
        coursepartDTO.setTeacherName(teacherName);

        return coursepartDTO;
    }

    //TimepartDTO转换成TimepartPO
    public TimepartPO TpDTOtoTpPO(TimepartDTO timepartDTO){

        if(timepartDTO == null) return null;

        TimepartPO timepartPO = new TimepartPO(
                null,
                timepartDTO.getRelationId(),
                timepartDTO.getTeacherNum(),
                timepartDTO.getWeekday(),
                timepartDTO.getSection(),
                null
        );

        //根据楼名缩写和教室号查到教室id，并且插入
        BuildingPO buildingPO = buildingServiceImpMP.selectByAbbrName(timepartDTO.getBuilding());
        ClassroomPO classroomPO = classroomServiceImpMP.selectByRoomNum(buildingPO.getId(),timepartDTO.getRoomNum());
        Integer roomId = classroomPO.getRoomId();// TODO: 2022/4/30 根据楼简称和教室号查教室id;
        timepartPO.setRoomId(roomId);

        return timepartPO;
    }

    //TimepartDTO转换成ReqTimepartPO
    public ReqTimepartPO TpDTOtoReqTpPO(TimepartDTO timepartDTO){

        if(timepartDTO == null) return null;

        ReqTimepartPO reqTimepartPO = new ReqTimepartPO(
                null,
                timepartDTO.getRelationId(),
                timepartDTO.getTeacherNum(),
                timepartDTO.getWeekday(),
                timepartDTO.getSection(),
                null
        );

        //根据楼名缩写和教室号查到教室id，并且插入
        BuildingPO buildingPO = buildingServiceImpMP.selectByAbbrName(timepartDTO.getBuilding());
        ClassroomPO classroomPO = classroomServiceImpMP.selectByRoomNum(buildingPO.getId(),timepartDTO.getRoomNum());
        Integer roomId = classroomPO.getRoomId();// TODO: 2022/4/30 根据楼简称和教室号查教室id;
        reqTimepartPO.setRoomId(roomId);

        return reqTimepartPO;
    }

    //TimepartPO转换成TimepartDTO
    public TimepartDTO TpPOtoTpDTO(TimepartPO timepartPO){

        if(timepartPO == null) return null;

        TimepartDTO timepartDTO = new TimepartDTO(
                timepartPO.getCourseId(),
                timepartPO.getTeacherNum(),
                null,
                null,
                timepartPO.getWeekday(),
                timepartPO.getSection()
        );

        //根据roomid查出building（简称）和roomNum，然后插入
        ClassroomPO classroomPO = classroomServiceImpMP.getById(timepartPO.getRoomId());
        BuildingPO buildingPO = buildingServiceImpMP.getById(classroomPO.getBuildingId());
        String building = buildingPO.getAbbrName();// TODO: 2022/4/30 根据roomid查出building简写;
        String roomNum = classroomPO.getRoomNum();// TODO: 2022/4/30 根据roomid查出roomNum;
        timepartDTO.setBuilding(building);
        timepartDTO.setRoomNum(roomNum);

        return timepartDTO;
    }

    //ReqTimepartPO转换成TimepartDTO
    public TimepartDTO ReqTpPOtoTpDTO(ReqTimepartPO reqTimepartPO){

        if(reqTimepartPO == null) return null;

        TimepartDTO timepartDTO = new TimepartDTO(
                reqTimepartPO.getRequestId(),
                reqTimepartPO.getTeacherNum(),
                null,
                null,
                reqTimepartPO.getWeekday(),
                reqTimepartPO.getSection()
        );

        //根据roomid查出building（简称）和roomNum，然后插入
        ClassroomPO classroomPO = classroomServiceImpMP.getById(reqTimepartPO.getRoomId());
        BuildingPO buildingPO = buildingServiceImpMP.getById(classroomPO.getBuildingId());
        String building = buildingPO.getAbbrName();// TODO: 2022/4/30 根据roomid查出building简写;
        String roomNum = classroomPO.getRoomNum();// TODO: 2022/4/30 根据roomid查出roomNum;
        timepartDTO.setBuilding(building);
        timepartDTO.setRoomNum(roomNum);

        return timepartDTO;
    }


    //MajorDTO转MajorPO
    public MajorPO MajorDTOtoPO(MajorDTO majorDTO){

        if(majorDTO == null) return null;

        MajorPO  majorPO = new MajorPO(
                majorDTO.getMajorId(),
                majorDTO.getName(),
                null
        );

        Integer collegeId;
        if(majorDTO.getCollegeName()==null){
            MajorPO majorPO2 = majorServiceMP.getById(majorDTO.getMajorId());
            collegeId = majorPO2.getCollegeId();
        }
        else{
            //根据collegeName查询collegeId并且插入
            CollegePO collegePO = collegeServiceImpMP.selectCollegeByName(majorDTO.getCollegeName());
             collegeId = collegePO.getCollegeId();// TODO: 2022/4/30 根据学院名查询学院id ;
        }

        majorPO.setCollegeId(collegeId);

       return majorPO;
    }

    //MajorPO转MajorDTO
    public MajorDTO MajorPOtoDTO(MajorPO majorPO){

        if(majorPO == null) return null;

        MajorDTO majorDTO = new MajorDTO(
                majorPO.getMajorId(),
                majorPO.getName(),
                null
        );

        //根据collegeId查到collegeName然后插入
        CollegePO collegePO = collegeServiceImpMP.getById(majorPO.getCollegeId());
        String collegeName = collegePO.getName();// TODO: 2022/4/30 根据学院id查询学院名;
        majorDTO.setCollegeName(collegeName);

        return majorDTO;
    }

    //RequestDTO转ReqTeacherPO
    public ReqTeacherPO RequestDTOtoPO(RequestDTO requestDTO){

        if(requestDTO == null) return null;

        ReqTeacherPO reqTeacherPO = new ReqTeacherPO(
                requestDTO.getRequestId(),
                requestDTO.getType(),
                requestDTO.getCourseId(),
                requestDTO.getTeacherNum(),
                requestDTO.isExamined(),
                requestDTO.isPassed(),
                null
        );

        //根据楼名缩写和教室号查到教室id，并且插入
        BuildingPO buildingPO = buildingServiceImpMP.selectByAbbrName(requestDTO.getBuilding());
        ClassroomPO classroomPO = classroomServiceImpMP.selectByRoomNum(buildingPO.getId(),requestDTO.getRoomNum());
        Integer roomId = classroomPO.getRoomId();// TODO: 2022/4/30 根据楼简称和教室号查教室id;
        reqTeacherPO.setRoomId(roomId);

        return reqTeacherPO;
    }

    //ReqTeacherPO转RequestDTO
    public RequestDTO ReqTeacherPOtoDTO(ReqTeacherPO reqTeacherPO){

        if(reqTeacherPO == null) return null;

        RequestDTO requestDTO = new RequestDTO(
                reqTeacherPO.getRequestId(),
                reqTeacherPO.getType(),
                reqTeacherPO.getCourseId(),
                reqTeacherPO.getTeacherNum(),
                null,
                null,
                reqTeacherPO.getExamined(),
                reqTeacherPO.getPassed()
        );

        //根据roomid查出building（简称）和roomNum，然后插入
        ClassroomPO classroomPO = classroomServiceImpMP.getById(reqTeacherPO.getRoomId());
        BuildingPO buildingPO = buildingServiceImpMP.getById(classroomPO.getBuildingId());
        String building = buildingPO.getAbbrName();// TODO: 2022/4/30 根据roomid查出building简写;
        String roomNum = classroomPO.getRoomNum();// TODO: 2022/4/30 根据roomid查出roomNum;
        requestDTO.setBuilding(building);
        requestDTO.setRoomNum(roomNum);

        return requestDTO;
    }

    //StudentPO转UserVO
    public UserVO StudentPOtoUserVO(StudentPO studentPO){

        if(studentPO == null) return null;

        UserVO userVO = new UserVO(
                studentPO.getRole(),
                studentPO.getNumber(),
                studentPO.getId(),
                studentPO.getName(),
                studentPO.getPassword(),
                studentPO.getPhone(),
                studentPO.getEmail(),
                studentPO.getStatus(),
                null,
                null
        );
        String major = majorServiceMP.getById(studentPO.getMajorId()).getName();
        String college = collegeServiceImpMP.getById(studentPO.getCollegeId()).getName();
        userVO.setMajor(major);
        userVO.setCollege(college);

        return userVO;
    }

    //UserVO转StudentPO
    public StudentPO UserVOtoStudentPO(UserVO userVO){

        if(userVO == null) return null;

        StudentPO studentPO = new StudentPO(
                userVO.getRole(),
                userVO.getNumber(),
                userVO.getId(),
                userVO.getName(),
                userVO.getPassword(),
                userVO.getPhone(),
                userVO.getEmail(),
                userVO.getStatus(),
                null,
                null
        );
        Integer majorId = majorServiceMP.selectMajorByName(userVO.getMajor()).getMajorId();
        Integer collegeId = collegeServiceImpMP.selectCollegeByName(userVO.getCollege()).getCollegeId();
        studentPO.setMajorId(majorId);
        studentPO.setCollegeId(collegeId);

        return studentPO;
    }

    //TeacherPO转UserVO
    public UserVO TeacherPOtoUserVO(TeacherPO teacherPO){

        if(teacherPO == null) return null;

        UserVO userVO = new UserVO(
                teacherPO.getRole(),
                teacherPO.getNumber(),
                teacherPO.getId(),
                teacherPO.getName(),
                teacherPO.getPassword(),
                teacherPO.getPhone(),
                teacherPO.getEmail(),
                teacherPO.getStatus(),
                null,
                null
        );
        String major = majorServiceMP.getById(teacherPO.getMajorId()).getName();
        String college = collegeServiceImpMP.getById(teacherPO.getCollegeId()).getName();
        userVO.setMajor(major);
        userVO.setCollege(college);

        return userVO;
    }

    //UserVO转teacherPO
    public TeacherPO UserVOtoTeacherPO(UserVO userVO){

        if(userVO == null) return null;

        TeacherPO teacherPO = new TeacherPO(
                userVO.getRole(),
                userVO.getNumber(),
                userVO.getId(),
                userVO.getName(),
                userVO.getPassword(),
                userVO.getPhone(),
                userVO.getEmail(),
                userVO.getStatus(),
                null,
                null
        );
        Integer majorId = majorServiceMP.selectMajorByName(userVO.getMajor()).getMajorId();
        Integer collegeId = collegeServiceImpMP.selectCollegeByName(userVO.getCollege()).getCollegeId();
        teacherPO.setMajorId(majorId);
        teacherPO.setCollegeId(collegeId);

        return teacherPO;
    }

    //ClassroomVO转ClassroomPO
    public ClassroomPO ClassroomVOtoPO(ClassroomVO classroomVO){

        if(classroomVO == null) return null;

        ClassroomPO classroomPO = new ClassroomPO(
                classroomVO.getRoomId(),
                null,
                classroomVO.getRoomNum(),
                classroomVO.getCapacity()
        );

        //取出buildingId
        BuildingPO buildingPO = buildingServiceImpMP.selectByAbbrName(classroomVO.getBuilding());
        Integer buildingId = buildingPO.getId();
        classroomPO.setBuildingId(buildingId);

        return classroomPO;
    }

    //ClassroomPO转VO
    public ClassroomVO ClassroomPOtoVO(ClassroomPO classroomPO){

        if(classroomPO == null) return null;

        ClassroomVO classroomVO = new ClassroomVO(
                classroomPO.getRoomId(),
                null,
                classroomPO.getRoomNum(),
                classroomPO.getCapacity()
        );

        BuildingPO buildingPO = buildingServiceImpMP.getById(classroomPO.getBuildingId());
        String building = buildingPO.getAbbrName();
        classroomVO.setBuilding(building);

        return classroomVO;
    }

    public ReqStudentPO ReqStuVOtoPO(ReqStudentVO reqStudentVO,String num) {
        return new ReqStudentPO(
                reqStudentVO.getRequestId(),
                reqStudentVO.getCourseVO().getCourseId(),
                num,
                reqStudentVO.getApplyReason(),
                reqStudentVO.isDealt()?1:0,
                reqStudentVO.isApproved()?1:0
        );
    }

    public ReqStudentVO ReqStuPOtoVO(ReqStudentPO reqStudentPO){
        return new ReqStudentVO(
                reqStudentPO.getId(),
                reqStudentPO.getReason(),
                getCourseById(reqStudentPO.getCourseId(),true),
                StudentPOtoUserVO(studentServiceMP.getById(reqStudentPO.getStudentNum())),
                reqStudentPO.getDealt() == 1,
                reqStudentPO.getApproved() == 1
        );
    }
}
