package com.jwsystem.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jwsystem.dao.CollegeDaoMP;
import com.jwsystem.dao.CoursepartDao;
import com.jwsystem.dao.ReqCoursepartDaoMP;
import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.entity.college.CollegePO;
import com.jwsystem.entity.course.CoursepartPO;
import com.jwsystem.dao.CoursepartDaoMP;
import com.jwsystem.entity.request.ReqCoursepartPO;
import com.jwsystem.service.CoursepartServiceMP;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwsystem.util.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    TransUtil transUtil;
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
        return coursepartDaoMP.insert(coursepartPO);
    }

    @Override
    public CoursepartDTO selectReqCoursepartByRequestId(int requestId) {
        reqCoursepartDaoMP.selectById(requestId);
        return null;
    }

    @Override
    public int insertReqCoursepart(CoursepartDTO coursepartDTO) {
        ReqCoursepartPO reqcoursepartPO = transUtil.CpDTOtoCpPO(coursepartDTO);
        reqCoursepartDaoMP.insert(coursepartPO);
        return 0;
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
}
