package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.entity.College;
import com.jwsystem.entity.Major;
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

    //管理员查看全部的学院和专业信息
    @GetMapping("")
    public Result showInfo(){

        List<College> collegeList = eduService.getAll();

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
        eduDao.insertCollege(college);
        return Result.succ("增加成功");
    }

    //管理员增加新的专业
    @PostMapping("/major/new")
    public Result addMajor(@RequestBody Major major){
        eduDao.insertMajor(major);
        return Result.succ("增加成功");
    }

    //管理员删除已有学院
    @DeleteMapping("/college")
    public Result deleteCollege(@RequestBody College college){
        //先查询是否存在该学院
        boolean exist = eduDao.findCollege(college);
        //存在，进行删除，并且删除对应的所有专业
        if(exist){
            eduService.deleteCollege(college); //记得要删除对应的所有专业
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
        boolean exist = eduDao.findMajor(major);
        //存在，进行删除，并且删除对应的所有专业
        if(exist){
            eduDao.deleteMajor(major);
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
        boolean exist = eduDao.findCollege(college);
        //存在，进行修改
        if(exist){
            eduService.changeCollege(college);
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
        boolean exist = eduDao.findMajor(major);
        //存在，进行修改
        if(exist){
            eduService.changeMajor(major);
        }
        else{
            response.setStatus(NO_MAJOR);
            return Result.fail("修改失败");
        }
        return Result.succ("修改成功");
    }
}
