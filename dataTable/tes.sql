/*
 Navicat Premium Data Transfer

 Source Server         : ww本机
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : tes

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 12/04/2022 23:17:30
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
  `curricular_variable` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0,
  PRIMARY KEY (`number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', '1', '111', 1);

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
  `building` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `room_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`room_id`) USING BTREE,
  INDEX `room_num`(`room_num`) USING BTREE,
  INDEX `building`(`building`, `room_num`) USING BTREE,
  INDEX `building_2`(`building`) USING BTREE,
  CONSTRAINT `fk_class_building` FOREIGN KEY (`building`) REFERENCES `building` (`abbr_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classroom
-- ----------------------------
INSERT INTO `classroom` VALUES (1, 'H2', '101');
INSERT INTO `classroom` VALUES (2, 'H2', '102');
INSERT INTO `classroom` VALUES (3, 'H2', '103');
INSERT INTO `classroom` VALUES (4, 'H3', '101');
INSERT INTO `classroom` VALUES (5, 'H3', '102');
INSERT INTO `classroom` VALUES (6, 'H3', '103');
INSERT INTO `classroom` VALUES (7, 'H3', '201');
INSERT INTO `classroom` VALUES (8, 'HGX', '101');
INSERT INTO `classroom` VALUES (9, 'HGX', '102');

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `college_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院名',
  PRIMARY KEY (`college_id`) USING BTREE,
  UNIQUE INDEX `uk_college_name`(`name`) USING BTREE,
  INDEX `college_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  `course_id` int(0) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `course_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `class_hours` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `credits` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `course_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `college_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `teacher_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `teacher_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `capacity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`course_id`) USING BTREE,
  UNIQUE INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `fk_teacher_num`(`teacher_num`) USING BTREE,
  INDEX `fk_college_name`(`college_name`) USING BTREE,
  INDEX `fk_teacher_name`(`teacher_name`) USING BTREE,
  CONSTRAINT `fk_college_name` FOREIGN KEY (`college_name`) REFERENCES `college` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_teacher_name` FOREIGN KEY (`teacher_name`) REFERENCES `teacher` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_teacher_num` FOREIGN KEY (`teacher_num`) REFERENCES `teacher` (`number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coursepart
-- ----------------------------
INSERT INTO `coursepart` VALUES (1, 'Python程序设计', 'COMP110040.01', '20', '3', '好课！', '计算机科学技术学院', '20000001', '老师1', '50');
INSERT INTO `coursepart` VALUES (2, '数据库引论', 'COMP110040.02', '30', '4', '需要恶补的课', '计算机科学技术学院', '20000001', '老师1', '50');
INSERT INTO `coursepart` VALUES (3, '软件工程', 'SOFT130006.01', '40', '4', '无', '软件学院', '20000002', '老师2', '100');
INSERT INTO `coursepart` VALUES (4, '离散数学', 'SOFT130040.01', '30', '3', '不是一般人能上懂的课', '软件学院', '20000002', '老师2', '100');

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `major_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '专业',
  `college_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应的学院',
  PRIMARY KEY (`major_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `fk_major_college`(`college_name`) USING BTREE,
  CONSTRAINT `fk_major_college` FOREIGN KEY (`college_name`) REFERENCES `college` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (1, '保密技术', '计算机科学技术学院');
INSERT INTO `major` VALUES (2, '计算机科学与技术', '计算机科学技术学院');
INSERT INTO `major` VALUES (3, '软件工程', '软件学院');
INSERT INTO `major` VALUES (4, '经济学', '经济学院');

-- ----------------------------
-- Table structure for req_coursepart
-- ----------------------------
DROP TABLE IF EXISTS `req_coursepart`;
CREATE TABLE `req_coursepart`  (
  `request_id` int(0) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `course_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `class_hours` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `credits` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `course_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `college_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `teacher_num` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `teacher_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `capacity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  UNIQUE INDEX `course_id`(`request_id`) USING BTREE,
  INDEX `fk_req_teacher_num`(`teacher_num`) USING BTREE,
  INDEX `fk_req_teacher_name`(`teacher_name`) USING BTREE,
  INDEX `fk_req_college_name`(`college_name`) USING BTREE,
  CONSTRAINT `fk_req_college_name` FOREIGN KEY (`college_name`) REFERENCES `college` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_req_id` FOREIGN KEY (`request_id`) REFERENCES `request` (`request_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_req_teacher_name` FOREIGN KEY (`teacher_name`) REFERENCES `teacher` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of req_coursepart
-- ----------------------------
INSERT INTO `req_coursepart` VALUES (1, '高级web技术', 'SOFT130050.01', '30', '2', '无', '软件学院', '20000002', '老师2', '30');
INSERT INTO `req_coursepart` VALUES (2, '卓越软件', 'SOFT130050.02', '30', '2', '无', '软件学院', '20000002', '老师2', '40');
INSERT INTO `req_coursepart` VALUES (3, '面向对象设计', 'SOFT130050.03', '40', '2', '无', '软件学院', '20000002', '老师2', '20');

-- ----------------------------
-- Table structure for req_timepart
-- ----------------------------
DROP TABLE IF EXISTS `req_timepart`;
CREATE TABLE `req_timepart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `request_id` int(0) NULL DEFAULT NULL,
  `teacher_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `building` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `room_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `weekday` int(0) NULL DEFAULT NULL,
  `section` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_request_id`(`request_id`) USING BTREE,
  INDEX `fk_tea_num`(`teacher_num`) USING BTREE,
  INDEX `fk_req_room_num`(`room_num`) USING BTREE,
  INDEX `fk_req_building`(`building`) USING BTREE,
  INDEX `fk_req_building_room_num`(`building`, `room_num`) USING BTREE,
  CONSTRAINT `fk_req_building_room_num` FOREIGN KEY (`building`, `room_num`) REFERENCES `classroom` (`building`, `room_num`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_request_id` FOREIGN KEY (`request_id`) REFERENCES `request` (`request_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of req_timepart
-- ----------------------------
INSERT INTO `req_timepart` VALUES (1, 1, '20000002', 'H3', '101', 5, '1 2 3');
INSERT INTO `req_timepart` VALUES (2, 2, '20000002', 'HGX', '101', 5, '4 5');
INSERT INTO `req_timepart` VALUES (3, 3, '20000002', 'HGX', '102', 6, '3 4');

-- ----------------------------
-- Table structure for request
-- ----------------------------
DROP TABLE IF EXISTS `request`;
CREATE TABLE `request`  (
  `request_id` int(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `course_id` int(0) NULL DEFAULT NULL,
  `teacher_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `building` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `room_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `examined` tinyint(1) NULL DEFAULT 0,
  `passed` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`request_id`) USING BTREE,
  INDEX `fk_tea_num_request`(`teacher_num`) USING BTREE,
  INDEX `fk_building_req`(`building`) USING BTREE,
  INDEX `fk_room_req`(`room_num`) USING BTREE,
  CONSTRAINT `fk_building_req` FOREIGN KEY (`building`) REFERENCES `classroom` (`building`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_room_req` FOREIGN KEY (`room_num`) REFERENCES `classroom` (`room_num`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tea_num_request` FOREIGN KEY (`teacher_num`) REFERENCES `teacher` (`number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of request
-- ----------------------------
INSERT INTO `request` VALUES (1, 'add', NULL, '20000002', 'H3', '101', 0, 0);
INSERT INTO `request` VALUES (2, 'add', NULL, '20000002', 'HGX', '101', 0, 0);
INSERT INTO `request` VALUES (3, 'add', NULL, '20000002', 'HGX', '102', 0, 0);

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
  `major` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
  `college` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学院',
  PRIMARY KEY (`number`) USING BTREE,
  INDEX `fk_student_college_major`(`major`, `college`) USING BTREE,
  INDEX `fk_student_college`(`college`) USING BTREE,
  CONSTRAINT `fk_student_college` FOREIGN KEY (`college`) REFERENCES `college` (`name`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_student_major` FOREIGN KEY (`major`) REFERENCES `major` (`name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('student', '220001', '533298200110034568', '小罗', '123456', '19012127754', '220001@fudan.edu.cn', 'studying', '软件工程', '软件学院');
INSERT INTO `student` VALUES ('student', '220002', '210321200111034562', '小俞', '123456', '17869897754', '220002@fudan.edu.cn', 'studying', '计算机科学与技术', '计算机科学技术学院');
INSERT INTO `student` VALUES ('student', '220003', '311321200206070023', '小李', 'xlxlll', '13111702898', '220003@fudan.edu.cn', 'studying', '软件工程', '软件学院');
INSERT INTO `student` VALUES ('student', '220004', '320683200110300603', '小文', 'Ww111', '19850336668', 'wwen75421@qq.com', 'studying', '软件工程', '软件学院');

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
  `major` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
  `college` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学院',
  PRIMARY KEY (`number`) USING BTREE,
  INDEX `fk_teacher_college`(`college`) USING BTREE,
  INDEX `fk_teacher_major`(`major`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  CONSTRAINT `fk_teacher_college` FOREIGN KEY (`college`) REFERENCES `college` (`name`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_teacher_major` FOREIGN KEY (`major`) REFERENCES `major` (`name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('teacher', '20000001', '410622197912093492', '老师1', '123456', '17318271111', 'zdd@fudan.edu.cn', 'working', '计算机科学与技术', '计算机科学技术学院');
INSERT INTO `teacher` VALUES ('teacher', '20000002', '354683199008097640', '老师2', '123456', '18958772236', 'pxx@163.com', 'working', '软件工程', '软件学院');
INSERT INTO `teacher` VALUES ('teacher', '20000003', '276408200207252211', '老师3', '123456', '13127686548', 'Martin@fudan.edu.cn', 'quit', '计算机科学与技术', '计算机科学技术学院');
INSERT INTO `teacher` VALUES ('teacher', '20000004', '321622197912093492', '老师4', '123456', '17318222222', 'zd@fudan.edu.cn', 'quit', '计算机科学与技术', '计算机科学技术学院');
INSERT INTO `teacher` VALUES ('teacher', '20000005', '211298200110034567', '老师5', '123456', '17318271111', 'zdd@fudan.edu.cn', 'working', '经济学', '经济学院');

-- ----------------------------
-- Table structure for timepart
-- ----------------------------
DROP TABLE IF EXISTS `timepart`;
CREATE TABLE `timepart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `course_id` int(0) NULL DEFAULT NULL,
  `teacher_num` int(0) NULL DEFAULT NULL,
  `building` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `room_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `weekday` int(0) NULL DEFAULT NULL,
  `section` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_course_id_timepart`(`course_id`) USING BTREE,
  INDEX `fk_building`(`building`) USING BTREE,
  INDEX `fk_room_num`(`room_num`) USING BTREE,
  INDEX `fk_building_room_num`(`building`, `room_num`) USING BTREE,
  CONSTRAINT `fk_building_room_num` FOREIGN KEY (`building`, `room_num`) REFERENCES `classroom` (`building`, `room_num`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_course_id_timepart` FOREIGN KEY (`course_id`) REFERENCES `coursepart` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of timepart
-- ----------------------------
INSERT INTO `timepart` VALUES (1, 1, 20000001, 'H2', '101', 0, '1 2 3');
INSERT INTO `timepart` VALUES (2, 2, 20000001, 'H2', '102', 1, '4 5');
INSERT INTO `timepart` VALUES (3, 3, 20000002, 'H2', '103', 2, '1 2');
INSERT INTO `timepart` VALUES (4, 4, 20000002, 'H3', '101', 4, '3 4');

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
