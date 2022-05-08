package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dao.*;
import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.dto.TimepartDTO;
import com.jwsystem.entity.college.CollegePO;
import com.jwsystem.entity.course.CoursepartPO;
import com.jwsystem.entity.course.RelaCourseStudentPO;
import com.jwsystem.entity.course.TimepartPO;
import com.jwsystem.entity.user.TeacherPO;
import com.jwsystem.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.util.CommonUtil;
import com.jwsystem.util.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.jwsystem.entity.course.RelaCourseStudentPO.SELECTED;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-04-29
 */
@Service
public class CoursepartServiceImpMP extends ServiceImpl<CoursepartDaoMP, CoursepartPO> implements CoursepartServiceMP {

    @Autowired
    CoursepartDaoMP coursepartDaoMP;
    @Autowired
    ReqCoursepartDaoMP reqCoursepartDaoMP;
    @Autowired
    CollegeDaoMP collegeDaoMP;
    @Autowired
    TransUtil transUtil;
    //@Autowired
   // ClassroomServiceMP classroomServiceMP;
   // @Autowired
    //TimepartServiceMP timepartServiceMP;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    TeacherDaoMP teacherDaoMP;
    @Autowired
    RelaCourseStudentDaoMP relaCourseStudentDaoMP;
    @Override
    public CoursepartDTO selectCoursepartByCourseId(int courseId) {
        return transUtil.CpPOtoCpDTO(coursepartDaoMP.selectById(courseId));
    }

    @Override
    public List<CoursepartDTO> getAllCoursepart() {
        List<CoursepartPO> coursepartPOList = coursepartDaoMP.selectList(null);
        List<CoursepartDTO> coursepartDTOList = new ArrayList<>();
        for (CoursepartPO coursepartPO: coursepartPOList) {
            CoursepartDTO c = transUtil.CpPOtoCpDTO(coursepartPO);
            coursepartDTOList.add(c);
        }
        return coursepartDTOList;
    }

    @Override
    public int insertCoursepart(CoursepartDTO coursepartDTO) {
        CoursepartPO coursepartPO = transUtil.CpDTOtoCpPO(coursepartDTO);
        coursepartDaoMP.insert(coursepartPO);
        return coursepartPO.getCourseId();
    }

    @Override
    public List<CoursepartDTO> selectCoursepartByCollege(String collegeName) {
        int collegeId = collegeDaoMP.selectOne(Wrappers.lambdaQuery(CollegePO.class)
                .eq(CollegePO::getName,collegeName)).getCollegeId();
        List<CoursepartPO> coursepartPOList = coursepartDaoMP.selectList(Wrappers.lambdaQuery(CoursepartPO.class)
                .eq(CoursepartPO::getCollegeId,collegeId));
        List<CoursepartDTO> coursepartDTOList = new ArrayList<>();
        for (CoursepartPO coursepartPO: coursepartPOList) {
            CoursepartDTO c = transUtil.CpPOtoCpDTO(coursepartPO);
            coursepartDTOList.add(c);
        }
        return coursepartDTOList;
    }

    @Override
    public List<CoursepartDTO> selectAllCoursepartByTeacherNum(String teacherNum) {
        List<CoursepartPO> coursepartPOList = coursepartDaoMP.selectList(Wrappers.lambdaQuery(CoursepartPO.class)
                .eq(CoursepartPO::getTeacherNum,teacherNum));
        List<CoursepartDTO> coursepartDTOList = new ArrayList<>();
        for (CoursepartPO c : coursepartPOList) {
            coursepartDTOList.add(transUtil.CpPOtoCpDTO(c));
        }
        return coursepartDTOList;
    }

    @Override
    public void solveExceededCourse() {
        //取出本学期开设的全部课程
        String year = commonUtil.getSchoolYear();
        String semester = commonUtil.getSemester();
        List<CoursepartPO> coursepartPOList = coursepartDaoMP.selectList(Wrappers.lambdaQuery(CoursepartPO.class)
                .eq(CoursepartPO::getYear,year)
                .eq(CoursepartPO::getSemester,semester));
        for (CoursepartPO c:
             coursepartPOList) {
            int capacity = Integer.parseInt(c.getCapacity());
            //取出这门课所有的选课记录
            List<RelaCourseStudentPO> courseStudentPOS = relaCourseStudentDaoMP.selectList(Wrappers.lambdaQuery(RelaCourseStudentPO.class)
                    .eq(RelaCourseStudentPO::getCourseId,c.getCourseId())
                    .eq(RelaCourseStudentPO::getStatus,SELECTED)); // TODO: 2022/5/4 根据课程id查出已选的relaCourseStudent对象List并返回
            int selected = courseStudentPOS.size();
            if(capacity < selected){
                //人数超出容量，踢人（被踢掉的人的选课记录会被删除）
                //根据relaCourseStudentPO里自带的学号，按年级进行排序（高年级优先）
                courseStudentPOS.sort(Comparator.comparing(RelaCourseStudentPO::getStudentNum));

                //将不同年级的申请分别存在不同的List里
                int currentGrade = Integer.parseInt(courseStudentPOS.get(0).getStudentNum().substring(0,2));
                int cnt = 0;
                int start = 0;
                Map<Integer,List<RelaCourseStudentPO>> map = new HashMap<>();
                for (RelaCourseStudentPO rela:
                     courseStudentPOS) {
                    int grade = Integer.parseInt(rela.getStudentNum().substring(0,2));
                    if(grade>currentGrade){
                        //说明已经进入了另一年级的选课信息部分，将之前一个年级的部分截取出来
                        List<RelaCourseStudentPO> temp = new ArrayList<>(courseStudentPOS.subList(start,cnt));
                        map.put(currentGrade,temp);
                        start = cnt;
                        currentGrade = grade;
                    }
                    cnt++;
                }

                //把最后一个年级的选课信息保存到map里
                List<RelaCourseStudentPO> tempRC = new ArrayList<>(courseStudentPOS.subList(start,cnt));
                map.put(currentGrade,tempRC);

                int total = 0;
                boolean flag = false;
                List<Integer> verified = new ArrayList<>();
                List<Integer> highToLow = new ArrayList<>(map.keySet());
                highToLow.sort(Comparator.comparing(Integer::intValue));

                for (Integer key:
                     highToLow) {
                    //将每个年级的申请顺序都打乱，保证之后删除多余部分时是随机删除的
                    Random rnd = new Random(233);
                    Collections.shuffle(map.get(key), rnd);
                    total+=map.get(key).size();

                    if(total<capacity){
                        //将可以全部选入的年级记录
                        verified.add(key);
                    }
                    else if (total == capacity){
                        //加入当前年级的选课申请后，恰好达到课程容量，则删除后面年级的选课申请
                        verified.add(key);
                        flag = true;
                        break;
                    }
                    else{
                        //加入当前年级的申请后超了，说明要在当前年级删除一部分
                        verified.add(key);
                        break;
                    }
                }

                //把要留下的数据从courseStudentPOS里删除，留在courseStudentPOS里的都是要从数据库删除掉的
                if(flag){
                    //恰好加入某个年级的申请后人数刚好，则将没有选中的年级的申请都留下
                    for(Integer key:
                    verified){
                        courseStudentPOS.removeAll(map.get(key));
                    }
                }
                else{
                    //需要对verified最后那个年级取一部分人
                    if(verified.size()==1){
                        //最高年级的选课就已经超了
                        int key = highToLow.get(0);
                        courseStudentPOS.removeAll(map.get(key).subList(0,capacity));
                    }
                    else {
                        List<Integer> temp = new ArrayList<>(verified.subList(0,verified.size()-1));
                        int count = 0;
                        for(Integer key:
                                temp){
                            count+=map.get(key).size();
                            courseStudentPOS.removeAll(map.get(key));
                        }
                        //最后一个年级只删除部分
                        courseStudentPOS.removeAll(map.get(verified.get(verified.size()-1)).subList(0,capacity-count));
                    }
                }

                //将被踢除的数据删去
                for(RelaCourseStudentPO courseStudentPOS1:
                courseStudentPOS){
                    relaCourseStudentDaoMP.deleteById(courseStudentPOS1.getId());
                }
            }
        }
    }

    @Override
    public void updateCourseInfo(String courseName, String courseNum, CoursepartPO coursepartPO) {
        coursepartDaoMP.update(null,Wrappers.lambdaUpdate(CoursepartPO.class)
                .eq(CoursepartPO::getCourseNum,courseNum)
                .eq(CoursepartPO::getCourseName,courseName)
                .set(CoursepartPO::getCourseNum,coursepartPO.getCourseNum())
                .set(CoursepartPO::getCourseName,coursepartPO.getCourseName())
                .set(CoursepartPO::getCourseInfo,coursepartPO.getCourseInfo())
                .set(CoursepartPO::getYear,coursepartPO.getYear())
                .set(CoursepartPO::getSemester,coursepartPO.getSemester()));
    }

    @Override
    public List<CoursepartPO> conditionSearch(String word) {
        //根据课程代码、课程名称模糊查询
        LambdaQueryWrapper<CoursepartPO> queryWrapper = new LambdaQueryWrapper<>();
        List<CoursepartPO> coursepartPOList = coursepartDaoMP.selectList(queryWrapper.and(c -> c.like(CoursepartPO::getCourseNum, word)
                .or().like(CoursepartPO::getCourseName, word)));
        //根据教师名称模糊查询
        LambdaQueryWrapper<TeacherPO> queryWrapperTea = new LambdaQueryWrapper<>();
        List<TeacherPO> teacherPOList = teacherDaoMP.selectList(queryWrapperTea.like(TeacherPO::getName,word));

        for (TeacherPO t:
             teacherPOList) {
            coursepartPOList.addAll(coursepartDaoMP.selectList(Wrappers.lambdaQuery(CoursepartPO.class)
                    .eq(CoursepartPO::getTeacherNum,t.getNumber())));
        }
        //过滤重复元素
        for (int i = 0; i < coursepartPOList.size(); i++) {
            for (int j = i + 1; j < coursepartPOList.size(); j++) {
                if (coursepartPOList.get(i).getCourseId() == coursepartPOList.get(j).getCourseId()) {
                    coursepartPOList.remove(j);
                    j--;
                }
            }
        }
        return coursepartPOList;
    }

    @Override
    public void updateCapacity(String capacity, int courseId) {
        coursepartDaoMP.update(null,Wrappers.lambdaUpdate(CoursepartPO.class)
                .eq(CoursepartPO::getCourseId,courseId)
                .set(CoursepartPO::getCapacity,capacity));
    }

    @Override
    public List<CoursepartPO> selectSameTypeCourse(String courseNum, String courseName) {
        return coursepartDaoMP.selectList(Wrappers.lambdaQuery(CoursepartPO.class)
                .eq(CoursepartPO::getCourseNum,courseNum)
                .eq(CoursepartPO::getCourseName,courseName));
    }

    @Override
    public List<CoursepartPO> selectByYearAndSemester(String schoolYear, String semester) {
        return coursepartDaoMP.selectList(Wrappers.lambdaQuery(CoursepartPO.class)
                .eq(CoursepartPO::getYear,schoolYear)
                .eq(CoursepartPO::getSemester,semester));
    }

    @Override
    public List<CoursepartPO> selectCoursepartByNameAndNum(String courseName, String courseNum) {
        return coursepartDaoMP.selectList(Wrappers.lambdaQuery(CoursepartPO.class)
                .eq(CoursepartPO::getCourseName,courseName)
                .eq(CoursepartPO::getCourseNum,courseNum));
    }


}
