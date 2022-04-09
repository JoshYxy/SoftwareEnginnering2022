package com.jwsystem.util;

import com.jwsystem.entity.CoursePart;
import com.jwsystem.entity.CourseVO;
import com.jwsystem.entity.TimePart;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//将数据库内存储的课程信息转换为CourseVO对象的工具类
@Data
@Component
public class CourseUtil {

    public CourseVO transToVO(CoursePart coursePart, List<TimePart> timePartList){

        CourseVO VO = new CourseVO();

        VO.setCourseId(coursePart.getCourseId());
        VO.setCourseName(coursePart.getCourseName());
        VO.setCourseNum(coursePart.getCourseNum());
        VO.setCollegeName(coursePart.getCollegeName());
        VO.setClassHours(coursePart.getClassHours());
        VO.setCredits(coursePart.getCredits());
        VO.setTeacherNum(coursePart.getTeacherNum());
        VO.setTeacherName(coursePart.getTeacherName());
        VO.setCourseInfo(coursePart.getCourseInfo());
        VO.setBuilding(timePartList.get(0).getBuilding());
        VO.setRoomNum(timePartList.get(0).getRoomNum());
        VO.setCapacity(coursePart.getCapacity());

        //设置时间部分
        int[][] time = new int[7][];
        for (TimePart t:
             timePartList) {
            int i = t.getWeekday();
            //转字符串为int数组
            String[] s = t.getSection().split(" ");
            for(int cnt=0; cnt<s.length; cnt++){
                time[i][cnt]=Integer.parseInt(s[cnt]);
            }
        }

        VO.setTimes(time);
        return VO;
    }

}
