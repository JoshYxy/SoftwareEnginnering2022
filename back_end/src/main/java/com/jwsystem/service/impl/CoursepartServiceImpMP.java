package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dao.CollegeDaoMP;
import com.jwsystem.dao.CoursepartDao;
import com.jwsystem.dao.ReqCoursepartDaoMP;
import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.entity.college.CollegePO;
import com.jwsystem.entity.course.CoursepartPO;
import com.jwsystem.dao.CoursepartDaoMP;
import com.jwsystem.service.CoursepartServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Override
    public CoursepartDTO selectCoursepartByCourseId(int courseId) {
        coursepartDaoMP.selectById(courseId);
        return null;
    }

    @Override
    public List<CoursepartDTO> getAllCoursepart() {
        coursepartDaoMP.selectList(null);
        return null;
    }

    @Override
    public int insertCoursepart(CoursepartDTO coursepartDTO) {
        return coursepartDaoMP.insert(coursepartPO);
    }

    @Override
    public CoursepartDTO selectReqCoursepartByRequestId(int requestId) {
        reqCoursepartDaoMP.selectById(requestId);
        return null;
    }

    @Override
    public int insertReqCoursepart(CoursepartDTO coursepartDTO) {
        reqCoursepartDaoMP.insert(coursepartPO);
        return 0;
    }

    @Override
    public List<CoursepartDTO> selectCoursepartByCollege(String collegeName) {
        int collegeId = collegeDaoMP.selectOne(Wrappers.lambdaQuery(CollegePO.class)
                .eq(CollegePO::getName,collegeName)).getCollegeId();
        coursepartDaoMP.selectList(Wrappers.lambdaQuery(CoursepartPO.class)
                .eq(CoursepartPO::getCollegeId,collegeId));
        return null;
    }

    @Override
    public List<CoursepartDTO> selectAllCoursepartByTeacherNum(String teacherNum) {
        coursepartDaoMP.selectList(Wrappers.lambdaQuery(CoursepartPO.class)
                .eq(CoursepartPO::getTeacherNum,teacherNum));
        return null;
    }
}
