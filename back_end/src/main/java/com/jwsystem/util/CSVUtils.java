package com.jwsystem.util;


import com.jwsystem.entity.Course;
import com.jwsystem.entity.User;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Component
public class CSVUtils {
    /**
     * 解析csv文件并转成bean（方法一）
     *
     * @param file
     * @return
     */

    //对list的处理user.setlist;
    public static List<User> getUserByCsv(MultipartFile file) {
        ArrayList<User> users = new ArrayList<>();

        InputStreamReader in = null;
        try{
            try {
                in = new InputStreamReader(file.getInputStream(), "utf-8");
                BufferedReader bufferedReader = new BufferedReader(in);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] split = line.split(",");
                    User user = new User();
                    user.setRole(splitResult(split[0]));
                    user.setNumber(splitResult(split[1]));
                    user.setId(splitResult(split[2]));
                    user.setName(splitResult(split[3]));
                    user.setPassword(splitResult(split[4]));
                    user.setPhone(splitResult(split[5]));
                    user.setEmail(splitResult(split[6]));
                    user.setStatus(splitResult(split[7]));
                    user.setMajor(splitResult(split[8]));
                    user.setCollege(splitResult(split[9]));
                    users.add(user);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
        return users;
    }

    public static List<Course> getCourseByCsv(MultipartFile file) {
        ArrayList<Course> courses = new ArrayList<>();

        InputStreamReader in = null;
        try {
            in = new InputStreamReader(file.getInputStream(), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(in);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");
                Course course = new Course();
                course.setCourseName(splitResult(split[0]));
                course.setCourseNum(splitResult(split[1]));
                course.setCollegeName(splitResult(split[2]));
                course.setClassHours(splitResult(split[3]));
                course.setCredits(splitResult(split[4]));
                course.setTeacherName(splitResult(split[5]));
                course.setTeacherNum(splitResult(split[6]));
                course.setCourseInfo(splitResult(split[7]));
                course.setBuilding(splitResult(split[8]));
                course.setRoomNum(splitResult(split[9]));
                course.setCapacity(splitResult(split[10]));
                course.setMon(splitResult(split[11]));
                course.setTue(splitResult(split[12]));
                course.setWed(splitResult(split[13]));
                course.setThu(splitResult(split[14]));
                course.setFri(splitResult(split[15]));
                course.setSat(splitResult(split[16]));
                course.setSun(splitResult(split[17]));
                courses.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * 解析csv文件并转成bean（方法二）
     *
     * @param file csv文件
     * @return 数组
     */
    public static List<String[]> getCsvDataMethod2(MultipartFile file) {

        List<String[]> list = new ArrayList<String[]>();
        int i = 0;
        try {
            CSVReader csvReader = new CSVReaderBuilder(
                    new BufferedReader(
                            new InputStreamReader(file.getInputStream(), "utf-8"))).build();
            Iterator<String[]> iterator = csvReader.iterator();
            while (iterator.hasNext()) {
                String[] next = iterator.next();
                //去除第一行的表头，从第二行开始
                if (i >= 1) {
                    list.add(next);
                }
                i++;
            }
            return list;
        } catch (Exception e) {
            System.out.println("CSV文件读取异常");
            return list;
        }
    }


    /**
     * 解析csv文件并转成bean（方法三）
     *
     * @param file  csv文件
     * @param clazz 类
     * @param <T>   泛型
     * @return 泛型bean集合
     */
    public static <T> List<T> getCsvDataMethod3(MultipartFile file, Class<T> clazz) {
        InputStreamReader in = null;
        CsvToBean<T> csvToBean = null;
        try {
            in = new InputStreamReader(file.getInputStream(), "utf-8");
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(clazz);
            csvToBean = new CsvToBeanBuilder<T>(in).withMappingStrategy(strategy).build();
        } catch (Exception e) {
            return null;
        }
        return csvToBean.parse();
    }

    private static String splitResult(String once) {
        String result = "";
        for (int i = 0; i < once.length(); i++) {
            if (once.charAt(i) != '"') {
                result += once.charAt(i);
            }
        }
        return result;
    }

}
