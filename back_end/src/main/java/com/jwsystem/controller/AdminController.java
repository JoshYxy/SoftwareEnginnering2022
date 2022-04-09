package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.entity.CollegeVO;
import com.jwsystem.entity.User;
import com.jwsystem.service.impl.EduServiceImp;
import com.jwsystem.service.impl.StuServiceImp;
import com.jwsystem.service.impl.TeaServiceImp;
import com.jwsystem.entity.*;
import com.jwsystem.util.CSVUtils;
import com.jwsystem.util.JwtUtils;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.jwsystem.entity.User.*;

@RestController
@RequestMapping("/admin")
public class AdminController extends MainController{
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CSVUtils csvUtils;

    @Autowired
    private EduServiceImp eduServiceImp;

    @Autowired
    private TeaServiceImp teaServiceImp;

    @Autowired
    private StuServiceImp stuServiceImp;

//    @Autowired
//    private CourseService courseService;
//
//    @Autowired
//    private CourseRequestService courseRequestService;
//
//    @Autowired
//    private AdminService adminService;




    /*
            用户信息管理部分
     */



        //管理员查看全部用户信息
    @GetMapping("/users")
    public Result getAll(){
        List<User> users = teaServiceImp.getAllUserInfos();
        List<User> students = stuServiceImp.getAllUserInfos();
        users.addAll(students);
        return Result.succ("查询成功",users);
    }

    //管理员批量导入用户信息
    @PostMapping("/users")
    public Result insertByCSV(@RequestParam("file") MultipartFile multipartFile) {
        //接收CSV文件
        System.out.println("[文件类型] - " + multipartFile.getContentType());
        System.out.println("[文件名称] - " + multipartFile.getOriginalFilename());
        System.out.println("[文件大小] - " + multipartFile.getSize());
        //保存
        if (!multipartFile.getContentType().equals("text/csv")) {
            //文件类型不匹配，接收文件错误
            response.setStatus(WRONG_FILE);
            return Result.fail("文件格式错误");
        }

        System.out.println("接收文件成功");

        //从CSV文件批量获取User对象
        List<User> users = CSVUtils.getUserByCsv(multipartFile);
        if(users == null){
            //csv文件为空
            response.setStatus(WRONG_RES);
            return Result.fail("csv文件读取失败！请检查文件格式或文件是否为空！");
        }

        //循环完成批量插入
        //加入如果中途插入失败了，跳过错过的该条，并且返回错误总数
        int cnt =0;
        for (User temp : users) {
            Result res = register(temp);
            String s = res.getMsg();
            if(!s.equals("录入信息成功")) cnt++;
            System.out.println(s);
        }
        if(cnt!=0){
            response.setStatus(WRONG_RES);
            String s = "批量导入信息完成，有" + cnt +"条数据导入失败";
            return Result.fail(s,cnt);
        }
        return Result.succ("批量导入信息成功");
    }

    //管理员单条录入信息
    @PostMapping("/new")
    public Result register(@RequestBody User tempUser){
        //增加数据格式检查功能
        System.out.println("进入了register...");
        String status = tempUser.getStatus();
        if(!status.equals(GRADUATED) && !status.equals(QUIT) && !status.equals(STUDYING) && !status.equals(WORKING)){
            response.setStatus(WRONG_DATA);
            return Result.fail("状态设置不符合规定");
        }
        if(tempUser.getRole().equals("student")){
            //学生
            if(stuServiceImp.selectStuById(tempUser.getId()) != null){
                response.setStatus(CONFLICT_ID);
                return Result.fail("该身份证号已注册！");
            }

            if(stuServiceImp.getUserByNumber(tempUser.getNumber()) != null){
                response.setStatus(CONFLICT_NUMBER);
                return Result.fail("该学号已注册！");
            }

            if(!stuServiceImp.legalNumber(tempUser.getNumber())){
                response.setStatus(WRONG_DATA);
                return Result.fail("学号需为6位整数！");
            }

            if(!stuServiceImp.findUserMajor(tempUser.getMajor())){
                response.setStatus(NO_MAJOR);
                return Result.fail("专业不存在！");
            }

            if(!eduServiceImp.judgeMajorAndCollege(tempUser.getMajor(),tempUser.getCollege())){
                response.setStatus(NO_COLLEGE);
                return Result.fail("无法找到专业对应学院！");
            }

            stuServiceImp.insertUser(tempUser);//增加相应的学院和专业信息的方法
        } else if(tempUser.getRole().equals("teacher")){
            //老师
            if(teaServiceImp.selectTeaById(tempUser.getId()) != null){
                response.setStatus(CONFLICT_ID);
                return Result.fail("该身份证号已注册！");
            }

            if(teaServiceImp.getUserByNumber(tempUser.getNumber()) != null){
                response.setStatus(CONFLICT_NUMBER);
                return Result.fail("该工号已注册！");
            }

            if(!teaServiceImp.legalNumber(tempUser.getNumber())){
                response.setStatus(WRONG_DATA);
                return Result.fail("工号需为8位整数！");
            }

            if(!teaServiceImp.findUserMajor(tempUser.getMajor())){
                response.setStatus(NO_MAJOR);
                return Result.fail("专业不存在！");
            }

            if(!eduServiceImp.judgeMajorAndCollege(tempUser.getMajor(),tempUser.getCollege())){
                response.setStatus(NO_COLLEGE);
                return Result.fail("无法找到专业对应学院！");
            }

            teaServiceImp.insertUser(tempUser);//增加相应的学院和专业信息的方法
        }
        else{
            response.setStatus(WRONG_DATA);
            return Result.fail("用户角色无效");
        }
        return Result.succ("录入信息成功",tempUser);
    }

    //管理员修改用户信息（包括状态）
    //加入身份证重复的判断
    //加入学院专业是否存在的检测
    @PostMapping("/user/info")
    public Result changeUserInfo(@RequestBody User tempUser){
        //管理员可以修改用户除学工号以外的所有信息
        String status = tempUser.getStatus();
        if(!status.equals(GRADUATED) && !status.equals(QUIT) && !status.equals(STUDYING) && !status.equals(WORKING)){
            response.setStatus(WRONG_DATA);
            return Result.fail("状态设置不符合规定");
        }
        if(tempUser.getRole().equals("student")){
            //学生
            Student stuInDB = stuServiceImp.selectStuByNum(tempUser.getNumber());
            String idInDB = stuInDB.getId();
            if(!idInDB.equals(tempUser.getId())){
                //身份证被修改，检查是否重复
                Student temp1 = stuServiceImp.selectStuById(tempUser.getId());
                Teacher temp2 = teaServiceImp.selectTeaById(tempUser.getId());
                if(temp1!=null || temp2!=null){
                    response.setStatus(CONFLICT_ID);
                    return Result.fail("修改后的身份证号已被注册！",tempUser.getId());
                }
            }
            if(!stuServiceImp.findUserMajor(tempUser.getMajor())){
                response.setStatus(NO_MAJOR);
                return Result.fail("专业不存在！");
            }

            if(!eduServiceImp.judgeMajorAndCollege(tempUser.getMajor(),tempUser.getCollege())){
                response.setStatus(NO_COLLEGE);
                return Result.fail("无法找到专业对应学院！");
            }
            stuServiceImp.updateStuInfoByAdmin(tempUser);
        } else if(tempUser.getRole().equals("teacher")){
            //老师
            Teacher teaInDB = teaServiceImp.selectTeaByNum(tempUser.getNumber());
            String idInDB = teaInDB.getId();
            if(!idInDB.equals(tempUser.getId())){
                //身份证被修改，检查是否重复
                Student temp1 = stuServiceImp.selectStuById(tempUser.getId());
                Teacher temp2 = teaServiceImp.selectTeaById(tempUser.getId());
                if(temp1!=null || temp2!=null){
                    response.setStatus(CONFLICT_ID);
                    return Result.fail("修改后的身份证号已被注册！",tempUser.getId());
                }
            }
            if(!teaServiceImp.findUserMajor(tempUser.getMajor())){
                response.setStatus(NO_MAJOR);
                return Result.fail("专业不存在！");
            }

            if(!eduServiceImp.judgeMajorAndCollege(tempUser.getMajor(),tempUser.getCollege())){
                response.setStatus(NO_COLLEGE);
                return Result.fail("无法找到专业对应学院！");
            }

            teaServiceImp.updateTeaInfoByAdmin(tempUser);
        }
        else{
            response.setStatus(WRONG_DATA);
            return Result.fail("用户角色无效");
        }
        return Result.succ("修改成功");
    }




    /*
            学院专业信息维护部分
     */




    //管理员查看全部的学院和专业信息
    @GetMapping("/edu")
    public Result showInfo(){
        List<CollegeVO> collegeList = eduServiceImp.getEduInfo();
        return Result.succ("查询成功",collegeList);
    }

    //管理员增加新的学院
    @PostMapping("/edu/college/new")
    public Result addCollege(@RequestBody College college){
        if(eduServiceImp.insertCollege(college)){
            College inserted = eduServiceImp.selectCollegeByName(college);
            return Result.succ("增加成功",inserted.getCollegeId());
        }else {
            response.setStatus(COLLEGE_CONFLICT);
            return Result.fail("增加失败");
        }
    }

    //管理员增加新的专业
    @PostMapping("/edu/major/new")
    public Result addMajor(@RequestBody Major major){
        if(eduServiceImp.insertMajor(major)) {
            Major inserted = eduServiceImp.selectMajorByName(major);
            return Result.succ("增加成功",inserted.getMajorId());
        }else{
            response.setStatus(MAJOR_CONFLICT);
            return Result.fail("增加失败");
        }
    }

    //管理员删除已有学院
    @DeleteMapping("/edu/college")
    public Result deleteCollege(@RequestBody College college){
        //先查询是否存在该学院
        boolean exist = eduServiceImp.findCollegeByName(college);
        //存在，进行删除，并且删除对应的所有专业
        if(exist){
            eduServiceImp.deleteCollege(college);
        }
        else{
            response.setStatus(NO_COLLEGE);
            return Result.fail("删除失败");
        }
        return Result.succ("删除成功");
    }

    //管理员删除已有专业
    @DeleteMapping("/edu/major")
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
    //前端必须传id
    //加入了重名判断
    @PostMapping("/edu/college")
    public Result changeCollege(@RequestBody College college){
        //先查询是否存在该学院id
        boolean exist = eduServiceImp.findCollegeById(college);
        //存在，进行修改
        if(exist){
            boolean sameName = eduServiceImp.findCollegeByName(college);
            if(sameName){
                response.setStatus(COLLEGE_CONFLICT);
                return Result.fail("已有同名学院，修改失败！");
            }
            eduServiceImp.updateCollege(college);
        }
        else{
            response.setStatus(NO_COLLEGE);
            return Result.fail("修改失败");
        }
        return Result.succ("修改成功");
    }

    //管理员修改已有专业
    //前端必须传id
    //加入了重名判断
    @PostMapping("/edu/major")
    public Result changeMajor(@RequestBody Major major){
        //先查询是否存在该专业
        boolean exist = eduServiceImp.findMajorById(major);
        //存在，进行修改
        if(exist){
            boolean sameName = eduServiceImp.findMajorByName(major);
            if(sameName){
                response.setStatus(MAJOR_CONFLICT);
                return Result.fail("已有同名专业，修改失败！");
            }
            eduServiceImp.updateMajor(major);
        }
        else{
            response.setStatus(NO_MAJOR);
            return Result.fail("修改失败");
        }
        return Result.succ("修改成功");
    }

}
