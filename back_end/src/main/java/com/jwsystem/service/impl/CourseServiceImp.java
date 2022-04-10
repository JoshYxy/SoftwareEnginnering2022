package com.jwsystem.service.impl;

import com.jwsystem.dao.CoursepartDao;
import com.jwsystem.dao.TimepartDao;
import com.jwsystem.entity.Coursepart;
import com.jwsystem.entity.Timepart;
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

    @Override
    public List<Coursepart> getAllCoursepart(){
        return coursepartDao.getCoursepartInfos();
    }

    @Override
    public List<Timepart> getAllTimepartByCourseId(Integer courseId){
        return timepartDao.getAllTimepart(courseId);
    }

    @Override
    public int insertCoursepart(Coursepart coursepart){
        return coursepartDao.insertCoursepart(coursepart);
    }

    @Override
    public boolean insertTimepart(Timepart timepart){
        //在插入的时候根据老师和教室判断有没有时间冲突
        //section '3 4 5' 得到数组 ['3'，'4'，'5']
        String[] sectionArray = timepart.getSection().split("\\s+");
        //老师时间冲突
        //根据teachernum 和 weekday 找出一个section string 数组 比对有没有
        List<String> sectionListByTea = timepartDao.selectSectionByTea(timepart.getTeacherNum(),timepart.getWeekday());
        for(String sections : sectionListByTea) {
            for (String s : sectionArray) {
                int result = sections.indexOf(s);//在sections中c查找sectionArray中元素的位置，找不到则返回-1
                if (result > -1) return false;
            }
        }
        //教室时间冲突
        //根据roomnum 和 weekday 找出一个section string 数组 比对有没有
        List<String> sectionListByRoom = timepartDao.selectSectionByRoom(timepart.getRoomNum(),timepart.getWeekday());
        for(String sections : sectionListByRoom) {
            for (String s : sectionArray) {
                int result = sections.indexOf(s);//在sections中c查找sectionArray中元素的位置，找不到则返回-1
                if (result > -1) return false;
            }
        }
        //如果冲突 返回false，否则 插入 返回true
        timepartDao.insertTimepart(timepart);
        return true;
    }

    @Override
    public int deleteCoursepartByCourseId(int courseId){
        return coursepartDao.deleteCoursepartByCourseId(courseId);
    }

    @Override
    public List<Coursepart> getCoursepartByCollege(String collegeName) {
        return coursepartDao.getCoursepartByCollege(collegeName);
    }

    @Override
    public List<Coursepart> getAllCoursepartByTeacherNum(String teacherNum) {
        return coursepartDao.getAllCoursepartByTeacherNum(teacherNum);
    }

    @Override
    public void insertReqCoursepart(Coursepart coursepart){
        coursepartDao.insertReqCoursepart(coursepart);
    }

    @Override
    public void insertReqTimepart(Timepart timepart){
        timepartDao.insertReqTimepart(timepart);
    }

    @Override
    public List<Timepart> getAllTimeByRoom(String building,String roomNum){
        return timepartDao.getAllTimeByRoom(building,roomNum);
    }
}





















