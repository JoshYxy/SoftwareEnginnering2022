package com.jwsystem.util;



import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.dto.MajorDTO;
import com.jwsystem.dto.RequestDTO;
import com.jwsystem.entity.affair.BuildingPO;
import com.jwsystem.entity.affair.ClassroomPO;
import com.jwsystem.entity.college.CollegePO;
import com.jwsystem.entity.college.MajorPO;
import com.jwsystem.entity.course.CoursepartPO;
import com.jwsystem.entity.course.TimepartPO;
import com.jwsystem.entity.request.ReqCoursepartPO;
import com.jwsystem.entity.request.ReqTeacherPO;
import com.jwsystem.entity.request.ReqTimepartPO;
import com.jwsystem.service.impl.BuildingServiceImpMP;
import com.jwsystem.service.impl.ClassroomServiceImpMP;
import com.jwsystem.service.impl.CollegeServiceImpMP;
import com.jwsystem.service.impl.TeacherServiceImpMP;
import com.jwsystem.vo.CourseVO;
import com.jwsystem.dto.TimepartDTO;
import com.jwsystem.vo.UserVO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//将数据库内存储的课程信息转换为CourseVO对象的工具类
@Data
@Component
public class TransUtil {

    @Autowired
    CollegeServiceImpMP collegeServiceImpMP;

    @Autowired
    TeacherServiceImpMP teacherServiceImpMP;

    @Autowired
    ClassroomServiceImpMP classroomServiceImpMP;

    @Autowired
    BuildingServiceImpMP buildingServiceImpMP;

    //CoursepartDTO和TimepartDTO转换成CourseVO
    public CourseVO transToVO(CoursepartDTO coursePart, List<TimepartDTO> timepartDTOList){

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
        return VO;
    }

    //CoursepartDTO转换成CoursepartPO
    public CoursepartPO CpDTOtoCpPO(CoursepartDTO coursepartDTO){

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

        ReqCoursepartPO reqCoursepartPO = new ReqCoursepartPO(
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

        //根据学院名获得collegeid，放入ReqCoursepartPO对象中

        CollegePO collegePO = collegeServiceImpMP.selectCollegeByName(coursepartDTO.getCollegeName());
        Integer collegeId = collegePO.getCollegeId();// TODO: 2022/4/30 根据学院名查询学院id ;
        reqCoursepartPO.setCollegeId(collegeId);

        return reqCoursepartPO;
    }

    //CoursepartPO转换成CoursepartDTO
    public CoursepartDTO CpPOtoCpDTO(CoursepartPO coursepartPO){

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
        String collegeName = xxx.(coursepartPO.getCollegeId())// TODO: 2022/4/30 根据学院id查询学院名;
        coursepartDTO.setCollegeName(collegeName);

        //根据老师工号查询老师姓名并且插入
        UserVO tea = teacherServiceImpMP.selectUserByNumber(coursepartPO.getTeacherNum());
        String teacherName = tea.getName();// TODO: 2022/4/30 根据老师工号查询老师名;
        coursepartDTO.setTeacherName(teacherName);

        return coursepartDTO;
    }

    //ReqCoursepartPO转换成CoursepartDTO
    public CoursepartDTO ReqCpPOtoCpDTO(ReqCoursepartPO reqCoursepartPO){

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
        String collegeName = xxx.(reqCoursepartPO.getCollegeId())// TODO: 2022/4/30 根据学院id查询学院名;
        coursepartDTO.setCollegeName(collegeName);

        //根据老师工号查询老师姓名并且插入
        UserVO tea = teacherServiceImpMP.selectUserByNumber(reqCoursepartPO.getTeacherNum());
        String teacherName = tea.getName();// TODO: 2022/4/30 根据老师工号查询老师名;
        coursepartDTO.setTeacherName(teacherName);

        return coursepartDTO;
    }

    //TimepartDTO转换成TimepartPO
    public TimepartPO TpDTOtoTpPO(TimepartDTO timepartDTO){

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

        TimepartDTO timepartDTO = new TimepartDTO(
                timepartPO.getCourseId(),
                timepartPO.getTeacherNum(),
                null,
                null,
                timepartPO.getWeekday(),
                timepartPO.getSection()
        );

        //根据roomid查出building（简称）和roomNum，然后插入
        String building = xxx.(timepartPO.getRoomId())// TODO: 2022/4/30 根据roomid查出building简写; 
        String roomNum = xxx.(timepartPO.getRoomId())// TODO: 2022/4/30 根据roomid查出roomNum;
        timepartDTO.setBuilding(building);
        timepartDTO.setRoomNum(roomNum);

        return timepartDTO;
    }

    //ReqTimepartPO转换成TimepartDTO
    public TimepartDTO ReqTpPOtoTpDTO(ReqTimepartPO reqTimepartPO){

        TimepartDTO timepartDTO = new TimepartDTO(
                reqTimepartPO.getRequestId(),
                reqTimepartPO.getTeacherNum(),
                null,
                null,
                reqTimepartPO.getWeekday(),
                reqTimepartPO.getSection()
        );

        //根据roomid查出building（简称）和roomNum，然后插入
        String building = xxx.(reqTimepartPO.getRoomId())// TODO: 2022/4/30 根据roomid查出building简写;
        String roomNum = xxx.(reqTimepartPO.getRoomId())// TODO: 2022/4/30 根据roomid查出roomNum;
        timepartDTO.setBuilding(building);
        timepartDTO.setRoomNum(roomNum);

        return timepartDTO;
    }


    //MajorDTO转MajorPO
    public MajorPO MajorDTOtoPO(MajorDTO majorDTO){

        MajorPO  majorPO = new MajorPO(
                majorDTO.getMajorId(),
                majorDTO.getName(),
                null
        );
        
        //根据collegeName查询collegeId并且插入
        CollegePO collegePO = collegeServiceImpMP.selectCollegeByName(majorDTO.getCollegeName());
        Integer collegeId = collegePO.getCollegeId();// TODO: 2022/4/30 根据学院名查询学院id ;
        majorPO.setCollegeId(collegeId);

       return majorPO;
    }

    //MajorPO转MajorDTO
    public MajorDTO MajorPOtoDTO(MajorPO majorPO){

        MajorDTO majorDTO = new MajorDTO(
                majorPO.getMajorId(),
                majorPO.getName(),
                null
        );

        //根据collegeId查到collegeName然后插入
        String collegeName = xxx.(majorPO.getCollegeId())// TODO: 2022/4/30 根据collegeId查到collegeName;
        majorDTO.setCollegeName(collegeName);

        return majorDTO;
    }

    //RequestDTO转ReqTeacherPO
    public ReqTeacherPO RequestDTOtoPO(RequestDTO requestDTO){

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
        String building = xxx.(reqTeacherPO.getRoomId())// TODO: 2022/4/30 根据roomid查出building简写;
        String roomNum = xxx.(reqTeacherPO.getRoomId())// TODO: 2022/4/30 根据roomid查出roomNum;
        requestDTO.setBuilding(building);
        requestDTO.setRoomNum(roomNum);

        return requestDTO;
    }

}
