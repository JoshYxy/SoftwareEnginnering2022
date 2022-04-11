package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.dto.User;
import com.jwsystem.entity.Building;
import com.jwsystem.entity.Classroom;
import com.jwsystem.entity.Timepart;
import com.jwsystem.entity.Times;
import com.jwsystem.service.AdminService;
import com.jwsystem.service.impl.*;
import com.jwsystem.vo.BuildingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/affair")
public class AffairController extends MainController{

    @Autowired
    AdminServiceImp adminServiceImp;

    @Autowired
    CourseServiceImp courseServiceImp;

    @Autowired
    BuildingServiceImp buildingServiceImp;

    @Autowired
    ClassroomServiceImp classroomServiceImp;

    @Autowired
    TimesServiceImp timesServiceImp;

    @Autowired
    TeaServiceImp teaServiceImp;

    @Autowired
    HttpServletResponse response;

    //管理员获得教务信息
    @GetMapping("")
    public Result getAffair(){
        //按照学院对应的所有教室（需要类似学院专业那样，创建VO类？）
        List<BuildingVO> buildingList = buildingServiceImp.getAllRooms();
        List<Times> timesList = timesServiceImp.getAllTimes();
        return Result.succ3(buildingList,timesList,null);
    }

    //管理员获得某间教室的所有上课时间信息
    @GetMapping("/building/room/time")
    public Result getRoomTime(@RequestBody Classroom classRoom){
        List<Timepart> timepartList = courseServiceImp.getAllTimeByRoom(classRoom.getBuilding(),classRoom.getRoomNum());
        int[][] time = new int[7][];
        for (Timepart t:
                timepartList) {
            int i = t.getWeekday();
            //转字符串为int数组
            String[] s = t.getSection().split(" ");
            time[i] = new int[s.length];
            for(int cnt=0; cnt<s.length; cnt++){
                time[i][cnt]=Integer.parseInt(s[cnt]);
            }
        }

        for(int i=0;i<7;i++){
            if(time[i]==null) time[i] = new int[0];
        }

        return Result.succ(time);
    }

//    //管理员获得某个老师所有上课时间
//    @GetMapping("/affair/teacher/time")
//    public Result getTeaTime(@RequestBody User user){
//        User temp = teaServiceImp.getUserByNumber(user.getNumber());
//        if(temp==null){
//            response.setStatus(WRONG_DATA);
//            return Result.fail("获取教师上课时间失败：该老师不存在");
//        }
//
//        List<Timepart> timepartList = courseServiceImp.getAllTimeByTea(user.getNumber());
//
//        int[][] time = new int[7][];
//        for (Timepart t:
//                timepartList) {
//            int i = t.getWeekday();
//            //转字符串为int数组
//            String[] s = t.getSection().split(" ");
//            time[i] = new int[s.length];
//            for(int cnt=0; cnt<s.length; cnt++){
//                time[i][cnt]=Integer.parseInt(s[cnt]);
//            }
//        }
//
//        for(int i=0;i<7;i++){
//            if(time[i]==null) time[i] = new int[0];
//        }
//
//        return Result.succ(time);
//    }

    //管理员修改某节课开始结束时间
    @PostMapping("/times")
    public Result changeTime(@RequestBody List<Times> times){

//  保留：单节修改的方法
//        if(timesServiceImp.findTimesByName(times.getName())==null){
//            //不存在
//            response.setStatus(WRONG_RES);
//            return Result.fail("不存在此节课，无法修改");
//        }
//        timesServiceImp.changeTimesByName(times.getName(),times.getStartTime(),times.getEndTime());

        //yxy的阴间要求：一改改全部
        //先把times表清空
//        timesServiceImp.deleteAll();
//        //再插入list中的全部对象
//        for (Times t:
//             times) {
//            timesServiceImp.addTimes(t);
//        }
//
        return Result.succ("修改成功");
    }

//    //管理员增加节数
//    @PostMapping("/times/new")
//    public Result addTime(@RequestBody Times times){
//        if(timesServiceImp.findTimesByName(times.getName())!=null){
//            //存在同名节数
//            response.setStatus(WRONG_RES);
//            return Result.fail("已存在同名节数，无法添加");
//        }
//        timesServiceImp.addTimes(times);
//        return Result.succ("添加成功");
//    }
//
//    //管理员删除节数
//    @DeleteMapping("/times")
//    public Result deleteTime(@RequestBody Times times){
//        if(timesServiceImp.findTimesByName(times.getName())==null){
//            //不存在
//            response.setStatus(WRONG_RES);
//            return Result.fail("不存在此节课，无法删除");
//        }
//        timesServiceImp.deleteTimesByName(times.getName());
//        return Result.succ("删除成功");
//    }

    //管理员增加楼
    @PostMapping("/building/new")
    public Result addBuilding(@RequestBody Building building){
        if(buildingServiceImp.findByName(building.getFullName())!=null){
            //存在同名楼
            response.setStatus(WRONG_RES);
            return Result.fail("已存在同名教学楼，无法添加");
        }
        //新增加的building的room应该是null
        buildingServiceImp.add(building);
        return Result.succ("添加成功");
    }

    //管理员删楼
    @DeleteMapping("/building")
    public Result deleteBuilding(@RequestBody Building building){
        if(buildingServiceImp.findByName(building.getFullName())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在此教学楼，无法删除");
        }
        //连带删除对应的教室
        buildingServiceImp.deleteByName(building.getFullName());
        return Result.succ("删除成功");
    }

//    //管理员删除一栋楼里所有教室（但是不删楼）
//    @DeleteMapping("/building/rooms")
//    public Result deleteRoomsInBuilding(@RequestBody Building building){
//        if(buildingServiceImp.findByName(building.getFullName())==null){
//            //不存在
//            response.setStatus(WRONG_RES);
//            return Result.fail("不存在此教学楼，无法删除");
//        }
//        //删除对应的教室，但是不删除楼
//        buildingServiceImp.deleteAllRoomByName(building.getFullName());
//        return Result.succ("删除成功");
//    }


    //管理员改楼名
    @PostMapping("/building")
    public Result changeBuilding(@RequestBody Building building){
        if(buildingServiceImp.findById(building.getId())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在此教学楼，无法修改");
        }
        //用id找到对应的楼，然后把全称和简写都改了
        buildingServiceImp.changeById(building);
        return Result.succ("修改成功");
    }

    //管理员增加教室
    @PostMapping("/room/new")
    public Result addRoom(@RequestBody Classroom classRoom){
        if(classroomServiceImp.findByNumAndBuilding(classRoom.getRoomNum(),classRoom.getBuilding())!=null){
            //存在同名教室
            response.setStatus(WRONG_RES);
            return Result.fail("已存在同名教室，无法添加");
        }
        classroomServiceImp.add(classRoom);
        return Result.succ("添加成功");
    }

    //管理员删教室
    @DeleteMapping("/room")
    public Result deleteRoom(@RequestBody Classroom classRoom){
        if(classroomServiceImp.findByNumAndBuilding(classRoom.getBuilding(),classRoom.getRoomNum())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在该教室");
        }
        classroomServiceImp.deleteByBuildingAndRoomNum(classRoom.getBuilding(),classRoom.getRoomNum());
        return Result.succ("删除成功");
    }

    //管理员改教室名
    @PostMapping("/room")
    public Result changeRoom(@RequestBody Classroom classRoom){
        if(classroomServiceImp.findById(classRoom.getRoomId())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在该教室");
        }
        //用id找到对应的教室，然后把名字改了
        classroomServiceImp.changeById(classRoom);
        return Result.succ("修改成功");     //修改成功
    }

    //获得选课权限
    @GetMapping("/curriculaVariable")
    public Result getCurrVariable(){
        boolean curr = adminServiceImp.getCurr();
        return Result.succ("获取选课状态成功",curr);
    }

    //修改选课权限
    @PostMapping("/curriculaVariable")
    public Result changeCurrVariable(@RequestParam("choice") boolean choice){
        adminServiceImp.setCurr(choice);
        return Result.succ("修改选课权限成功");
    }

//    //获得所有楼的信息（不含room）
//    @GetMapping("/building")
//    public Result getBuildingWithoutRoom(){
//        List<Building> buildings = buildingServiceImp.getAllBuildings();
//        return Result.succ("获取成功",buildings);
//    }

}
