package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.entity.College;
import com.jwsystem.entity.User;
import com.jwsystem.util.CSVUtils;
import com.jwsystem.util.JwtUtils;
import com.opencsv.exceptions.CsvException;
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

        //管理员查看全部用户信息
    @GetMapping("/users")
    public Result getAll(){
        List<User> users = teacherService.getAll();
        List<User> students = studentService.getAll();
        users.addAll(students);
        return Result.succ("查询成功",users);
    }

    //管理员批量导入用户信息
    @PostMapping("/users")
    public Result insertByCSV(@RequestParam("file") MultipartFile multipartFile) throws IOException, CsvException {
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

        //循环完成批量插入
        for (User temp : users) {
            register(temp);
        }
        return Result.succ("批量导入信息成功");
    }

        //管理员取得学院和专业信息
    @GetMapping("/new")
    public Result getRegInfo(){
        List<College> collegeList = eduService.getAll();

        /*
        取出全部学院和专业信息，然后放在result的data里返回
        List<College>
        每个college对象里有一个List<Major>
         */

        return Result.succ("查询成功",collegeList);
    }

    //管理员单条录入信息
    @PostMapping("/new")
    public Result register(@RequestBody User tempUser){
        //增加数据格式检查功能
        System.out.println("进入了register...");
        if(tempUser.getRole().equals("student")){
            //学生
            if(stuDao.selectById(tempUser.getId()) != null){
                response.setStatus(CONFLICT_ID);
                return Result.fail("该身份证号已注册！");
            }

            if(stuDao.selectByNumber(tempUser.getNumber()) != null){
                response.setStatus(CONFLICT_NUMBER);
                return Result.fail("该学号已注册！");
            }

            stuService.insertByNumber(tempUser.getNumber());//增加相应的学院和专业信息的方法
        } else if(tempUser.getRole().equals("teacher")){
            //老师
            if(teacherDao.selectById(tempUser.getId()) != null){
                response.setStatus(CONFLICT_ID);
                return Result.fail("该身份证号已注册！");
            }

            if(teacherDao.selectByNumber(tempUser.getNumber()) != null){
                response.setStatus(CONFLICT_NUMBER);
                return Result.fail("该工号已注册！");
            }

            teacherService.insertByNumber(tempUser.getNumber());//增加相应的学院和专业信息的方法
        }
        else{
            response.setStatus(WRONG_DATA);
            return Result.fail("用户角色无效");
        }
        return Result.succ("录入信息成功",tempUser);
    }

    //管理员修改用户信息（包括状态）
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
            stuService.changeInfo(tempUser);
        } else if(tempUser.getRole().equals("teacher")){
            //老师
            teacherService.changeInfo(tempUser);
        }
        else{
            response.setStatus(WRONG_DATA);
            return Result.fail("用户角色无效");
        }
        return Result.succ("修改成功");
    }
}
