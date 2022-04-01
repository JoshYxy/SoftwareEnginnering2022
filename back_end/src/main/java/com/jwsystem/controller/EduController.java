package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.entity.College;
import com.jwsystem.entity.CollegeVO;
import com.jwsystem.entity.Major;
import com.jwsystem.service.impl.EduServiceImp;
import com.jwsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/edu")
public class EduController extends MainController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    EduServiceImp eduServiceImp;

    //管理员查看全部的学院和专业信息
    @GetMapping("")
    public Result showInfo(){
        List<CollegeVO> collegeList = eduServiceImp.getEduInfo();
        /*
        取出全部学院和专业信息，然后放在result的data里返回
        List<College>
        每个college对象里有一个List<Major>
         */
        return Result.succ("查询成功",collegeList);
    }

    //管理员增加新的学院
    @PostMapping("/college/new")
    public Result addCollege(@RequestBody College college){
        if(eduServiceImp.insertCollege(college)){
            return Result.succ("增加成功");
        }else
            return Result.fail("该学院已存在，添加失败");

    }

    //管理员增加新的专业
    @PostMapping("/major/new")
    public Result addMajor(@RequestBody Major major){       //这里major里包含专业名和学院名两个String
        if(eduServiceImp.insertMajor(major)) {
            return Result.succ("增加成功");
        }else
            return Result.fail("该专业已存在，添加失败");
    }

    //管理员删除已有学院
    @DeleteMapping("/college")
    public Result deleteCollege(@RequestBody College college){
        //先查询是否存在该学院
        boolean exist = eduServiceImp.findCollegeByName(college);
        //存在，进行删除，并且删除对应的所有专业
        if(exist){
            eduServiceImp.deleteCollege(college); //记得要删除对应的所有专业 ✔
        }
        else{
            response.setStatus(NO_COLLEGE);
            return Result.fail("删除失败");
        }
        return Result.succ("删除成功");
    }

    //管理员删除已有专业
    @DeleteMapping("/major")
    public Result deleteMajor(@RequestBody Major major){
        //先查询是否存在该专业
        boolean exist = eduServiceImp.findMajorByName(major);
        //存在，进行删除，并且删除对应的所有专业
        if(exist){
            eduServiceImp.deleteMajor(major);
        }
        else{
            response.setStatus(NO_MAJOR);
            return Result.fail("删除失败");
        }
        return Result.succ("删除成功");
    }

    //管理员修改已有学院
    @PostMapping("/college")
    public Result changeCollege(@RequestBody College college){
        //先查询是否存在该学院
        boolean exist = eduServiceImp.findCollegeById(college);
        //存在，进行修改
        if(exist){
            eduServiceImp.updateCollege(college);
        }
        else{
            response.setStatus(NO_COLLEGE);
            return Result.fail("修改失败");
        }
        return Result.succ("修改成功");
    }

    //管理员修改已有专业
    @PostMapping("/major")
    public Result changeMajor(@RequestBody Major major){
        //先查询是否存在该专业
        boolean exist = eduServiceImp.findMajorById(major);
        //存在，进行修改
        if(exist){
            eduServiceImp.updateMajor(major);
        }
        else{
            response.setStatus(NO_MAJOR);
            return Result.fail("修改失败");
        }
        return Result.succ("修改成功");
    }
}
