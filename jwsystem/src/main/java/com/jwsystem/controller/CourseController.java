package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.dto.CourseDTO;
import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.dto.RequestDTO;
import com.jwsystem.dto.TimepartDTO;
import com.jwsystem.entity.course.CoursepartPO;
import com.jwsystem.entity.course.RelaCourseStudentPO;
import com.jwsystem.entity.request.ReqStudentPO;
import com.jwsystem.service.ReqStudentServiceMP;
import com.jwsystem.service.StudentServiceMP;
import com.jwsystem.service.impl.*;
import com.jwsystem.util.CSVUtils;
import com.jwsystem.util.TransUtil;
import com.jwsystem.vo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.util.*;

import static com.jwsystem.entity.course.RelaCourseStudentPO.SELECTED;
import static com.jwsystem.vo.CourseRequestVO.*;


//管理员对课程的管理
@RestController
@RequestMapping("/course")
public class CourseController extends MainController{

    @Autowired
    private HttpServletResponse response;

    @Autowired
    TransUtil transUtil;

    @Autowired
    private CoursepartServiceImpMP coursepartServiceImpMP;

    @Autowired
    private TimepartServiceImpMP timepartServiceImpMP;

    @Autowired
    private ReqCoursepartServiceImpMP reqCoursepartServiceImpMP;

    @Autowired
    private ReqTimepartServiceImpMP reqTimepartServiceImpMP;

    @Autowired
    private ReqTeacherServiceImpMP reqTeacherServiceImpMP;

    @Autowired
    private ClassroomServiceImpMP classroomServiceImpMP;

    @Autowired
    private BuildingServiceImpMP buildingServiceImpMP;

    @Autowired
    private RelaCourseStudentServiceImpMP relaCourseStudentServiceImpMP;

    @Autowired
    private StudentServiceMP studentServiceMP;

    @Autowired
    private ReqStudentServiceMP reqStudentServiceMP;



    //判断课程容量与教室容量
    public boolean capacityVerify(CourseVO courseVO){
        int courseCap = Integer.parseInt(courseVO.getCapacity());
        int buildingId = buildingServiceImpMP.selectByAbbrName(courseVO.getBuilding()).getId();
        int roomCap = Integer.parseInt(classroomServiceImpMP.selectByRoomNum(buildingId,courseVO.getRoomNum()).getCapacity());

        //根据课程id找到当前已选人数
        int selected = relaCourseStudentServiceImpMP.xxx(courseVO.getCourseId(),SELECTED).size();

        return (courseCap<=roomCap) && (courseCap>=selected);
    }

    //管理员获得全部课程
    @GetMapping("")
    public Result getAllCourse(){

        List<CourseVO> courses = new ArrayList<>();
        List<CoursepartDTO> coursepartDTOList = coursepartServiceImpMP.getAllCoursepart();

        for (CoursepartDTO c:
                coursepartDTOList) {
            List<TimepartDTO> timepartDTOList = timepartServiceImpMP.selectAllTimepartByCourseId(c.getRelationId());

            //包装成CourseVO的List
            CourseVO tempVO = transUtil.transToVO(c, timepartDTOList,true);

            courses.add(tempVO);
        }
        return Result.succ("获取课程信息成功",courses);

    }

    //新增课程
    @PostMapping("/new")
    public Result addCourse(@RequestBody CourseVO courseVO){
        //判断课程容量与教室容量
        if(!capacityVerify(courseVO)){
            response.setStatus(WRONG_DATA);
            return Result.fail("新增课程失败：课程容量不合规！");
        }

        //courseVO 截成两段
        System.out.println(courseVO.getCourseId());
        CoursepartDTO coursePart = new CoursepartDTO(
                courseVO.getCourseId(),
                courseVO.getCourseName(),
                courseVO.getCourseNum(),
                courseVO.getClassHours(),
                courseVO.getCredits(),
                courseVO.getCourseInfo(),
                courseVO.getCollegeName(),
                courseVO.getTeacherNum(),
                courseVO.getTeacherName(),
                courseVO.getCapacity(),
                courseVO.getYear(),
                courseVO.getSemester(),
                courseVO.getIsGeneral());
        //存课程名称、编号、学院名称、学时、学分、教师姓名、教师工号、课程简介、选课容量
        //返回插入后自增得到的课程id给我
        //存到coursePart表里
        int courseId = coursepartServiceImpMP.insertCoursepart(coursePart);
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
                    TimepartDTO timepartDTO = new TimepartDTO(
                            courseId,
                            courseVO.getTeacherNum(),
                            courseVO.getBuilding(),
                            courseVO.getRoomNum(),
                            i,
                            timeString  //1 2 3
                    );

                    //在插入的时候根据老师和教室判断有没有时间冲突
                    boolean res = timepartServiceImpMP.insertTimepart(timepartDTO);
                    if(!res){
                        //插入冲突，删除coursepart表和coursetime表中这次插入相关的数据（设置成连带删除的）
                        coursepartServiceImpMP.removeById(courseId);
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

    //管理员删除现有课程
    @DeleteMapping("")
    public Result deleteCourse(@RequestBody CourseVO courseVO ){
        //先根据课程id查询该课程是否有人选过或修读过，比如返回list，list不为空
        List<RelaCourseStudentPO> list = relaCourseStudentServiceImpMP.xxx(courseVO.getCourseId());
        if(!list.isEmpty()){
            response.setStatus(WRONG_RES);
            return Result.fail("删除失败，该门课程已被修读过选过");
        }
        //根据courseId删除coursePart和TimePart（做成连带的），加上一个存在性检验，返回bool
        boolean res = coursepartServiceImpMP.removeById(courseVO.getCourseId());
        if(!res){
            response.setStatus(WRONG_RES);
            return Result.fail("删除失败，课程id无效");
        }
        return Result.succ("删除课程成功");
    }

    //管理员修改现有课程
    @PostMapping("")
    public Result changeCourseByAdmin(@RequestBody CourseVO courseVO){
        //判断课程容量与教室容量
        if(!capacityVerify(courseVO)){
            response.setStatus(WRONG_DATA);
            return Result.fail("修改课程失败：课程容量不合规！");
        }

        //先对id所对应的单门课程进行修改，取得修改结果
        Result res = changeCourse(courseVO);

        //单门修改不成功，此时被修改课程也回归到原本状态，因此不对同类课程进行修改操作
        if(!res.getMsg().equals("修改课程信息成功")){
            response.setStatus(WRONG_RES);
            return res;
        }

        //单门课程修改成功，对同类课程的coursepart里的下列字段进行统一修改

        /*
            courseNum
            courseName
            courseInfo
            year
            semester
         */

        //统一更新的只在coursepart里，故进行类型转换
        CoursepartDTO coursePart = new CoursepartDTO(
                courseVO.getCourseId(),
                courseVO.getCourseName(),
                courseVO.getCourseNum(),
                courseVO.getClassHours(),
                courseVO.getCredits(),
                courseVO.getCourseInfo(),
                courseVO.getCollegeName(),
                courseVO.getTeacherNum(),
                courseVO.getTeacherName(),
                courseVO.getCapacity(),
                courseVO.getYear(),
                courseVO.getSemester(),
                courseVO.getIsGeneral());
        CoursepartPO coursepartPO = transUtil.CpDTOtoCpPO(coursePart);
        // TODO: 2022/5/3 MP根据课程num和name找到同类课程，并只对上面几个字段进行更新。在这里增加更新方法
        coursepartServiceImpMP.xxx(courseVO.getCourseName(),courseVO.getCourseNum(),coursepartPO);

        return Result.succ("修改课程信息成功：同类课程信息已更新");
    }

    //核心的修改方法，被审核申请和管理员修改课程调用。不包含同类课程修改部分。
    public Result changeCourse(CourseVO courseVO){
        //核心思想：根据courseId，保存原有的coursepart和所有timepart
        //对原有的coursePart进行修改，然后把原有的timePart全部删掉，插入新的timepart
        //如果有冲突，则将原本coursepart和timeparts重新存入，即进行数据还原

        CoursepartDTO originalCP = coursepartServiceImpMP.selectCoursepartByCourseId(courseVO.getCourseId());
        List<TimepartDTO> timepartDTOList = timepartServiceImpMP.selectAllTimepartByCourseId(courseVO.getCourseId());

        CoursepartDTO coursePart = new CoursepartDTO(
                courseVO.getCourseId(),
                courseVO.getCourseName(),
                courseVO.getCourseNum(),
                courseVO.getClassHours(),
                courseVO.getCredits(),
                courseVO.getCourseInfo(),
                courseVO.getCollegeName(),
                courseVO.getTeacherNum(),
                courseVO.getTeacherName(),
                courseVO.getCapacity(),
                courseVO.getYear(),
                courseVO.getSemester(),
                courseVO.getIsGeneral());
        CoursepartPO coursepartPO = transUtil.CpDTOtoCpPO(coursePart);
        //修改原有的coursepart
        coursepartServiceImpMP.updateById(coursepartPO);
        //删除原有的timepart
        timepartServiceImpMP.deleteTimepartByCourseId(coursePart.getRelationId());
        //插入修改后的timepart
        int courseId = coursePart.getRelationId();
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

                    //存课程id（对应上面那条）、教师工号、上课楼、教室号、星期几、节次
                    //存到timepart表里
                    TimepartDTO timepartDTO = new TimepartDTO(
                            courseId,
                            courseVO.getTeacherNum(),
                            courseVO.getBuilding(),
                            courseVO.getRoomNum(),
                            i,
                            timeString  //1 2 3
                    );

                    //在插入的时候根据老师和教室判断有没有时间冲突
                    boolean res = timepartServiceImpMP.insertTimepart(timepartDTO);
                    if(!res){
                        //插入冲突，删除timepart表中这次插入相关的数据
                        timepartServiceImpMP.deleteTimepartByCourseId(coursePart.getRelationId());
                        //更新成原本的coursepart
                        coursepartServiceImpMP.updateById(transUtil.CpDTOtoCpPO(originalCP));
                        //插入原本的timepart
                        for (TimepartDTO t:
                             timepartDTOList) {
                            timepartServiceImpMP.insertTimepart(t);
                        }
                        return Result.fail("修改时间失败：时间冲突！");
                    }
                }
            }
        } catch (IndexOutOfBoundsException e ){
            return Result.fail("数组越界");
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

                //判断课程容量是否小于教室容量
                int roomCap = Integer.parseInt(classroomServiceImpMP.selectByRoomNum(
                        buildingServiceImpMP.selectByAbbrName(temp.getBuilding()).getId(),temp.getRoomNum())
                        .getCapacity());

                if(Integer.parseInt(temp.getCapacity()) > roomCap) {
                    System.out.println("课程容量大于教室容量："+temp.getCourseName());
                    wrongCnt++;
                    continue;
                }

                CoursepartDTO coursepartDTO = new CoursepartDTO(
                        null,
                        temp.getCourseName(),
                        temp.getCourseNum(),
                        temp.getClassHours(),
                        temp.getCredits(),
                        temp.getCourseInfo(),
                        temp.getCollegeName(),
                        temp.getTeacherNum(),
                        temp.getTeacherName(),
                        temp.getCapacity(),
                        temp.getYear(),
                        temp.getSemester(),
                        temp.getIsGeneral());
                //存课程名称、编号、学院名称、学时、学分、教师姓名、教师工号、课程简介、选课容量
                //返回插入后自增得到的课程id给我
                int courseId = coursepartServiceImpMP.insertCoursepart(coursepartDTO);

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
                        TimepartDTO timePart = new TimepartDTO(
                                courseId,
                                temp.getTeacherNum(),
                                temp.getBuilding(),
                                temp.getRoomNum(),
                                i,
                                timeMap.get(i)
                        );

                        //在插入的时候根据老师和教室判断有没有时间冲突
                        boolean res = timepartServiceImpMP.insertTimepart(timePart);
                        if(!res){
                            //插入冲突，删除coursepart表和coursetime表中这次插入相关的数据（设置成连带删除的）
                            coursepartServiceImpMP.removeById(courseId);
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
        catch (IndexOutOfBoundsException | IOException e) {
            response.setStatus(WRONG_FILE);
            return Result.fail("文件格式错误");
        }
    }

    //管理员单独修改课程容量接口
    @PutMapping("/capacity")
    public Result updateCourseCap(@RequestBody Map<String,Integer>map){
        int courseId = map.get("courseId");
        int capacity = map.get("capacity");

        //根据课程id找到对应的教室，取出最小的教室容量
        //我们现在courseId相同的timepart对应的教室都是同一个，因此只用取一个就行
        List<TimepartDTO> timepartDTOList = timepartServiceImpMP.selectAllTimepartByCourseId(courseId);
        int buildingId = buildingServiceImpMP.selectByAbbrName(timepartDTOList.get(0).getBuilding()).getId();
        int roomCap = Integer.parseInt(classroomServiceImpMP.selectByRoomNum(buildingId,timepartDTOList.get(0).getRoomNum()).getCapacity());

        if(capacity > roomCap){
            response.setStatus(WRONG_DATA);
            return Result.fail("修改失败：课程容量大于教室容量！");
        }

        //根据课程id找到当前已选人数
        int selected = relaCourseStudentServiceImpMP.xxx(courseId).size;

        if(capacity < selected){
            response.setStatus(WRONG_DATA);
            return Result.fail("修改失败：课程容量小于已选人数！");
        }

        //单独修改courseId对应coursepart的capacity字段
        coursepartServiceImpMP.updatexxx(courseId,Integer.toString(capacity));
        return Result.succ("修改容量成功");
    }


     /*

            课程申请部分

     */


    //管理员获得所有未审核的申请
    @GetMapping("/courseRequests")
    public Result getAllRequests(){

        //从存申请信息的表中取出所有未被审核的申请
        List<RequestDTO> requestDTOList = reqTeacherServiceImpMP.selectAllTeacherRequests();

        List<CourseRequestVO> courseRequestVOList = new ArrayList<>();
        //将每个requestInDB包装成CourseRequest

        for (RequestDTO r:
                requestDTOList) {
            CourseRequestVO courseRequestVO = new CourseRequestVO();

            //从req-coursepart和req-timepart里取出request对应数据
            CoursepartDTO cp = reqCoursepartServiceImpMP.selectReqCoursepartByRequestId(r.getRequestId());
            List<TimepartDTO> tp = reqTimepartServiceImpMP.selectAllReqTimepartByRequestId(r.getRequestId());

            courseRequestVO.setRequestId(r.getRequestId());
            courseRequestVO.setType(r.getType());
            courseRequestVO.setPassed(r.isPassed());
            courseRequestVO.setExamined(r.isExamined());

            CourseVO courseVO = transUtil.transToVO(cp,tp,false);
            courseVO.setCourseId(r.getCourseId());

            courseRequestVO.setCourseVO(courseVO);
            courseRequestVOList.add(courseRequestVO);
        }

        return Result.succ(courseRequestVOList);
    }

    //管理员审核申请
    @PostMapping("/courseRequests")
    public Result examine(@RequestBody RequestResultVO requestResultVO){
        //检查request是否存在
        if(requestResultVO.getRequestId()==null){
            response.setStatus(WRONG_DATA);
            return Result.fail("审核失败：未给出请求id");
        }

        RequestDTO r = reqTeacherServiceImpMP.selectRequestById(requestResultVO.getRequestId());
        if(r==null){
            response.setStatus(WRONG_DATA);
            return Result.fail("审核失败：该请求不存在");
        }

        //设置对应的请求，examined为true，passed为requestResult.isRes()
        reqTeacherServiceImpMP.examinedById(requestResultVO.getRequestId(),true, requestResultVO.isRes());

        String resultInfo = "申请未通过";

        Integer requestId = r.getRequestId();
        //将请求附带的课程信息转化成CourseVO对象
        CoursepartDTO tempc = reqCoursepartServiceImpMP.selectReqCoursepartByRequestId(requestId);
        if(tempc==null){
            response.setStatus(WRONG_RES);
            return Result.fail("申请审核失败：课程不存在");
        }

        List<TimepartDTO> tempt = reqTimepartServiceImpMP.selectAllReqTimepartByRequestId(requestId);
        //将requestId修改成courseId，才能正确删除和修改对应课程
        tempc.setRelationId(r.getCourseId());
        for (TimepartDTO t:
                tempt) {
            t.setRelationId(r.getCourseId());
        }
        CourseVO tempVO = transUtil.transToVO(tempc,tempt,false);

        //如果申请通过
        if(requestResultVO.isRes()){
            String type = r.getType();

            if(type.equals(ADD)){
                //新增课程：先从req-coursepart和req-timepart里取出对应数据
                Result res = addCourse(tempVO);
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

                Result res = deleteCourse(tempVO);
                if(!res.getMsg().equals("删除课程成功")){
                    response.setStatus(WRONG_RES);
                    return Result.fail("按申请删除课程失败！该门课程已被修读过选过!", requestResultVO);
                }
                resultInfo="按照申请删除课程成功";
            }
            else if(type.equals(CHANGE)){
                //修改：核心思想是把requestId对应的reqcoursePart和所有的reqTimepart包装成CourseVO
                //按照上述courseId调用修改课程的方法
                if(r.getCourseId()==null){
                    response.setStatus(WRONG_DATA);
                    return Result.fail("申请审核失败：该请求的课程id为空，数据错误！");
                }

                Result res = changeCourse(tempVO);
                if(!res.getMsg().equals("修改课程信息成功")){
                    response.setStatus(CONFLICT_TIME);
                    return Result.fail("修改时间失败：时间冲突！", requestResultVO);
                }
                resultInfo="按照申请修改课程成功";
            }
            else{
                response.setStatus(WRONG_DATA);
                return Result.fail("无效的请求类型", requestResultVO);
            }
        }
        return Result.succ("审核完毕，"+ resultInfo);
    }

    /*
          选课部分
     */

    //获得某门课程已选/已修名单
    @GetMapping("/selectedList")
    public Result selected(@RequestBody Map<String,Integer> map){

        int courseId = map.get("courseId");
        //根据课程id取出已选或已修名单（当前学期为已选，以前的课为已修，因此只传了id进去）
        List<RelaCourseStudentPO> relaCourseStudentPOList = relaCourseStudentServiceImpMP.xxx(courseId);

        List<SelectedStudentVO> selectedStudentVOS = new ArrayList<>();

        for (RelaCourseStudentPO rela:
                relaCourseStudentPOList) {
            UserVO student = transUtil.StudentPOtoUserVO(studentServiceMP.getById(rela.getStudentNum()));
            SelectedStudentVO s = new SelectedStudentVO(
                    student.getName(),
                    student.getNumber(),
                    student.getMajor()
            );

            selectedStudentVOS.add(s);
        }

        return Result.succ(selectedStudentVOS);
    }

    //管理员获得所有未审核的选课申请
    @GetMapping("/student/requests")
    public Result getStuRequests(){
        //取出全部未审核的选课申请
        List<ReqStudentPO> reqStudentPOS = reqStudentServiceMP.xxx(dealt=false);

        List<ReqStudentVO> reqStudentVOS = new ArrayList<>();
        for (ReqStudentPO r:
                reqStudentPOS) {
            ReqStudentVO reqStudentVO = transUtil.ReqStuPOtoVO(r);
            reqStudentVOS.add(reqStudentVO);
        }

        return Result.succ("获取成功",reqStudentVOS);
    }

    //管理员审核学生选课申请
    @PostMapping("/student/requests")
    public Result dealStuReqs(@RequestBody Map<String,Object> map){
        boolean res = (boolean) map.get("approved");
        Integer id = (Integer) map.get("requestId");
        //更新对应学生请求数据为已审核，对应审核结果
        reqStudentServiceMP.update(id,res,dealt=true);

        ReqStudentPO reqStudentPO = reqStudentServiceMP.getById(id);
        int courseId = reqStudentPO.getCourseId();

        if(res){
            //审核通过，判断是否还能加人
            //找出对应课程的容量和已选人数
            //根据课程id找到对应的教室，取出最小的教室容量
            //我们现在courseId相同的timepart对应的教室都是同一个，因此只用取一个就行
            List<TimepartDTO> timepartDTOList = timepartServiceImpMP.selectAllTimepartByCourseId(courseId);
            int buildingId = buildingServiceImpMP.selectByAbbrName(timepartDTOList.get(0).getBuilding()).getId();
            int roomCap = Integer.parseInt(classroomServiceImpMP.selectByRoomNum(buildingId,timepartDTOList.get(0).getRoomNum()).getCapacity());

            //找到当前已选人数
            int selected = relaCourseStudentServiceImpMP.xxx(courseId,SELECTED).size;

            //不能加，返回失败结果
            if(selected >= roomCap){
                response.setStatus(WRONG_DATA);
                return Result.fail("申请通过失败：教室已满，不能再加人！");
            }
            else{
                //能加，判断对应课程是否为学生能选的（此处只需要判断学生是否已经修过/选了同类课）
                //根据课程的名字和编号返回同类课程
                CoursepartPO coursepartPO = coursepartServiceImpMP.getById(reqStudentPO.getCourseId());
                List<CoursepartPO> coursepartPOList = coursepartServiceImpMP.selectCoursepartByNameAndNum(coursepartPO.getCourseName(),coursepartPO.getCourseNum());
                for (CoursepartPO c:
                        coursepartPOList) {
                    //根据课程的id和学生num查找是否有对应的选课/修读记录，有的话就说明已选同类/已修读，则不能选
                    List<RelaCourseStudentPO> relaCourseStudentPOS = relaCourseStudentServiceImpMP.xxx(c.getCourseId(),reqStudentPO.getStudentNum());
                    if(!relaCourseStudentPOS.isEmpty()){
                        response.setStatus(WRONG_DATA);
                        return Result.fail("申请通过失败：学生不能选择已修读的课程或已选过同类课程的课程");
                    }
                }
                //能选，修改对应课程容量+1，添加已选信息
                int capacity = Integer.parseInt(coursepartServiceImpMP.getById(courseId).getCapacity());
                capacity++;
                //更新对应课程的容量
                coursepartServiceImpMP.updatexx(courseId,Integer.toString(capacity));
                //增加该学生的选课信息
                RelaCourseStudentPO relaCourseStudentPO = new RelaCourseStudentPO(
                        null,
                        courseId,
                        reqStudentPO.getStudentNum(),
                        SELECTED
                );
                relaCourseStudentServiceImpMP.save(relaCourseStudentPO);
                return Result.succ("审核成功：成功增加选课信息");
            }
        }
        else{
            return Result.succ("审核成功：已拒绝该选课申请");
        }
    }

    /*
        条件搜索部分
     */

    //根据课程代码、课程名称、教师模糊搜索课程
    @PostMapping("/search")
    public Result blurSearch(@RequestBody Map<String,String>map){
        String word = map.get("search");
        //根据word对课程代码、课程名称、教师字段模糊查询，返回coursepartPO的List
        List<CoursepartPO> temp = coursepartServiceImpMP.xxx(word);

        if(!temp.isEmpty()){
            List<CourseVO> searched = new ArrayList<>();
            for (CoursepartPO c:
                    temp) {
                CoursepartDTO tempc = transUtil.CpPOtoCpDTO(c);
                List<TimepartDTO> tempt = timepartServiceImpMP.selectAllTimepartByCourseId(tempc.getRelationId());
                CourseVO tempVO = transUtil.transToVO(tempc,tempt,true);
                searched.add(tempVO);
            }
            return Result.succ("查询成功",searched);
        }
        else{
            response.setStatus(WRONG_RES);
            return Result.fail("查询失败，没有符合条件的课程");
        }
    }
}
