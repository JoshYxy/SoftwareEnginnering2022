package com.jwsystem.controller;

import com.jwsystem.common.Result;
import com.jwsystem.entity.Building;
import com.jwsystem.entity.Classroom;
import com.jwsystem.entity.Timepart;
import com.jwsystem.entity.Times;
import com.jwsystem.service.AdminService;
import com.jwsystem.service.impl.TimesServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/affair")
public class AffairController extends MainController{

    @Autowired
    AdminService adminService;

    @Autowired
    CourseService courseService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    ClassRoomService classRoomService;

    @Autowired
    TimesServiceImp timesServiceImp;

    @Autowired
    HttpServletResponse response;

    //管理员获得教务信息
    @GetMapping("")
    public Result getAffair(){
        //按照学院对应的所有教室（需要类似学院专业那样，创建VO类？）
        List<Building> buildingList = buildingService.getAllRoom();
        List<Times> timesList = timesServiceImp.getAllTimes();
        return Result.succ(buildingList,timesList,null);
    }

    //管理员获得某间教室的所有上课时间信息
    @GetMapping("/building/room/time")
    public Result getRoomTime(@RequestBody Classroom classRoom){
        List<Timepart> timepartList = courseService.getAllTimeByRoom(classRoom.getBuilding(),classRoom.getRoomNum());
        int[][] time = new int[7][];
        for (Timepart t:
                timepartList) {
            int i = t.getWeekday();
            //转字符串为int数组
            String[] s = t.getSection().split(" ");
            for(int cnt=0; cnt<s.length; cnt++){
                time[i][cnt]=Integer.parseInt(s[cnt]);
            }
        }

        return Result.succ(time);
    }

    //管理员修改某节课开始结束时间
    @PostMapping("/times")
    public Result changeTime(@RequestBody Times times){
        if(timesService.findByName(times.getName())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在此节课，无法修改");
        }
        timesService.changeTimeByName(times.getName(),times.getStartTime(),times.getEndTime());
        return Result.succ("修改成功");
    }

    //管理员增加节数
    @PostMapping("/times/new")
    public Result addTime(@RequestBody Times times){
        if(timesService.findByName(times.getName())!=null){
            //存在同名节数
            response.setStatus(WRONG_RES);
            return Result.fail("已存在同名节数，无法添加");
        }
        timesService.add(times);
        return Result.succ("添加成功");
    }

    //管理员删除节数
    @DeleteMapping("/times")
    public Result deleteTime(@RequestBody Times times){
        if(timesService.findByName(times.getName())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在此节课，无法删除");
        }
        timesService.deleteByName(times.getName());
        return Result.succ("删除成功");
    }

    //管理员增加楼
    @PostMapping("/building/new")
    public Result addBuilding(@RequestBody Building building){
        if(buildingService.findByName(building.getFullName())!=null){
            //存在同名楼
            response.setStatus(WRONG_RES);
            return Result.fail("已存在同名教学楼，无法添加");
        }
        //新增加的building的room应该是null
        buildingService.add(building);
        return Result.succ("添加成功");
    }

    //管理员删楼
    @DeleteMapping("/building")
    public Result deleteBuilding(@RequestBody Building building){
        if(buildingService.findByName(building.getFullName())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在此教学楼，无法删除");
        }
        //连带删除对应的教室
        buildingService.deleteByName(building.getFullName());
        return Result.succ("删除成功");
    }

    //管理员改楼名
    @PostMapping("/building")
    public Result changeBuilding(@RequestBody Building building){
        if(buildingService.findByName(building.getFullName())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在此教学楼，无法修改");
        }
        //用id找到对应的楼，然后把全称和简写都改了
        buildingService.changeById(building);
        return Result.succ("修改成功");
    }

    //管理员增加教室
    @PostMapping("/room/new")
    public Result addRoom(@RequestBody Classroom classRoom){
        if(classRoomService.findByNumAndBuilding(classRoom.getRoomNum(),classRoom.getBuilding())!=null){
            //存在同名教室
            response.setStatus(WRONG_RES);
            return Result.fail("已存在同名教室，无法添加");
        }
        classRoomService.add(classRoom);
        return Result.succ("添加成功");
    }

    //管理员删教室
    @DeleteMapping("/room")
    public Result deleteRoom(@RequestBody Classroom classRoom){
        if(classRoomService.findById(classRoom.getRoomId())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在该教室");
        }
        classRoomService.deleteById(classRoom.getRoomId());
        return Result.succ("删除成功");
    }

    //管理员改教室名
    @PostMapping("/room")
    public Result changeRoom(@RequestBody Classroom classRoom){
        if(classRoomService.findById(classRoom.getRoomId())==null){
            //不存在
            response.setStatus(WRONG_RES);
            return Result.fail("不存在该教室");
        }
        //用id找到对应的教室，然后把名字改了
        classRoomService.changeById(classRoom);
        return Result.succ("添加成功");
    }

    //选课权限开关
    @PostMapping("/curriculaVariable")
    public Result changeCurrVariable(@RequestBody boolean choice){
        adminService.setCurr(choice);
        return Result.succ("修改选课权限成功");
    }
}
