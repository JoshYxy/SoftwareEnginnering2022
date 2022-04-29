package com.jwsystem.util;


import com.jwsystem.dto.CoursepartDTO;
import com.jwsystem.vo.CourseVO;
import com.jwsystem.dto.TimepartDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

//将数据库内存储的课程信息转换为CourseVO对象的工具类
@Data
@Component
public class CourseUtil {

    public CourseVO transToVO(CoursepartDTO coursePart, List<TimepartDTO> timepartDTOList){

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
        VO.setBuilding(timepartDTOList.get(0).getBuilding());
        VO.setRoomNum(timepartDTOList.get(0).getRoomNum());
        VO.setCapacity(coursePart.getCapacity());

        //设置时间部分
        int[][] time = new int[7][];
        for (TimepartDTO t:
                timepartDTOList) {
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
