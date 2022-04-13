package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.dto.CourseDTO;
import com.jwsystem.entity.Coursepart;
import com.jwsystem.entity.Request;
import com.jwsystem.entity.Timepart;
import com.jwsystem.entity.Times;
import com.jwsystem.service.impl.*;
import com.jwsystem.util.CSVUtils;
import com.jwsystem.util.CourseUtil;
import com.jwsystem.util.RequestResult;
import com.jwsystem.vo.BuildingVO;
import com.jwsystem.vo.CourseRequest;
import com.jwsystem.vo.CourseVO;
import com.jwsystem.vo.TeacherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.jwsystem.vo.CourseRequest.*;

//管理员对课程的管理
@RestController
@RequestMapping("/course")
public class CourseController extends MainController{

    @Autowired
    private HttpServletResponse response;

    @Autowired
    CourseUtil courseUtil;

    @Autowired
    private CourseServiceImp courseServiceImp;

    @Autowired
    private TeaServiceImp teaServiceImp;

    @Autowired
    private BuildingServiceImp buildingServiceImp;

    @Autowired
    private TimesServiceImp timesServiceImp;

    @Autowired
    private CourseRequestImp courseRequestImp;


    //新增课程
    @PostMapping("/new")
    public Result addCourse(@RequestBody CourseVO courseVO){
        //courseVO 截成两段
        System.out.println(courseVO.getCourseId());
        Coursepart coursePart = new Coursepart(
                courseVO.getCourseId(),
                courseVO.getCourseName(),
                courseVO.getCourseNum(),
                courseVO.getClassHours(),
                courseVO.getCredits(),
                courseVO.getCourseInfo(),
                courseVO.getCollegeName(),
                courseVO.getTeacherNum(),
                courseVO.getTeacherName(),
                courseVO.getCapacity());
        //存课程名称、编号、学院名称、学时、学分、教师姓名、教师工号、课程简介、选课容量
        //返回插入后自增得到的课程id给我
        //存到coursePart表里
        courseServiceImp.insertCoursepart(coursePart);
        int courseId = coursePart.getRelationId();
      //          System.out.println("courseId="+courseId);
        try{
            for(int i=0;i<7;i++){
                //周i（从0到6，表示周天，周一到周六

                if(courseVO.getTimes()[i].length>0){
                    int[] intArray = courseVO.getTimes()[i];
                    //周i有课，转int数组为String
                    String[] strArray = Arrays.stream(intArray)
                            .mapToObj(String::valueOf)
                            .toArray(String[]::new);

                    String timeString =Arrays.toString(strArray)
                            .replace("[","")
                            .replace("]","")
                            .replace(" ","")
                            .replace(',',' ');

                    System.out.println(timeString);

                    //存课程id（对应上面那条）、教师工号、上课楼、教室号、星期几、节次
                    //存到timepart表里
                    Timepart timepart = new Timepart(
                            null,
                            courseId,
                            courseVO.getTeacherNum(),
                            courseVO.getBuilding(),
                            courseVO.getRoomNum(),
                            i,
                            timeString  //1 2 3
                    );

                    //在插入的时候根据老师和教室判断有没有时间冲突
                    boolean res = courseServiceImp.insertTimepart(timepart);
                    if(!res){
                        //插入冲突，删除coursepart表和coursetime表中这次插入相关的数据（设置成连带删除的）
                        courseServiceImp.deleteCoursepartByCourseId(courseId);
                        response.setStatus(CONFLICT_TIME);
                        return Result.fail("插入失败，时间冲突");
                    }
                }

            }
        } catch (IndexOutOfBoundsException e ){
            response.setStatus(WRONG_DATA);
            return Result.fail("数组越界");
        }
        return Result.succ("新增课程成功");
    }





    /*

            课程申请部分

     */


    //管理员获得所有未审核的申请
    @GetMapping("/courseRequests")
    public Result getAllRequests(){

        //从存申请信息的表中取出所有未被审核的申请
        List<Request> requestList = courseRequestImp.getAllRequests();

        List<CourseRequest> courseRequestList = new ArrayList<>();
        //将每个requestInDB包装成CourseRequest
        for (Request r:
                requestList) {
                CourseRequest courseRequest  = new CourseRequest();

                //从req-coursepart和req-timepart里取出request对应数据
                Coursepart cp = courseRequestImp.getReqCoursepartByRequestId(r.getRequestId());
                List<Timepart> tp = courseRequestImp.getAllReqTimepartByRequestId(r.getRequestId());

                courseRequest.setRequestId(r.getRequestId());
                courseRequest.setType(r.getType());
                courseRequest.setPassed(r.isPassed());
                courseRequest.setExamined(r.isExamined());

                CourseVO courseVO = courseUtil.transToVO(cp,tp);
                courseVO.setCourseId(r.getCourseId());

                courseRequest.setCourseVO(courseVO);
                courseRequestList.add(courseRequest);
        }

        return Result.succ(courseRequestList);
    }

    //管理员审核申请
    @PostMapping("/courseRequests")
    public Result examine(@RequestBody RequestResult requestResult){
        //检查request是否存在
        if(requestResult.getRequestId()==null){
            response.setStatus(WRONG_DATA);
            return Result.fail("审核失败：未给出请求id");
        }

        Request r = courseRequestImp.selectRequestById(requestResult.getRequestId());
        if(r==null){
            response.setStatus(WRONG_DATA);
            return Result.fail("审核失败：该请求不存在");
        }

        String resultInfo = null;

        //如果申请通过
        if(requestResult.isRes()){
            String type = r.getType();


            if(type.equals(ADD)){
                //新增课程：先从req-coursepart和req-timepart里取出对应数据
                Coursepart req_cp = courseRequestImp.getReqCoursepartByRequestId(r.getRequestId());
                List<Timepart> req_tp = courseRequestImp.getAllReqTimepartByRequestId(r.getRequestId());
                CourseVO courseVO = courseUtil.transToVO(req_cp,req_tp);
                courseVO.setCourseId(r.getCourseId());

                Result res = addCourse(courseVO);
                if(!res.getMsg().equals("新增课程成功")){
                    //插入失败
                    response.setStatus(WRONG_RES);
                    return Result.fail("申请审核失败：插入失败");
                }
                resultInfo="按照申请增加课程成功";
            }
            else if(type.equals(DELETE)){
                Integer courseId = r.getCourseId();
                if(courseId==null){
                    response.setStatus(WRONG_DATA);
                    return Result.fail("申请审核失败：该请求的课程id为空，数据错误！");
                }
                //删除coursepart表和coursetime表中courseId对应的相关的数据（设置成连带删除的）
                //在Service里加入对courseId对应课程是否存在的判断
                //返回bool，我好判断申请审核有没有成功
                int res = courseServiceImp.deleteCoursepartByCourseId(courseId);
                if(res == 0){
                    response.setStatus(WRONG_RES);
                    return Result.fail("申请审核失败：删除失败");
                }
                resultInfo="按照申请删除课程成功";
            }
            else if(type.equals(CHANGE)){
                //修改：核心思想是根据courseId删掉现有的coursePart和所有的Timepart
                // 然后把requestId对应的reqcoursePart和所有的reqTimepart包装成CourseVO
                // 按照上述courseId插入到coursePart和timePart表里


                Integer courseId = r.getCourseId();
                //先保存当前数据库内课程信息，并且转化成CourseVO对象
                Coursepart tempc = courseServiceImp.getCoursepartByCourseId(courseId);
                List<Timepart> tempt = courseServiceImp.getAllTimepartByCourseId(courseId);
                CourseVO tempVO = courseUtil.transToVO(tempc,tempt);

                //删除coursepart表和coursetime表中courseId对应的相关的数据（设置成连带删除的）
                int res = courseServiceImp.deleteCoursepartByCourseId(courseId);
                if(res!=0){
                    Coursepart req_cp = courseRequestImp.getReqCoursepartByRequestId(r.getRequestId());
                    List<Timepart> req_tp = courseRequestImp.getAllReqTimepartByRequestId(r.getRequestId());
                    CourseVO courseVO = courseUtil.transToVO(req_cp,req_tp);
                    courseVO.setCourseId(courseId);
                    //把修改后的数据按照一样的courseId插入进去
                    Result res2 = addCourse(courseVO);
                    if(!res2.getMsg().equals("新增课程成功")){
                        //插入数据失败，恢复数据
                        addCourse(tempVO);
                        response.setStatus(WRONG_RES);
                        return Result.fail("申请审核失败：新增修改后信息失败");
                    }
                }
                else {
                    response.setStatus(WRONG_RES);
                    return Result.fail("申请审核失败：删除失败");
                }
                resultInfo="按照申请修改课程成功";
            }
            else{
                response.setStatus(WRONG_DATA);
                return Result.fail("无效的请求类型",requestResult);
            }
        }
        //设置对应的请求，examined为true，passed为requestResult.isRes()
        courseRequestImp.examinedById(requestResult.getRequestId(),true,requestResult.isRes());
        return Result.succ("审核完毕，"+ resultInfo);
    }

    //管理员获得全部课程
    @GetMapping("")
    public Result getAllCourse(){

        List<CourseVO> courses = new ArrayList<>();
        List<Coursepart> coursepartList = courseServiceImp.getAllCoursepart();

        for (Coursepart c:
                coursepartList) {
            List<Timepart> timepartList = courseServiceImp.getAllTimepartByCourseId(c.getRelationId());

            //包装成CourseVO的List
            CourseVO tempVO = courseUtil.transToVO(c, timepartList);

            courses.add(tempVO);
        }
        return Result.succ("获取课程信息成功",courses);

    }

    //管理员删除现有课程
    @DeleteMapping("")
    public Result deleteCourse(@RequestBody CourseVO courseVO ){
        //根据courseId删除coursePart和TimePart（做成连带的），加上一个存在性检验，返回bool
        int res = courseServiceImp.deleteCoursepartByCourseId(courseVO.getCourseId());
        if(res == 0){
            response.setStatus(WRONG_RES);
            return Result.fail("删除失败，课程id无效");
        }
        return Result.succ("删除课程成功");
    }

    //管理员修改现有课程
    @PostMapping("")
    public Result changeCourse(@RequestBody CourseVO courseVO){
        //核心思想：把原有的都删了，把传来的插入

        //先保存当前数据库内课程信息，并且转化成CourseVO对象
        Coursepart tempc = courseServiceImp.getCoursepartByCourseId(courseVO.getCourseId());
        List<Timepart> tempt = courseServiceImp.getAllTimepartByCourseId(courseVO.getCourseId());
        CourseVO tempVO = courseUtil.transToVO(tempc,tempt);

        //根据courseId删除coursePart和TimePart（做成连带的），加上一个存在性检验，返回bool
        int res = courseServiceImp.deleteCoursepartByCourseId(courseVO.getCourseId());

        if(res != 0){
            //删除成功，将传来的插入
            //传来的courseVO自带courseId
            Result r = addCourse(courseVO);
            if(!r.getMsg().equals("新增课程成功")){
                //插入修改后信息失败，复原原来的信息
                addCourse(tempVO);
                response.setStatus(WRONG_RES);
                return Result.fail("修改信息失败！插入修改后信息失败！");
            }
        }else{
            //删除失败
            response.setStatus(WRONG_RES);
            return Result.fail("修改信息失败！删除原本信息失败！");
        }
        return Result.succ("修改课程信息成功");
    }

    //CSV导入课程
    @PostMapping("/csv")
    public Result addByCsv(@RequestParam("file") MultipartFile multipartFile){
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

        //从CSV文件批量获取Course对象
        try{

            int wrongCnt = 0;
            List<CourseDTO> courses = CSVUtils.getCourseByCsv(multipartFile);
            //循环完成批量插入（此时插入类型为Course，不是CourseVO）
            for (CourseDTO temp : courses) {
                Coursepart coursepart = new Coursepart(
                        null,
                        temp.getCourseName(),
                        temp.getCourseNum(),
                        temp.getClassHours(),
                        temp.getCredits(),
                        temp.getCourseInfo(),
                        temp.getCollegeName(),
                        temp.getTeacherNum(),
                        temp.getTeacherName(),
                        temp.getCapacity());
                //存课程名称、编号、学院名称、学时、学分、教师姓名、教师工号、课程简介、选课容量
                //返回插入后自增得到的课程id给我
                courseServiceImp.insertCoursepart(coursepart);
                int courseId = coursepart.getRelationId();

                Map<Integer,String> timeMap = new HashMap<>();
                timeMap.put(0,temp.getSun());
                timeMap.put(1,temp.getMon());
                timeMap.put(2,temp.getTue());
                timeMap.put(3,temp.getWed());
                timeMap.put(4,temp.getThu());
                timeMap.put(5,temp.getFri());
                timeMap.put(6,temp.getSat());


                for(int i=0;i<7;i++){
                    //周i（从0到6，表示周天，周一到周六

                    if(timeMap.get(i)!=null && !timeMap.get(i).equals("NULL")){

                        //存课程id（对应上面那条）、教师工号、上课楼、教室号、星期几、节次
                        Timepart timePart = new Timepart(
                                null,
                                courseId,
                                temp.getTeacherNum(),
                                temp.getBuilding(),
                                temp.getRoomNum(),
                                i,
                                timeMap.get(i)
                        );

                        //在插入的时候根据老师和教室判断有没有时间冲突
                        boolean res = courseServiceImp.insertTimepart(timePart);
                        if(!res){
                            //插入冲突，删除coursepart表和coursetime表中这次插入相关的数据（设置成连带删除的）
                            courseServiceImp.deleteCoursepartByCourseId(courseId);
                            wrongCnt++;
                            break;
                        }
                    }
                }
            }
            if(wrongCnt!=0){
                response.setStatus(WRONG_RES);
                String s = "批量导入课程成功，共有"+wrongCnt+"条数据错误。";
                return Result.fail(s,wrongCnt);
            }
            return Result.succ("批量导入课程成功");
        }
        catch (IndexOutOfBoundsException e ) {
            response.setStatus(WRONG_FILE);
            return Result.fail("文件格式错误");
        }
    }
}
