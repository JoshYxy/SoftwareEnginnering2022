//package com.jwsystem.dao;
//
//<<<<<<< Updated upstream
//import com.jwsystem.entity.affair.Classroom;
//=======
//import com.jwsystem.entity.affair.ClassroomVO;
//>>>>>>> Stashed changes
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.List;
//
//@Mapper
//public interface ClassroomDao {
//    void add(String building, @Param("room_num") String roomNum);
//
//    void changeById(@Param("room_id")Integer roomId, String building,@Param("room_num") String roomNum);
//
//    void deleteByRoomId(Integer roomId);
//
//    //得到所有教室信息
//    List<ClassroomVO> getClassroomsByBuilding(String building);
//
//    ClassroomVO findByNumAndBuilding(@Param("room_num")String roomNum, String building);
//
//    ClassroomVO findById(Integer roomId);
//}
