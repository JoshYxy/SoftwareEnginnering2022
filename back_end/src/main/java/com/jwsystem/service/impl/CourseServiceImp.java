package com.jwsystem.service.impl;

import com.jwsystem.dao.CoursepartDao;
import com.jwsystem.dao.RequestDao;
import com.jwsystem.dao.TimepartDao;
<<<<<<< Updated upstream
import com.jwsystem.entity.course.Coursepart;
import com.jwsystem.entity.course.Timepart;
=======
import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.dto.TimepartDTO;
>>>>>>> Stashed changes
import com.jwsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImp implements CourseService {
    @Autowired
    CoursepartDao coursepartDao;

    @Autowired
    TimepartDao timepartDao;

    @Autowired
    RequestDao requestDao;

    @Override
    public CoursepartDTO getCoursepartByCourseId(Integer courseId) {
        return coursepartDao.getCoursepartByCourseId(courseId);
    }

    @Override
    public List<CoursepartDTO> getAllCoursepart(){
        return coursepartDao.getCoursepartInfos();
    }

    @Override
    public List<TimepartDTO> getAllTimepartByCourseId(Integer courseId){
        return timepartDao.getAllTimepart(courseId);
    }

    @Override
    public int insertCoursepart(CoursepartDTO coursepartDTO){
        return coursepartDao.insertCoursepart(coursepartDTO);
    }

    @Override
    public boolean insertTimepart(TimepartDTO timepartDTO){
        //在插入的时候根据老师和教室判断有没有时间冲突
        //section '3 4 5' 得到数组 ['3'，'4'，'5']
        String[] sectionArray = timepartDTO.getSection().split("\\s+");
        //老师时间冲突
        //根据teachernum 和 weekday 找出一个section string 数组 比对有没有
        List<String> sectionListByTea = timepartDao.selectSectionByTea(timepartDTO.getTeacherNum(), timepartDTO.getWeekday());
        for(String sections : sectionListByTea) {
            for (String s : sectionArray) {
                int result = sections.indexOf(s);//在sections中c查找sectionArray中元素的位置，找不到则返回-1
                if (result > -1) return false;
            }
        }
        //教室时间冲突
        //根据roomnum 和 weekday 找出一个section string 数组 比对有没有
        List<String> sectionListByRoom = timepartDao.selectSectionByRoom(timepartDTO.getRoomNum(), timepartDTO.getWeekday(), timepartDTO.getBuilding());
        for(String sections : sectionListByRoom) {
            for (String s : sectionArray) {
                int result = sections.indexOf(s);//在sections中c查找sectionArray中元素的位置，找不到则返回-1
                if (result > -1) return false;
            }
        }
        //如果冲突 返回false，否则 插入 返回true
        timepartDao.insertTimepart(timepartDTO);
        return true;
    }

    @Override
    public int deleteCoursepartByCourseId(int courseId){
        return coursepartDao.deleteCoursepartByCourseId(courseId);
    }

    @Override
    public List<CoursepartDTO> getCoursepartByCollege(String collegeName) {
        return coursepartDao.getCoursepartByCollege(collegeName);
    }

    @Override
    public List<CoursepartDTO> getAllCoursepartByTeacherNum(String teacherNum) {
        return coursepartDao.getAllCoursepartByTeacherNum(teacherNum);
    }

    @Override
    public void insertReqCoursepart(CoursepartDTO coursepartDTO){
        coursepartDao.insertReqCoursepart(coursepartDTO);
    }

    @Override
    public void insertReqTimepart(TimepartDTO timepartDTO){
        timepartDao.insertReqTimepart(timepartDTO);
    }

    @Override
    public List<TimepartDTO> getAllTimeByRoom(String building, String roomNum){
        return timepartDao.getAllTimeByRoom(building,roomNum);
    }

    @Override
    public List<TimepartDTO> getAllTimeByTea(String number) {
        return timepartDao.getAllTimeByTeacherNum(number);
    }

    //根据楼在timepart和req_timepart里对应的所有的course_id和request_id
    @Override
    public List<Integer> getCourseIdByBuilding(String building) {
        return timepartDao.getCourseIdByBuilding(building);
    }
    @Override
    public List<Integer> getRequestIdByBuilding(String building) {
        return timepartDao.getRequestIdByBuilding(building);
    }

    @Override
    public List<Integer> getCourseIdByRoom(String building, String roomNum){
        return timepartDao.getCourseIdByRoom(building,roomNum);
    }

    @Override
    public List<Integer> getRequestIdByRoom(String building, String roomNum) {
        return timepartDao.getRequestIdByRoom(building,roomNum);
    }

    @Override
    public void deleteReqByRequestId(Integer c) {
        requestDao.deleteReqByRequestId(c);
    }

    public boolean examineTimes(String[] s) {
        //得到timepart 和 req_timepart中所有的section
        List<String> allSections = timepartDao.getAllSections();
        for(String section:s){
            for(String allSection:allSections) {
                if(allSection.contains(section)){
                    return true;
                }
            }
        }
        return false;
    }
}





















