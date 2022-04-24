package com.jwsystem.util;

import com.jwsystem.entity.course.Coursepart;
import com.jwsystem.vo.CourseVO;
import com.jwsystem.entity.course.Timepart;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

//将数据库内存储的课程信息转换为CourseVO对象的工具类
@Data
@Component
public class CourseUtil {

    public CourseVO transToVO(Coursepart coursePart, List<Timepart> timepartList){

        CourseVO VO = new CourseVO();

        VO.setCourseId(coursePart.getRelationId());
        VO.setCourseName(coursePart.getCourseName());
        VO.setCourseNum(coursePart.getCourseNum());
        VO.setCollegeName(coursePart.getCollegeName());
        VO.setClassHours(coursePart.getClassHours());
        VO.setCredits(coursePart.getCredits());
        VO.setTeacherNum(coursePart.getTeacherNum());
        VO.setTeacherName(coursePart.getTeacherName());
        VO.setCourseInfo(coursePart.getCourseInfo());
        VO.setBuilding(timepartList.get(0).getBuilding());
        VO.setRoomNum(timepartList.get(0).getRoomNum());
        VO.setCapacity(coursePart.getCapacity());

        //设置时间部分
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
        VO.setTimes(time);
        return VO;
    }

}
