package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.dto.TimepartDTO;
import com.jwsystem.entity.affair.BuildingPO;
import com.jwsystem.entity.affair.ClassroomPO;
import com.jwsystem.entity.affair.TimesPO;
import com.jwsystem.service.BuildingServiceMP;
import com.jwsystem.util.TransUtil;
import com.jwsystem.vo.ClassroomVO;
import com.jwsystem.vo.UserVO;
import com.jwsystem.service.impl.*;
import com.jwsystem.vo.BuildingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/affair")
public class AffairController extends MainController{

    @Autowired
    AdminServiceImpMP adminServiceImpMP;

    @Autowired
    CoursepartServiceImpMP coursepartServiceImpMP;

    @Autowired
    TimepartServiceImpMP timepartServiceImpMP;

    @Autowired
    ReqTimepartServiceImpMP reqTimepartServiceImpMP;

    @Autowired
    BuildingServiceMP buildingServiceImpMP;

    @Autowired
    ClassroomServiceImpMP classroomServiceImpMP;

    @Autowired
    TimesServiceImpMP timesServiceImpMP;

    @Autowired
    TeacherServiceImpMP teacherServiceImpMP;

    @Autowired
    HttpServletResponse response;

    @Autowired
    TransUtil transUtil;


    //管理员获得教务信息
    @GetMapping("")
    public Result getAffair(){
        //按照学院对应的所有教室（需要类似学院专业那样，创建VO类？）
        List<BuildingVO> buildingList = buildingServiceImpMP.selectAllBuildingAndRoomByList();
        List<TimesPO> timesList = timesServiceImpMP.list();
        return Result.succ3(buildingList,timesList,null);
    }

    //管理员获得某间教室的所有上课时间信息
    @PutMapping("/building/room/time")
    public Result getRoomTime(@RequestBody ClassroomVO classRoom){
        List<TimepartDTO> timepartDTOList = timepartServiceImpMP.selectAllTimepartByRoom(classRoom.getBuilding(),classRoom.getRoomNum());
        int[][] time = new int[7][];
        for (TimepartDTO t:
                timepartDTOList) {
            int i = t.getWeekday();
            //转字符串为int数组
            String[] s = t.getSection().split(" ");
            if(time[i]==null){
                //首次录入该天的上课时间数据，新建一个数组
                time[i] = new int[s.length];
                for(int cnt=0; cnt<s.length; cnt++){
                    time[i][cnt]=Integer.parseInt(s[cnt]);
                }
            }
            else{
                //多次录入，需要将数组进行从小到大拼接
                int l = time[i].length + s.length;
                int[] temp = new int[l];
                List<Integer> tempList = new ArrayList<>();
                for(int cnt=0;cnt<time[i].length;cnt++){
                    tempList.add(time[i][cnt]);
                }

                for (String value : s) {
                    tempList.add(Integer.parseInt(value));
                }
                Collections.sort(tempList);

                for(int cnt=0;cnt<l;cnt++){
                    temp[cnt] = tempList.get(cnt);
                }

                time[i]=temp;
            }
        }

        for(int i=0;i<7;i++){
            if(time[i]==null) time[i] = new int[0];
        }

        return Result.succ(time);
    }

    //管理员获得某个老师所有上课时间
    @PutMapping("/teacher/time")
    public Result getTeaTime(@RequestBody UserVO userVO){
        UserVO temp = teacherServiceImpMP.selectUserByNumber(userVO.getNumber());
        if(temp==null){
            response.setStatus(WRONG_DATA);
            return Result.fail("获取教师上课时间失败：该老师不存在");
        }

        List<TimepartDTO> timepartDTOList = timepartServiceImpMP.selectAllTimepartByTea(userVO.getNumber());

        int[][] time = new int[7][];
        for (TimepartDTO t:
                timepartDTOList) {
            int i = t.getWeekday();
            //转字符串为int数组
            String[] s = t.getSection().split(" ");
            if(time[i]==null){
                //首次录入该天的上课时间数据，新建一个数组
                time[i] = new int[s.length];
                for(int cnt=0; cnt<s.length; cnt++){
                    time[i][cnt]=Integer.parseInt(s[cnt]);
                }
            }
            else{
                //多次录入，需要将数组进行从小到大拼接
                int l = time[i].length + s.length;
                int[] tempArr = new int[l];
                List<Integer> tempList = new ArrayList<>();
                for(int cnt=0;cnt<time[i].length;cnt++){
                    tempList.add(time[i][cnt]);
                }

                for (String value : s) {
                    tempList.add(Integer.parseInt(value));
                }
                Collections.sort(tempList);

                for(int cnt=0;cnt<l;cnt++){
                    tempArr[cnt] = tempList.get(cnt);
                }

                time[i]=tempArr;
            }
        }

        for(int i=0;i<7;i++){
            if(time[i]==null) time[i] = new int[0];
        }

        return Result.succ(time);
    }

    //管理员修改节次
    @PostMapping("/times")
    public Result changeTime(@RequestBody List<TimesPO> times){

        //前端要求：一改改全部
        //加入对是否有课的判断
        //根据传入times的个数，表示出修改后节次，从第一节开始
        int changed = times.size();

        //取出数据库内目前的times数组，转化为int，表示节次
        List<TimesPO> timesInDB = timesServiceImpMP.list();
        int now = timesInDB.size();

        //如果存在删除，即传入节次少于目前节次，则被删掉的节次就是从changed末尾，差值个数的节次，将其作为数组传入service，检验有没有课
        if(changed<now){
            int diff = now - changed;
            String [] s = new String[diff];
            for(int i=0;i<diff;i++){
                s[i] = Integer.toString(changed+i+1);
            }

            //如果有课，修改失败
            //有课返回true
            //得到timepart 和 req_timepart中所有的section，现在的TimepartServiceImplMP里只拿出了timepart的，没有拿出reqTimepart的
            //在reqTimepart里再加一个examineTimes，内部逻辑类似
            //把service里请求和课程的方法分开
            boolean haveClass = (timepartServiceImpMP.examineTimes(s)
                    && reqTimepartServiceImpMP.examineTimes(s));


            if(haveClass){
                response.setStatus(CONFLICT_TIME);
                return Result.fail("删除的时间段存在课程，删除失败");
            }
        }

        //无冲突，执行修改操作
        //先把times表清空
        timesServiceImpMP.remove(null);
        //再插入list中的全部对象
        for (TimesPO t:
             times) {
            timesServiceImpMP.save(t);
        }

        return Result.succ("修改成功");
    }


    //管理员增加楼
    @PostMapping("/building/new")
    public Result addBuilding(@RequestBody BuildingPO building){
        if( buildingServiceImpMP.selectByFullName(building.getFullName())!=null){
            //存在同名楼
            response.setStatus(WRONG_RES);
            return Result.fail("已存在同名教学楼，无法添加");
        }
        //新增加的building的room应该是null
        buildingServiceImpMP.save(building);
        return Result.succ("添加成功");
    }

    //管理员删楼
    @DeleteMapping("/building")
    public Result deleteBuilding(@RequestBody BuildingPO building){
        if(buildingServiceImpMP.selectByFullName(building.getFullName())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在此教学楼，无法删除");
        }
        //楼里有教室，删除失败
        BuildingPO buildingPO = buildingServiceImpMP.selectByAbbrName(building.getAbbrName());
        List<ClassroomPO> classroomPOS = classroomServiceImpMP.selectListByBuildingId(buildingPO.getId());
        if(!classroomPOS.isEmpty()){
            response.setStatus(WRONG_RES);
            return Result.fail("教学楼内有教室，无法删除");
        }

        buildingServiceImpMP.deleteByName(building.getFullName());

        return Result.succ("删除成功");
    }

    //管理员删除一栋楼里所有教室（但是不删楼）
    @DeleteMapping("/building/rooms")
    public Result deleteRoomsInBuilding(@RequestBody BuildingPO building){
        if(buildingServiceImpMP.selectByAbbrName(building.getAbbrName())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在此教学楼，无法删除");
        }

        //检验楼内教室有没有课程
        List<Integer> idList = timepartServiceImpMP.selectCourseIdByBuilding(building.getAbbrName());
        if(!idList.isEmpty()){
            //楼内有课
            response.setStatus(WRONG_RES);
            return Result.fail("楼内存在课程，删除失败！");
        }

        //删除对应的教室，但是不删除楼
        BuildingPO buildingPO = buildingServiceImpMP.selectByAbbrName(building.getAbbrName());
        classroomServiceImpMP.deleteByBuildingId(buildingPO.getId());
        return Result.succ("删除成功");
    }


    //管理员增加教室
    @PostMapping("/room/new")
    public Result addRoom(@RequestBody ClassroomVO classRoom){
        BuildingPO buildingPO = buildingServiceImpMP.selectByAbbrName(classRoom.getBuilding());
        if(buildingPO == null){
            response.setStatus(WRONG_DATA);
            return Result.fail("不存在对应教学楼，无法添加");
        }

        if(classroomServiceImpMP.selectByRoomNum(buildingPO.getId(),classRoom.getRoomNum())!=null){
            //存在同名教室
            response.setStatus(WRONG_RES);
            return Result.fail("已存在同名教室，无法添加");
        }
        ClassroomPO classroomPO = transUtil.ClassroomVOtoPO(classRoom);
        classroomServiceImpMP.save(classroomPO);
        return Result.succ("添加成功");
    }

    //管理员删教室
    @DeleteMapping("/room")
    public Result deleteRoom(@RequestBody ClassroomVO classRoom){
        BuildingPO buildingPO = buildingServiceImpMP.selectByAbbrName(classRoom.getBuilding());
        if(buildingPO == null){
            response.setStatus(WRONG_DATA);
            return Result.fail("不存在对应教学楼，无法删除");
        }
        if(classroomServiceImpMP.selectByRoomNum(buildingPO.getId(),classRoom.getRoomNum())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在该教室");
        }

        //检验教室有没有课程
        List<Integer> idList = timepartServiceImpMP.selectCourseIdByRoom(classRoom.getBuilding(),classRoom.getRoomNum());
        if(!idList.isEmpty()){
            //有课
            response.setStatus(WRONG_RES);
            return Result.fail("教室内存在课程，删除失败");
        }

        //根据buildingPO拿到buildingid，然后删除
        int buildingId = buildingPO.getId();
        classroomServiceImpMP.deleteByBuildingAndRoomNum(buildingId,classRoom.getRoomNum());
        return Result.succ("删除成功");
    }

    //修改选课状态
    @PostMapping("/curriculaVariable")
    public Result changeCurrVariable(@RequestParam("choice") String choice){
        adminServiceImpMP.setCurr(choice);
        return Result.succ("修改选课权限成功");
    }

    //获得所有楼的信息（不含room）
    @GetMapping("/building")
    public Result getBuildingWithoutRoom(){
        List<BuildingPO> buildings = buildingServiceImpMP.list();
        return Result.succ("获取成功",buildings);
    }

}
