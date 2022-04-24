package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.dto.User;
import com.jwsystem.entity.college.College;
import com.jwsystem.entity.college.Major;
import com.jwsystem.entity.user.Student;
import com.jwsystem.entity.user.Teacher;
import com.jwsystem.service.impl.EduServiceImp;
import com.jwsystem.service.impl.StuServiceImp;
import com.jwsystem.service.impl.TeaServiceImp;
import com.jwsystem.util.CSVUtils;
import com.jwsystem.vo.CollegeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.jwsystem.controller.UserController.ID_LENGTH;
import static com.jwsystem.dto.User.*;

@RestController
@RequestMapping("/admin")
public class AdminController extends MainController{

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private EduServiceImp eduServiceImp;

    @Autowired
    private TeaServiceImp teaServiceImp;

    @Autowired
    private StuServiceImp stuServiceImp;




    //用户信息合规性检验函数
    boolean verifyUser (User tempUser){

        boolean valid = true;

        //role检验：是否为老师或学生
        String role = tempUser.getRole();
        if(!role.equals("student") && !role.equals("teacher")) valid = false;

        //number检验：学工号非空判断和数字判断、位数判断
        if(role.equals("student") && !stuServiceImp.legalNumber(tempUser.getNumber())) valid = false;
        if(role.equals("teacher") && !teaServiceImp.legalNumber(tempUser.getNumber())) valid = false;

        //id检验:非空判断和数字判断、位数判断
        String id = tempUser.getId();
        //先检验长度是否为18
        if(id.length() != ID_LENGTH) valid = false;
        else{
            //长度正确的情况下检验前17位为数字，第18位为数字或者X
            String shortId = id.substring(0,17);
            String last = id.substring(17);
            String template = "0123456789Xx";
            if(!StringUtils.isNumeric(shortId) || !template.contains(last)){
                valid = false;
            }
        }

        //name检验
        if(tempUser.getName().isEmpty()) valid = false;

        //password检验
        if(tempUser.getPassword().isEmpty()) valid = false;

        //status检验
        String status = tempUser.getStatus();
        if(!status.equals(GRADUATED)
                && !status.equals(QUIT)
                && !status.equals(STUDYING)
                && !status.equals(WORKING)
        ) valid = false;

        //major检验
        if(tempUser.getMajor().isEmpty()) valid = false;

        //college检验
        if(tempUser.getCollege().isEmpty()) valid = false;

        return valid;
    }

    //用户信息有效性检验函数
    boolean verifyData(User tempUser, String type){

        boolean valid = true;

        if(type.equals("add")){
            //number非重复检验
            String number = tempUser.getNumber();
            if(stuServiceImp.getUserByNumber(number) != null || teaServiceImp.getUserByNumber(number) != null) valid = false;
        }

        //id非重复检验
        String id = tempUser.getId();
        if(type.equals("add")){
            if(stuServiceImp.selectStuById(id) != null || teaServiceImp.selectTeaById(id) != null) valid = false;
        }
        else if(type.equals("change")){
            Student temp1 = stuServiceImp.selectStuById(tempUser.getId());
            Teacher temp2 = teaServiceImp.selectTeaById(tempUser.getId());

            if(temp1!=null){
                String tempNum = temp1.getNumber();
                if(!tempNum.equals(tempUser.getNumber())){
                    valid = false;
                }
            }

            if(temp2!=null){
                String tempNum = temp2.getNumber();
                if(!tempNum.equals(tempUser.getNumber())){
                    valid = false;
                }
            }
        }

        //major存在性检验
        if(!eduServiceImp.findMajorByStringName(tempUser.getMajor())) valid = false;

        //college存在性检验
        if(!eduServiceImp.judgeMajorAndCollege(tempUser.getMajor(),tempUser.getCollege())) valid = false;

        return valid;
    }




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

    //管理员单条录入信息
    @PostMapping("/new")
    public Result register(@RequestBody User tempUser){
        System.out.println("进入了register...");

        //数据格式检查
        if(!verifyUser(tempUser)){
            response.setStatus(WRONG_DATA);
            return Result.fail("用户数据格式不符合规定！");
        }

        //数据有效性检查
        if(!verifyData(tempUser,"add")){
            response.setStatus(WRONG_DATA);
            return Result.fail("用户数据内容无效！");
        }

        if(tempUser.getRole().equals("student")) stuServiceImp.insertUser(tempUser);

        else if(tempUser.getRole().equals("teacher")) teaServiceImp.insertUser(tempUser);

        return Result.succ("录入信息成功",tempUser);
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

    //管理员修改用户信息（包括状态）
    //加入身份证重复的判断
    //加入学院专业是否存在的检测
    @PostMapping("/user/info")
    public Result changeUserInfo(@RequestBody User tempUser){
        //管理员可以修改用户除学工号以外的所有信息

        //数据格式检查
        if(!verifyUser(tempUser)){
            response.setStatus(WRONG_DATA);
            return Result.fail("修改后数据格式不符合规定！");
        }

        //数据有效性检查
        if(!verifyData(tempUser,"change")){
            response.setStatus(WRONG_DATA);
            return Result.fail("修改后数据内容无效！");
        }

        if(tempUser.getRole().equals("student")){
            //学生
            stuServiceImp.updateStuInfoByAdmin(tempUser);
        } else if(tempUser.getRole().equals("teacher")){
            //老师
            teaServiceImp.updateTeaInfoByAdmin(tempUser);
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
