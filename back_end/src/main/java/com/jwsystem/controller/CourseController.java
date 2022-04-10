package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.dto.CourseDTO;
import com.jwsystem.dto.CourseRequest;
import com.jwsystem.entity.*;
import com.jwsystem.service.impl.BuildingServiceImp;
import com.jwsystem.service.impl.CourseServiceImp;
import com.jwsystem.service.impl.TeaServiceImp;
import com.jwsystem.service.impl.TimesServiceImp;
import com.jwsystem.util.CSVUtils;
import com.jwsystem.util.CourseUtil;
import com.jwsystem.util.JwtUtils;
import com.jwsystem.util.RequestResult;
import com.jwsystem.vo.BuildingDTO;
import com.jwsystem.vo.BuildingVO;
import com.jwsystem.vo.CourseVO;
import com.jwsystem.vo.TeacherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.jwsystem.dto.CourseRequest.*;

//管理员对课程的管理
@RestController
@RequestMapping("/course")
public class CourseController extends MainController{

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    CourseUtil courseUtil;

    @Autowired
    private CourseServiceImp courseServiceImp;

    @Autowired
    private CourseRequestService courseRequestService;

    @Autowired
    private TeaServiceImp teaServiceImp;

    @Autowired
    private BuildingServiceImp buildingServiceImp;

    @Autowired
    private TimesServiceImp timesServiceImp;

    //新增课程获得必要信息
    @GetMapping("/new")
    public Result getInfo(){
        //返回教师信息：按照学院分类，将每个学院的老师都取出来，以teacherData的List返回
        List<TeacherData> teacherDataList = teaServiceImp.getAllTeachersWithCollege();

        //返回教室信息：按照楼分类，将每个楼里的教室都取出来，以classroom list的形式返回
        List<BuildingVO> buildingVOList = buildingServiceImp.getAllRooms();

        //上课时间信息
        List<Times> times = timesServiceImp.getAllTimes();

        return Result.succ(teacherDataList,buildingVOList,times);
    }

    //新增课程
    @PostMapping("/new")
    public Result addCourse(CourseVO courseVO){
        //courseVO 截成两段

        Coursepart coursePart = new Coursepart(
                null,
                null,
                courseVO.getCourseName(),
                courseVO.getCourseNum(),
                courseVO.getCollegeName(),
                courseVO.getClassHours(),
                courseVO.getCredits(),
                courseVO.getTeacherName(),
                courseVO.getTeacherNum(),
                courseVO.getCourseInfo(),
                courseVO.getCapacity());
        //存课程名称、编号、学院名称、学时、学分、教师姓名、教师工号、课程简介、选课容量
        //返回插入后自增得到的课程id给我
        int courseId = courseService.insertCoursePart(coursePart);

        try{
            for(int i=0;i<7;i++){
                //周i（从0到6，表示周天，周一到周六

                if(courseVO.getTimes()[i].length>0){
                    int[] intArray = courseVO.getTimes()[i];
                    //周i有课，转int数组为String
                    String strArray[] = Arrays.stream(intArray)
                            .mapToObj(String::valueOf)
                            .toArray(String[]::new);

                    String timeString =Arrays.toString(strArray)
                            .replace("[","")
                            .replace("]","")
                            .replace(" ","")
                            .replace(',',' ');

                    System.out.println(timeString);

                    //存课程id（对应上面那条）、教师工号、上课楼、教室号、星期几、节次
                    Timepart timePart = new Timepart(
                            courseId,
                            null,
                            courseVO.getTeacherNum(),
                            courseVO.getBuilding(),
                            courseVO.getRoomNum(),
                            i,
                            timeString  //1 2 3
                    );

                    //在插入的时候根据老师和教室判断有没有时间冲突
                    /*
                    判断有没有时间冲突
                    判断有没有时间冲突
                    判断有没有时间冲突
                    判断有没有时间冲突
                    判断有没有时间冲突
                    判断有没有时间冲突
                     */
                    boolean res = courseService.insertTimePart(timePart);
                    if(!res){
                        //插入冲突，删除coursepart表和coursetime表中这次插入相关的数据（设置成连带删除的）
                        courseService.deleteCoursePartByCourseId(courseId);
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
        //从数据库中取出所有没有被审核的申请
        List<Request> requestList = courseRequestService.getAllRequest();

        List<CourseRequest> courseRequestList = new ArrayList<>();
        //将每个requestInDB包装成CourseRequest
        for (Request r:
                requestList) {
                CourseRequest courseRequest  = new CourseRequest();
                Coursepart cp = courseService.getCourseByRequestId(r.getRequestId());
                List<Timepart> tp = courseService.getAllTimePartByRequestId(r.getRequestId());

                courseRequest.setRequestId(r.getRequestId());
                courseRequest.setType(r.getType());
                courseRequest.setPassed(r.isPassed());
                courseRequest.setExamined(r.isExamined());

                CourseVO courseVO = courseUtil.transToVO(cp,tp);

                courseRequest.setCourseVO(courseVO);
                courseRequestList.add(courseRequest);
        }

        return Result.succ(courseRequestList);
    }

    //管理员审核申请
    @PostMapping("/courseRequests")
    public Result examine(@RequestBody RequestResult requestResult){
        //如果申请通过
        if(requestResult.isRes()){
            Request r = courseRequestService.selectRequestById(requestResult.getRequestId());
            String type = r.getType();

            if(type.equals(ADD)){
                //新增课程：
                Coursepart cp = courseService.getCoursePartByRequestId(r.getRequestId());
                List<Timepart> tp = courseService.getAllTimePartByRequestId(r.getRequestId());
                CourseVO courseVO = courseUtil.transToVO(cp,tp);
                //用申请id删除掉申请对应的coursePart和TimePart（设置成连带删除）
                courseService.deleteCoursePartByRequestId(requestResult.getRequestId());
                addCourse(courseVO);
            }
            else if(type.equals(DELETE)){
                String courseId = r.getCourseId();
                //删除coursepart表和coursetime表中courseId对应的相关的数据（设置成连带删除的）
                courseService.deleteCoursePartByCourseId(courseId);
                //用申请id删除掉申请对应的coursePart和TimePart（设置成连带删除）
                courseService.deleteCoursePartByRequestId(requestResult.getRequestId());
            }
            else if(type.equals(CHANGE)){
                //修改：核心思想是根据courseId删掉现有的coursePart和所有的Timepart，然后把requestId对应的coursePart和所有的Timepart的courseId改掉，然后擦掉requestId
                String courseId = r.getCourseId();
                //删除coursepart表和coursetime表中courseId对应的相关的数据（设置成连带删除的）
                courseService.deleteCoursePartByCourseId(courseId);
                //修改requestId对应的两部分数据的courseid，并且擦掉requestId
                //能不能做成连带的？
                courseService.changeCourseIdByRequestId(requestResult.getRequestId(),r.getCourseId());
            }
            else{
                response.setStatus(WRONG_DATA);
                return Result.fail("无效的请求类型",requestResult);
            }
        }
        //设置对应的请求，examined为true，passed为requestResult.isRes()
        courseRequestService.examinedById(requestResult.getRequestId(),requestResult.isRes(),true);
        return Result.succ("审核成功");
    }

    //管理员获得全部课程
    @GetMapping("")
    public Result getAllCourse(){

        List<CourseVO> courses = new ArrayList<>();
        List<Coursepart> coursepartList = courseServiceImp.getAllCoursepart();

        for (Coursepart c:
                coursepartList) {
            List<Timepart> timepartList = courseServiceImp.getAllTimepartByCourseId(c.getCourseId());

            //包装成CourseVO的List
            CourseVO tempVO = courseUtil.transToVO(c, timepartList);

            courses.add(tempVO);
        }
        return Result.succ("获取课程信息成功",courses);
    }

    //管理员删除现有课程
    @DeleteMapping("")
    public Result deleteCourse(@RequestBody int courseId ){
        //根据courseId删除coursePart和TimePart（做成连带的），加上一个存在性检验，返回bool
        boolean res = courseService.deleteByCourseId(courseId);
        if(!res){
            response.setStatus(WRONG_RES);
            return Result.fail("删除失败，课程id无效");
        }
        return Result.succ("删除课程成功");
    }

    //管理员修改现有课程
    @PostMapping("")
    public Result changeCourse(@RequestBody CourseVO courseVO){
        //核心思想：把原有的都删了，把传来的插入
        //根据courseId删除coursePart和TimePart（做成连带的），加上一个存在性检验，返回bool
        boolean res = courseService.deleteByCourseId(courseId);

        if(res){
            //删除成功，将传来的插入
            addCourse(courseVO);
        }else{
            //删除失败
            response.setStatus(WRONG_RES);
            return Result.fail("修改信息失败！");
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

        //从CSV文件批量获取User对象
        try{
            List<CourseDTO> courses = CSVUtils.getCourseByCsv(multipartFile);
            //循环完成批量插入（此时插入类型为Course，不是CourseVO）
            for (CourseDTO temp : courses) {
                Coursepart coursePart = new Coursepart(
                        null,
                        null,
                        temp.getCourseName(),
                        temp.getCourseNum(),
                        temp.getCollegeName(),
                        temp.getClassHours(),
                        temp.getCredits(),
                        temp.getTeacherName(),
                        temp.getTeacherNum(),
                        temp.getCourseInfo(),
                        temp.getCapacity());
                //存课程名称、编号、学院名称、学时、学分、教师姓名、教师工号、课程简介、选课容量
                //返回插入后自增得到的课程id给我
                int courseId = courseService.insertCoursePart(coursePart);

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

                    if(timeMap.get(i)!=null){

                        //存课程id（对应上面那条）、教师工号、上课楼、教室号、星期几、节次
                        Timepart timePart = new Timepart(
                                courseId,
                                null,
                                temp.getTeacherNum(),
                                temp.getBuilding(),
                                temp.getRoomNum(),
                                i,
                                timeMap.get(i)
                        );

                        //在插入的时候根据老师和教室判断有没有时间冲突
                    /*
                    判断有没有时间冲突
                    判断有没有时间冲突
                    判断有没有时间冲突
                    判断有没有时间冲突
                    判断有没有时间冲突
                    判断有没有时间冲突
                     */
                        boolean res = courseService.insertTimePart(timePart);
                        if(res == false){
                            //插入冲突，删除coursepart表和coursetime表中这次插入相关的数据（设置成连带删除的）
                            courseService.deleteCoursePartByCourseId(courseId);
                            response.setStatus(CONFLICT_TIME);
                            return Result.fail("插入失败，时间冲突");
                        }
                    }
                }
            }
            return Result.succ("批量导入课程成功");
        }
        catch (IndexOutOfBoundsException e ) {
            response.setStatus(WRONG_FILE);
            return Result.fail("文件格式错误");
        }
    }


}
