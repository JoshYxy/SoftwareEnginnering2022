package com.jwsystem.controller;

import com.jwsystem.common.Result;

import com.jwsystem.entity.affair.TimesPO;
import com.jwsystem.entity.user.StudentPO;
import com.jwsystem.entity.user.TeacherPO;
import com.jwsystem.vo.UserVO;
import com.jwsystem.service.impl.*;
import com.jwsystem.util.JwtUtils;
import com.jwsystem.vo.BuildingVO;
import com.jwsystem.vo.TeacherDataVO;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.jwsystem.vo.UserVO.*;
import static com.jwsystem.vo.UserVO.QUIT;

@RestController
@RequestMapping("/user")
public class UserController extends MainController{

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StudentServiceImpMP studentServiceImpMP;

    @Autowired
    private AdminServiceImpMP adminServiceImpMP;

    @Autowired
    private TeacherServiceImpMP teacherServiceImpMP;

    @Autowired
    private BuildingServiceImpMP buildingServiceImpMP;

    @Autowired
    private TimesServiceImpMP timesServiceImpMP;

    @Autowired
    private MajorServiceImpMP majorServiceImpMP;

    public static int TEACHER_NUM_LENGTH = 8;
    public static int STUDENT_NUM_LENGTH = 6;
    public static int ID_LENGTH = 18;


    //用户信息合规性检验函数
    boolean verifyUser (UserVO tempUserVO){

        boolean valid = true;

        String role = tempUserVO.getRole();
        String id = tempUserVO.getId();
        String status = tempUserVO.getStatus();

        //role检验：是否为老师或学生
        if(!role.equals("student") && !role.equals("teacher")){
            valid = false;
        }

        //number检验：学工号非空判断和数字判断、位数判断
        else if(role.equals("student") && !studentServiceImpMP.legalNumber(tempUserVO.getNumber())){
            valid = false;
        }
        else if(role.equals("teacher") && !teacherServiceImpMP.legalNumber(tempUserVO.getNumber())){
            valid = false;
        }

        //id检验:非空判断和数字判断、位数判断
        //先检验长度是否为18
        else if(id.length() != ID_LENGTH) {
            valid = false;
        }
        else if(id.length() == ID_LENGTH){
            //长度正确的情况下检验前17位为数字，第18位为数字或者X
            String shortId = id.substring(0,17);
            String last = id.substring(17);
            String template = "0123456789Xx";
            if(!org.apache.commons.lang3.StringUtils.isNumeric(shortId) || !template.contains(last)){
                valid = false;
            }
        }

        //name检验
        else if(tempUserVO.getName().isEmpty()) valid = false;

            //password检验
        else if(tempUserVO.getPassword().isEmpty()) valid = false;

            //status检验
        else if(!status.equals(GRADUATED)
                && !status.equals(QUIT)
                && !status.equals(STUDYING)
                && !status.equals(WORKING)
        ) valid = false;

            //major检验
        else if(tempUserVO.getMajor().isEmpty()) valid = false;

            //college检验
        else if(tempUserVO.getCollege().isEmpty()) valid = false;

        return valid;
    }

    //用户信息有效性检验函数
    boolean verifyData(UserVO tempUserVO, String type){

        boolean valid = true;

        if(type.equals("add")){
            //number非重复检验
            String number = tempUserVO.getNumber();
            if(studentServiceImpMP.selectUserByNumber(number) != null || teacherServiceImpMP.selectUserByNumber(number) != null) valid = false;
        }

        //id非重复检验
        String id = tempUserVO.getId();
        if(type.equals("add")){
            if(studentServiceImpMP.selectStuById(id) != null || teacherServiceImpMP.selectTeaById(id) != null) valid = false;
        }
        else if(type.equals("change")){
            StudentPO temp1 = studentServiceImpMP.selectStuById(tempUserVO.getId());
            TeacherPO temp2 = teacherServiceImpMP.selectTeaById(tempUserVO.getId());

            if(temp1!=null){
                String tempNum = temp1.getNumber();
                if(!tempNum.equals(tempUserVO.getNumber())){
                    valid = false;
                }
            }

            if(temp2!=null){
                String tempNum = temp2.getNumber();
                if(!tempNum.equals(tempUserVO.getNumber())){
                    valid = false;
                }
            }
        }

        //major存在性检验
        if(majorServiceImpMP.selectMajorByName(tempUserVO.getMajor()) == null ) valid = false;

            //college存在性检验
        else if(!majorServiceImpMP.judgeMajorAndCollege(tempUserVO.getMajor(), tempUserVO.getCollege())) valid = false;

        return valid;
    }


    //登陆请求
    @PostMapping("")
    public Result login (@RequestBody UserVO tempUserVO){

        String number = tempUserVO.getNumber();
        String password = tempUserVO.getPassword();

        //用Dao，以number为键去数据库查询有没有对应的用户
        UserVO userVO;

        if(number.length() == TEACHER_NUM_LENGTH){
            //老师
            userVO = teacherServiceImpMP.selectUserByNumber(number);

            if(userVO ==null){
                response.setStatus(NO_USER);
                return Result.fail("用户不存在");
            }

            if( !userVO.getStatus().equals(WORKING) ) {
                response.setStatus(WRONG_DATA);
                return Result.fail("已离职教师无权登陆");
            }
        }
        else if(number.length() == STUDENT_NUM_LENGTH){
            //学生
            userVO = studentServiceImpMP.selectUserByNumber(number);

            if(userVO ==null){
                response.setStatus(NO_USER);
                return Result.fail("用户不存在");
            }

            if( !userVO.getStatus().equals(STUDYING)) {
                response.setStatus(WRONG_DATA);
                return Result.fail("已毕业学生无权登陆");
            }
        }
        else{
            userVO = adminServiceImpMP.selectAdminUserByNumber(number);
            if(userVO ==null){
                response.setStatus(NO_USER);
                return Result.fail("用户不存在");
            }
        }

        if(!userVO.getPassword().equals(password)){
            response.setStatus(WRONG_PSWD);
            return Result.fail("密码错误");
        }

        String token = jwtUtils.generateToken(number.toString()) ;
        if (!StringUtils.isNullOrEmpty(token)) {
            response.addHeader("Access-Control-Expose-Headers","token");
            response.setHeader(jwtUtils.getHeader(),token);
        }

        String role = userVO.getRole();
        response.addHeader("Access-Control-Expose-Headers","role");
        response.setHeader("role",role);

        return Result.succ("登陆成功",null);
    }

    //第一次登陆修改密码
    @PostMapping("/password")
    public Result reset(@RequestBody UserVO tempUserVO) {
        String number = getNumByToken();

        if(number.length() == TEACHER_NUM_LENGTH){
            //老师
            teacherServiceImpMP.updatePwdByNumber(tempUserVO.getPassword(),number);
        }
        else if(number.length() == STUDENT_NUM_LENGTH){
            //学生
            studentServiceImpMP.updatePwdByNumber(tempUserVO.getPassword(),number);
        }
        else{
            response.setStatus(WRONG_DATA);
            return Result.fail("用户学工号长度非法");
        }

        return Result.succ("密码修改成功");
    }

    //用户查看个人信息
    @GetMapping("/info")
    public Result getInfo(){
        String number = getNumByToken();
        //根据number长度判断登陆用户是老师还是学生，再到对应的表中去查
        UserVO userVO;
        if(number.length() == STUDENT_NUM_LENGTH){
            //学生
                 userVO = studentServiceImpMP.selectUserByNumber(number);
        } else {
            //老师
                userVO = teacherServiceImpMP.selectUserByNumber(number);
        }
        if(userVO == null){
            response.setStatus(NO_USER);
            return Result.fail("用户不存在！");
        }
        return Result.succ(userVO);
    }

    //用户修改个人信息
    //前端一定要返回完整的User对象
    //判断密码重复
    @PostMapping("/info")
    public Result changeInfo(@RequestBody UserVO tempUserVO){

        boolean r = verifyUser(tempUserVO);
        if(r) r=verifyData(tempUserVO,"change");

        if(!r){
            response.setStatus(WRONG_RES);
            return Result.fail("修改信息失败，传入数据不合法！");
        }


        //判断登陆用户是老师还是学生，再到对应的表中去查
        if(tempUserVO.getRole().equals("student")){
            //学生
            boolean res = (studentServiceImpMP.updateStuInfoByUser(tempUserVO) != 0);
            if(!res){
                response.setStatus(WRONG_RES);
                return Result.fail("修改信息失败，service层操作没有正确执行");
            }
        }
        else if(tempUserVO.getRole().equals("teacher")){
            //老师
            boolean res = (teacherServiceImpMP.updateTeaInfoByUser(tempUserVO) != 0);
            if (!res){
                response.setStatus(WRONG_RES);
                return Result.fail("修改信息失败，service层操作没有正确执行");
            }
        }
        else {
            response.setStatus(WRONG_DATA);
            return Result.fail("无效的用户role", tempUserVO);
        }
        return Result.succ("用户信息修改成功！");
    }

    //获得选课权限
    @GetMapping("/curriculaVariable")
    public Result getCurrVariable(){
        String curr = adminServiceImpMP.getCur();
        return Result.succ("获取选课状态成功",curr);
    }

    //新增课程获得必要信息
    @GetMapping("/course/new")
    public Result getCourseInfo(){
        //返回教师信息：按照学院分类，将每个学院的老师都取出来，以teacherData的List返回
        List<TeacherDataVO> teacherDataVOList = teacherServiceImpMP.getAllTeachersWithCollege();

        //返回教室信息：按照楼分类，将每个楼里的教室都取出来，以classroom list的形式返回
        List<BuildingVO> buildingVOList = buildingServiceImpMP.selectAllBuildingAndRoomByList();

        //上课时间信息
        List<TimesPO> times = timesServiceImpMP.list();

        return Result.succ3(teacherDataVOList,buildingVOList,times);
    }

}
