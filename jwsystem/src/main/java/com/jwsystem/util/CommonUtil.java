package com.jwsystem.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Component
public class CommonUtil {

    public int getYear(){
        Date date = new Date();
        SimpleDateFormat year=new SimpleDateFormat("YYYY");//设置当前时间的格式，为年
        return Integer.parseInt(year.format(date));
    }

    public int getMonth(){
        Date date = new Date();
        SimpleDateFormat month=new SimpleDateFormat("MM");//设置当前时间的格式，为年
        return Integer.parseInt(month.format(date));
    }

    //获得当前所属学年
    public String getSchoolYear(){
        int year = getYear();
        int month = getMonth();

        String schoolYear;
        if(month>6){
            schoolYear = year + "-" + year+1;
        }
        else{
            schoolYear = year-1 + "-" + year;
        }

        return schoolYear;
    }

    //获得当前所属学期
    public String getSemester(){
        int month = getMonth();
        String semester;

        if(month>6){
            semester = "秋";
        }
        else{
            semester = "春";
        }

        return semester;
    }



}
