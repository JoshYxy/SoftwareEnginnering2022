//package com.jwsystem.dao;
//
//<<<<<<< Updated upstream
//import com.jwsystem.entity.course.Timepart;
//=======
//import com.jwsystem.dto.TimepartDTO;
//>>>>>>> Stashed changes
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.List;
//
//@Mapper
//public interface TimepartDao {
//    List<TimepartDTO> getAllTimepart(Integer courseId);
//    List<TimepartDTO> getAllReqTimepartByRequestId(int requestId);
//    List<String> selectSectionByTea(@Param("teacher_num") String teacherNum, Integer weekday);
//    List<String> selectSectionByRoom(@Param("room_num")String roomNum,Integer weekday,String building);
//    void insertTimepart(TimepartDTO timepartDTO);
//    void insertReqTimepart(TimepartDTO timepartDTO);
//    List<TimepartDTO> getAllTimeByRoom(String building, @Param("room_num") String roomNum);
//    List<TimepartDTO> getAllTimeByTeacherNum(String number);
//    List<Integer> getCourseIdByBuilding(String building);
//    List<Integer> getRequestIdByBuilding(String building);
//    List<Integer> getCourseIdByRoom(String building,  @Param("room_num")String roomNum);
//    List<Integer> getRequestIdByRoom(String building,  @Param("room_num")String roomNum);
//    List<String> getAllSections();
//}
