package com.jwsystem.util;


import com.jwsystem.dto.CourseDTO;
import com.jwsystem.dto.User;
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
            } finally{
                in.close();
            }
        } catch (ArrayIndexOutOfBoundsException | IOException e){
            return null;
        }
        finally{

        }
        return users;
    }

    public static List<CourseDTO> getCourseByCsv(MultipartFile file) throws IOException {
        ArrayList<CourseDTO> courses = new ArrayList<>();

        InputStreamReader in = null;
        try {
            in = new InputStreamReader(file.getInputStream(), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(in);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setCourseName(splitResult(split[0]));
                courseDTO.setCourseNum(splitResult(split[1]));
                courseDTO.setCollegeName(splitResult(split[2]));
                courseDTO.setClassHours(splitResult(split[3]));
                courseDTO.setCredits(splitResult(split[4]));
                courseDTO.setTeacherName(splitResult(split[5]));
                courseDTO.setTeacherNum(splitResult(split[6]));
                courseDTO.setCourseInfo(splitResult(split[7]));
                courseDTO.setBuilding(splitResult(split[8]));
                courseDTO.setRoomNum(splitResult(split[9]));
                courseDTO.setCapacity(splitResult(split[10]));
                courseDTO.setMon(splitResult(split[11]));
                courseDTO.setTue(splitResult(split[12]));
                courseDTO.setWed(splitResult(split[13]));
                courseDTO.setThu(splitResult(split[14]));
                courseDTO.setFri(splitResult(split[15]));
                courseDTO.setSat(splitResult(split[16]));
                courseDTO.setSun(splitResult(split[17]));
                courses.add(courseDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        return courses;
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
