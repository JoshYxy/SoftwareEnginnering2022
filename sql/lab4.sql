/*
 Navicat Premium Data Transfer

 Source Server         : ww本机
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : lab4

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 30/04/2022 22:47:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'admin',
  `number` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `curricular_variable` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '第一轮 第二轮 未开始 结束',
  PRIMARY KEY (`number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', '1', '111', '1');

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `abbr_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `abbr_name`(`abbr_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of building
-- ----------------------------
INSERT INTO `building` VALUES (1, '第一教学楼', 'H1');
INSERT INTO `building` VALUES (2, '第二教学楼', 'H2');
INSERT INTO `building` VALUES (3, '第三教学楼', 'H3');
INSERT INTO `building` VALUES (4, '第四教学楼', 'H4');
INSERT INTO `building` VALUES (5, '第五教学楼', 'H5');
INSERT INTO `building` VALUES (6, '第六教学楼', 'H6');
INSERT INTO `building` VALUES (7, '光华楼西辅楼', 'HGX');

-- ----------------------------
-- Table structure for classroom
-- ----------------------------
DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom`  (
  `room_id` int(0) NOT NULL AUTO_INCREMENT,
  `building_id` int(0) NULL DEFAULT NULL,
  `room_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `capacity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`room_id`) USING BTREE,
  INDEX `room_num`(`room_num`) USING BTREE,
  INDEX `building`(`building_id`, `room_num`) USING BTREE,
  INDEX `building_2`(`building_id`) USING BTREE,
  CONSTRAINT `fk_building_classroom` FOREIGN KEY (`building_id`) REFERENCES `building` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classroom
-- ----------------------------
INSERT INTO `classroom` VALUES (1, 1, '101', '100');
INSERT INTO `classroom` VALUES (2, 2, '102', '100');
INSERT INTO `classroom` VALUES (3, 3, '103', '100');
INSERT INTO `classroom` VALUES (4, 1, '101', '100');
INSERT INTO `classroom` VALUES (5, 3, '102', '100');
INSERT INTO `classroom` VALUES (6, 3, '103', '100');
INSERT INTO `classroom` VALUES (7, 1, '201', '100');
INSERT INTO `classroom` VALUES (8, 1, '101', '100');
INSERT INTO `classroom` VALUES (9, 1, '102', '100');

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `college_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院名',
  PRIMARY KEY (`college_id`) USING BTREE,
  INDEX `college_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES (1, '微电子学院');
INSERT INTO `college` VALUES (2, '物理学院');
INSERT INTO `college` VALUES (3, '管理学院');
INSERT INTO `college` VALUES (4, '经济学院');
INSERT INTO `college` VALUES (5, '计算机科学技术学院');
INSERT INTO `college` VALUES (6, '软件学院');

-- ----------------------------
-- Table structure for coursepart
-- ----------------------------
DROP TABLE IF EXISTS `coursepart`;
CREATE TABLE `coursepart`  (
  `course_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '课程id',
  `course_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程编号',
  `course_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `class_hours` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学时',
  `credits` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学分',
  `course_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程简介',
  `capacity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程容量',
  `year` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学年',
  `semester` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学期',
  `is_general` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否为通识课程',
  `college_id` int(0) NOT NULL COMMENT '学院',
  `teacher_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '老师',
  PRIMARY KEY (`course_id`) USING BTREE,
  UNIQUE INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `fk_teacher_num`(`teacher_num`) USING BTREE,
  CONSTRAINT `fk_tea_course` FOREIGN KEY (`teacher_num`) REFERENCES `teacher` (`number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coursepart
-- ----------------------------
INSERT INTO `coursepart` VALUES (1, 'COMP110040.01', 'Python程序设计', '20', '3', '好课！', '50', NULL, NULL, '0', 1, '20000001');
INSERT INTO `coursepart` VALUES (2, 'COMP110040.02', '数据库引论', '30', '4', '需要恶补的课', '50', NULL, NULL, '1', 1, '20000001');
INSERT INTO `coursepart` VALUES (3, 'SOFT130006.01', '软件工程', '40', '4', '无', '100', NULL, NULL, '1', 1, '20000002');
INSERT INTO `coursepart` VALUES (4, 'SOFT130040.01', '离散数学', '30', '3', '不是一般人能上懂的课', '100', NULL, NULL, '0', 1, '20000002');

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `major_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '专业',
  `college_id` int(0) NOT NULL COMMENT '对应的学院',
  PRIMARY KEY (`major_id`) USING BTREE,
  INDEX `fk_major_college`(`college_id`) USING BTREE,
  INDEX `major_id`(`major_id`, `college_id`) USING BTREE,
  CONSTRAINT `fk_major_college` FOREIGN KEY (`college_id`) REFERENCES `college` (`college_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (1, '保密技术', 1);
INSERT INTO `major` VALUES (2, '计算机科学与技术', 3);
INSERT INTO `major` VALUES (3, '软件工程', 6);
INSERT INTO `major` VALUES (4, '经济学', 2);

-- ----------------------------
-- Table structure for rela_course_major
-- ----------------------------
DROP TABLE IF EXISTS `rela_course_major`;
CREATE TABLE `rela_course_major`  (
  `id` int(0) NOT NULL,
  `course_id` int(0) NULL DEFAULT NULL,
  `major_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_rela_course`(`course_id`) USING BTREE,
  INDEX `fk_rela_major`(`major_id`) USING BTREE,
  CONSTRAINT `fk_rela_course` FOREIGN KEY (`course_id`) REFERENCES `coursepart` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rela_major` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rela_course_major
-- ----------------------------

-- ----------------------------
-- Table structure for rela_course_student
-- ----------------------------
DROP TABLE IF EXISTS `rela_course_student`;
CREATE TABLE `rela_course_student`  (
  `id` int(0) NOT NULL,
  `course_id` int(0) NOT NULL,
  `student_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '选课状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_rela_course_student`(`course_id`) USING BTREE,
  INDEX `fk_rela_student_course`(`student_num`) USING BTREE,
  CONSTRAINT `fk_rela_course_student` FOREIGN KEY (`course_id`) REFERENCES `coursepart` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_rela_student_course` FOREIGN KEY (`student_num`) REFERENCES `student` (`number`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rela_course_student
-- ----------------------------

-- ----------------------------
-- Table structure for req_coursepart
-- ----------------------------
DROP TABLE IF EXISTS `req_coursepart`;
CREATE TABLE `req_coursepart`  (
  `request_id` int(0) NOT NULL AUTO_INCREMENT,
  `course_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
  `course_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `class_hours` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学时',
  `credits` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学分',
  `course_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程介绍',
  `capacity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师容量',
  `is_general` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否为通识',
  `year` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学年',
  `semester` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学期',
  `college_id` int(0) NOT NULL COMMENT '学院',
  `teacher_num` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '老师',
  UNIQUE INDEX `course_id`(`request_id`) USING BTREE,
  INDEX `fk_req_teacher_num`(`teacher_num`) USING BTREE,
  CONSTRAINT `fk_req_id` FOREIGN KEY (`request_id`) REFERENCES `req_teacher` (`request_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of req_coursepart
-- ----------------------------
INSERT INTO `req_coursepart` VALUES (1, 'SOFT130050.01', '高级web技术', '30', '2', '无', '30', '1', NULL, NULL, 2, '20000001');
INSERT INTO `req_coursepart` VALUES (2, 'SOFT130050.02', '卓越软件', '30', '2', '无', '40', '1', NULL, NULL, 2, '20000002');
INSERT INTO `req_coursepart` VALUES (3, 'SOFT130050.03', '面向对象设计', '40', '2', '无', '20', '1', NULL, NULL, 2, '20000002');

-- ----------------------------
-- Table structure for req_student
-- ----------------------------
DROP TABLE IF EXISTS `req_student`;
CREATE TABLE `req_student`  (
  `id` int(0) NOT NULL COMMENT '选课申请id',
  `course_id` int(0) NOT NULL COMMENT '课程',
  `student_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请理由',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `dealt` tinyint(0) NULL DEFAULT NULL COMMENT '处理状态',
  `approved` tinyint(0) NULL DEFAULT NULL COMMENT '审批是否通过',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `fk_request_stu_student`(`student_num`) USING BTREE,
  CONSTRAINT `fk_request_stu_course` FOREIGN KEY (`course_id`) REFERENCES `coursepart` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_request_stu_student` FOREIGN KEY (`student_num`) REFERENCES `student` (`number`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of req_student
-- ----------------------------

-- ----------------------------
-- Table structure for req_teacher
-- ----------------------------
DROP TABLE IF EXISTS `req_teacher`;
CREATE TABLE `req_teacher`  (
  `request_id` int(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `course_id` int(0) NULL DEFAULT NULL,
  `teacher_num` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `examined` tinyint(1) NULL DEFAULT 0,
  `passed` tinyint(1) NULL DEFAULT 0,
  `room_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`request_id`) USING BTREE,
  INDEX `fk_tea_num_request`(`teacher_num`) USING BTREE,
  INDEX `fk_room_req`(`room_id`) USING BTREE,
  CONSTRAINT `fk_request_room_id` FOREIGN KEY (`room_id`) REFERENCES `classroom` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_request_tea` FOREIGN KEY (`teacher_num`) REFERENCES `teacher` (`number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of req_teacher
-- ----------------------------
INSERT INTO `req_teacher` VALUES (1, 'add', NULL, '20000002', 0, 0, 1);
INSERT INTO `req_teacher` VALUES (2, 'add', NULL, '20000002', 0, 0, 2);
INSERT INTO `req_teacher` VALUES (3, 'add', NULL, '20000002', 0, 0, 3);

-- ----------------------------
-- Table structure for req_timepart
-- ----------------------------
DROP TABLE IF EXISTS `req_timepart`;
CREATE TABLE `req_timepart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `request_id` int(0) NOT NULL,
  `teacher_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `weekday` int(0) NULL DEFAULT NULL,
  `section` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `room_id` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_request_id`(`request_id`) USING BTREE,
  INDEX `fk_tea_num`(`teacher_num`) USING BTREE,
  INDEX `fk_req_room_num`(`room_id`) USING BTREE,
  INDEX `fk_req_building_room_num`(`room_id`) USING BTREE,
  CONSTRAINT `fk_request_id` FOREIGN KEY (`request_id`) REFERENCES `req_teacher` (`request_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_room_id` FOREIGN KEY (`room_id`) REFERENCES `classroom` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of req_timepart
-- ----------------------------
INSERT INTO `req_timepart` VALUES (1, 1, '20000002', 5, '1 2 3', 1);
INSERT INTO `req_timepart` VALUES (2, 2, '20000002', 5, '4 5', 2);
INSERT INTO `req_timepart` VALUES (3, 3, '20000002', 6, '3 4', 3);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'student',
  `number` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学号',
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '123456',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'studying' COMMENT '状态',
  `major_id` int(0) NOT NULL,
  `college_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`number`) USING BTREE,
  INDEX `fk_student_major`(`major_id`, `college_id`) USING BTREE,
  CONSTRAINT `fk_student_major` FOREIGN KEY (`major_id`, `college_id`) REFERENCES `major` (`major_id`, `college_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('student', '220001', '533298200110034568', '小罗', '123456', '19012127754', '220001@fudan.edu.cn', 'studying', 1, NULL);
INSERT INTO `student` VALUES ('student', '220002', '210321200111034562', '小俞', '123456', '17869897754', '220002@fudan.edu.cn', 'studying', 1, NULL);
INSERT INTO `student` VALUES ('student', '220003', '311321200206070023', '小李', 'xlxlll', '13111702898', '220003@fudan.edu.cn', 'studying', 1, NULL);
INSERT INTO `student` VALUES ('student', '220004', '320683200110300603', '小文', 'Ww111', '19850336668', 'wwen75421@qq.com', 'studying', 1, NULL);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'teacher',
  `number` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工号',
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '123456',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'working' COMMENT '状态',
  `major_id` int(0) NOT NULL COMMENT '专业',
  `college_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`number`) USING BTREE,
  INDEX `number`(`number`, `name`) USING BTREE,
  INDEX `fk_teacher_major_college`(`major_id`, `college_id`) USING BTREE,
  CONSTRAINT `fk_teacher_major_college` FOREIGN KEY (`major_id`, `college_id`) REFERENCES `major` (`major_id`, `college_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('teacher', '20000001', '410622197912093492', '老师1', '123456', '17318271111', 'zdd@fudan.edu.cn', 'working', 0, NULL);
INSERT INTO `teacher` VALUES ('teacher', '20000002', '354683199008097640', '老师2', '123456', '18958772236', 'pxx@163.com', 'working', 0, NULL);
INSERT INTO `teacher` VALUES ('teacher', '20000003', '276408200207252211', '老师3', '123456', '13127686548', 'Martin@fudan.edu.cn', 'quit', 0, NULL);
INSERT INTO `teacher` VALUES ('teacher', '20000004', '321622197912093492', '老师4', '123456', '17318222222', 'zd@fudan.edu.cn', 'quit', 0, NULL);
INSERT INTO `teacher` VALUES ('teacher', '20000005', '211298200110034567', '老师5', '123456', '17318271111', 'zdd@fudan.edu.cn', 'working', 0, NULL);

-- ----------------------------
-- Table structure for timepart
-- ----------------------------
DROP TABLE IF EXISTS `timepart`;
CREATE TABLE `timepart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `course_id` int(0) NULL DEFAULT NULL,
  `teacher_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `weekday` int(0) NULL DEFAULT NULL,
  `section` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `room_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_course_id_timepart`(`course_id`) USING BTREE,
  INDEX `fk_timepart_classroom`(`room_id`) USING BTREE,
  CONSTRAINT `fk_course_id_timepart` FOREIGN KEY (`course_id`) REFERENCES `coursepart` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_timepart_classroom` FOREIGN KEY (`room_id`) REFERENCES `classroom` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of timepart
-- ----------------------------
INSERT INTO `timepart` VALUES (1, 1, '20000001', 0, '1 2 3', 1);
INSERT INTO `timepart` VALUES (2, 2, '20000001', 1, '4 5', 2);
INSERT INTO `timepart` VALUES (3, 3, '20000002', 2, '1 2', 3);
INSERT INTO `timepart` VALUES (4, 4, '20000002', 4, '3 4', 4);

-- ----------------------------
-- Table structure for times
-- ----------------------------
DROP TABLE IF EXISTS `times`;
CREATE TABLE `times`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `end_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of times
-- ----------------------------
INSERT INTO `times` VALUES (1, '第一节', '8:00', '8:40');
INSERT INTO `times` VALUES (2, '第二节', '8:55', '9:40');
INSERT INTO `times` VALUES (3, '第三节', '9:50', '10:40');
INSERT INTO `times` VALUES (4, '第四节', '10:50', '11:35');
INSERT INTO `times` VALUES (5, '第五节', '13:30', '14:15');

SET FOREIGN_KEY_CHECKS = 1;
