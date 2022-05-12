/*
 Navicat Premium Data Transfer

 Source Server         : ww本机
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : 111

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 12/05/2022 10:09:19
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
INSERT INTO `admin` VALUES ('admin', '1', '111', '一轮选课结束');

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
INSERT INTO `classroom` VALUES (1, 3, '101', '100');
INSERT INTO `classroom` VALUES (2, 3, '102', '100');
INSERT INTO `classroom` VALUES (3, 3, '103', '100');
INSERT INTO `classroom` VALUES (4, 2, '101', '100');
INSERT INTO `classroom` VALUES (5, 2, '102', '100');
INSERT INTO `classroom` VALUES (6, 2, '103', '100');
INSERT INTO `classroom` VALUES (7, 3, '201', '100');
INSERT INTO `classroom` VALUES (8, 7, '101', '100');
INSERT INTO `classroom` VALUES (9, 7, '102', '100');

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `college_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院名',
  PRIMARY KEY (`college_id`) USING BTREE,
  INDEX `college_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES (7, '外国语言文学学院');
INSERT INTO `college` VALUES (1, '微电子学院');
INSERT INTO `college` VALUES (2, '数学科学学院');
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
  `is_general` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否为通识课程',
  `college_id` int(0) NOT NULL COMMENT '学院',
  `teacher_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '老师',
  PRIMARY KEY (`course_id`) USING BTREE,
  UNIQUE INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `fk_teacher_num`(`teacher_num`) USING BTREE,
  INDEX `fk_course_college_id`(`college_id`) USING BTREE,
  CONSTRAINT `fk_course_college_id` FOREIGN KEY (`college_id`) REFERENCES `college` (`college_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tea_course` FOREIGN KEY (`teacher_num`) REFERENCES `teacher` (`number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coursepart
-- ----------------------------
INSERT INTO `coursepart` VALUES (1, 'MICR130002', '半导体物理', '20', '3', '微电子通选', '2', '2021-2022', '春', '通选课程', 1, '20000001');
INSERT INTO `coursepart` VALUES (2, 'MATH120010', '解析几何', '30', '4', '数院专选', '50', '2020-2021', '春', '专业课程', 2, '20000002');
INSERT INTO `coursepart` VALUES (3, 'MANA130005', '财务管理', '40', '4', '管院专选', '100', '2021-2022', '春', '专业课程', 3, '20000003');
INSERT INTO `coursepart` VALUES (4, 'MANA130008', '金融工程', '10', '1', '管院通选', '50', '2021-2022', '春', '通选课程', 3, '20000003');
INSERT INTO `coursepart` VALUES (5, 'ECON130064', '资本论', '20', '3', '经院通选', '2', '2021-2022', '春', '通选课程', 4, '20000014');
INSERT INTO `coursepart` VALUES (6, 'ECON130099', '博弈论', '30', '4', '经院部分可选', '50', '2020-2021', '春', '面向部分专业课程', 4, '20000004');
INSERT INTO `coursepart` VALUES (7, 'COMP110040', 'Python程序设计', '40', '4', '计院专选', '100', '2021-2022', '春', '专业课程', 5, '20000007');
INSERT INTO `coursepart` VALUES (8, 'COMP110040', 'Python程序设计', '10', '1', '计院专选（同类）', '50', '2021-2022', '春', '专业课程', 5, '20000007');
INSERT INTO `coursepart` VALUES (9, 'COMP110011', 'C程序设计', '20', '3', '计院通选', '2', '2021-2022', '春', '通选课程', 5, '20000018');
INSERT INTO `coursepart` VALUES (10, 'COMP110022', '数据库引论', '30', '4', '计院部分可选', '50', '2020-2021', '春', '面向部分专业课程', 5, '20000018');
INSERT INTO `coursepart` VALUES (11, 'COMP110077', '人工智能', '40', '4', '计院专选', '100', '2021-2022', '春', '专业课程', 5, '20000018');
INSERT INTO `coursepart` VALUES (12, 'COMP110043', 'Java程序设计', '10', '1', '计院通选', '50', '2021-2022', '秋', '通选课程', 5, '20000018');
INSERT INTO `coursepart` VALUES (13, 'COMP110042', '面向对象程序设计', '20', '3', '计院通选', '2', '2021-2022', '春', '通选课程', 5, '20000018');
INSERT INTO `coursepart` VALUES (14, 'COMP110078', '云计算', '30', '4', '计院通选', '50', '2020-2021', '春', '面向部分专业课程', 5, '20000018');
INSERT INTO `coursepart` VALUES (15, 'SOFT130006', '软件工程', '40', '4', '软院专选', '100', '2021-2022', '春', '专业课程', 6, '20000019');
INSERT INTO `coursepart` VALUES (16, 'SOFT130007', '分布式系统', '10', '1', '软院部分可选', '50', '2021-2022', '春', '面向部分专业课程', 6, '20000019');
INSERT INTO `coursepart` VALUES (17, 'SOFT130016', '离散数学', '20', '3', '软院专选', '2', '2021-2022', '春', '专业课程', 6, '20000019');
INSERT INTO `coursepart` VALUES (18, 'SOFT130026', '计算机图形学', '20', '3', '软院通选', '2', '2021-2022', '春', '通选课程', 6, '20000019');
INSERT INTO `coursepart` VALUES (19, 'SOFT130036', '编译原理', '20', '3', '软院专选', '2', '2021-2022', '春', '专业课程', 6, '20000019');
INSERT INTO `coursepart` VALUES (20, 'ENGL230001', '英国文学', '20', '3', '外院部分可选', '2', '2021-2022', '春', '面向部分专业课程', 7, '20000020');
INSERT INTO `coursepart` VALUES (21, 'ENGL230002', '文学翻译鉴赏', '20', '3', '外院通选', '2', '2021-2022', '春', '通选课程', 7, '20000020');
INSERT INTO `coursepart` VALUES (22, 'ENGL230003', '基础俄语', '20', '3', '外院通选', '20', '2021-2022', '春', '通选课程', 7, '20000010');
INSERT INTO `coursepart` VALUES (23, 'ENGL230004', '文化阅读', '20', '3', '外院通选', '2', '2021-2022', '春', '通选课程', 7, '20000010');
INSERT INTO `coursepart` VALUES (24, 'ENGL230005', '基础德语', '20', '3', '外院通选', '30', '2021-2022', '春', '通选课程', 7, '20000020');
INSERT INTO `coursepart` VALUES (25, 'ENGL230006', '英语笔译', '20', '3', '外院部分可选', '2', '2021-2022', '春', '面向部分专业课程', 7, '20000010');
INSERT INTO `coursepart` VALUES (26, 'ENGL230006', '英语笔译', '20', '3', '外院部分可选（同类）', '2', '2021-2022', '秋', '面向部分专业课程', 7, '20000010');
INSERT INTO `coursepart` VALUES (27, 'ENGL230010', '英语论说文写作', '20', '3', '外院通选', '2', '2021-2022', '春', '通选课程', 7, '20000010');
INSERT INTO `coursepart` VALUES (28, 'ENGL230011', '英语应用文写作', '20', '3', '外院通选', '2', '2021-2022', '春', '通选课程', 7, '20000020');

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
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (1, '微电子科学与工程', 1);
INSERT INTO `major` VALUES (2, '数学与应用数学', 2);
INSERT INTO `major` VALUES (3, '数据科学与大数据技术', 2);
INSERT INTO `major` VALUES (4, '财务管理', 3);
INSERT INTO `major` VALUES (5, '会计学', 3);
INSERT INTO `major` VALUES (6, '经济学', 4);
INSERT INTO `major` VALUES (7, '信息安全', 5);
INSERT INTO `major` VALUES (8, '计算机科学与技术', 5);
INSERT INTO `major` VALUES (9, '软件工程', 6);
INSERT INTO `major` VALUES (10, '英语专业', 7);
INSERT INTO `major` VALUES (11, '翻译专业', 7);
INSERT INTO `major` VALUES (12, '俄语专业', 7);
INSERT INTO `major` VALUES (13, '德语专业', 7);

-- ----------------------------
-- Table structure for rela_course_major
-- ----------------------------
DROP TABLE IF EXISTS `rela_course_major`;
CREATE TABLE `rela_course_major`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `course_id` int(0) NULL DEFAULT NULL,
  `major_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_rela_course`(`course_id`) USING BTREE,
  INDEX `fk_rela_major`(`major_id`) USING BTREE,
  CONSTRAINT `fk_rela_course` FOREIGN KEY (`course_id`) REFERENCES `coursepart` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_rela_major` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rela_course_major
-- ----------------------------
INSERT INTO `rela_course_major` VALUES (1, 2, 2);
INSERT INTO `rela_course_major` VALUES (2, 3, 4);
INSERT INTO `rela_course_major` VALUES (3, 6, 4);
INSERT INTO `rela_course_major` VALUES (4, 6, 5);
INSERT INTO `rela_course_major` VALUES (5, 6, 6);
INSERT INTO `rela_course_major` VALUES (6, 7, 8);
INSERT INTO `rela_course_major` VALUES (7, 8, 8);
INSERT INTO `rela_course_major` VALUES (8, 10, 8);
INSERT INTO `rela_course_major` VALUES (9, 10, 9);
INSERT INTO `rela_course_major` VALUES (10, 11, 8);
INSERT INTO `rela_course_major` VALUES (11, 14, 3);
INSERT INTO `rela_course_major` VALUES (12, 14, 7);
INSERT INTO `rela_course_major` VALUES (13, 14, 8);
INSERT INTO `rela_course_major` VALUES (14, 14, 9);
INSERT INTO `rela_course_major` VALUES (15, 15, 9);
INSERT INTO `rela_course_major` VALUES (16, 16, 7);
INSERT INTO `rela_course_major` VALUES (17, 16, 8);
INSERT INTO `rela_course_major` VALUES (18, 16, 9);
INSERT INTO `rela_course_major` VALUES (19, 17, 9);
INSERT INTO `rela_course_major` VALUES (20, 19, 9);
INSERT INTO `rela_course_major` VALUES (21, 20, 10);
INSERT INTO `rela_course_major` VALUES (22, 20, 11);
INSERT INTO `rela_course_major` VALUES (23, 25, 10);
INSERT INTO `rela_course_major` VALUES (24, 25, 11);
INSERT INTO `rela_course_major` VALUES (25, 25, 12);
INSERT INTO `rela_course_major` VALUES (26, 25, 13);
INSERT INTO `rela_course_major` VALUES (27, 26, 10);
INSERT INTO `rela_course_major` VALUES (28, 26, 11);
INSERT INTO `rela_course_major` VALUES (29, 26, 12);
INSERT INTO `rela_course_major` VALUES (30, 26, 13);

-- ----------------------------
-- Table structure for rela_course_student
-- ----------------------------
DROP TABLE IF EXISTS `rela_course_student`;
CREATE TABLE `rela_course_student`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `course_id` int(0) NOT NULL,
  `student_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '选课状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_rela_course_student`(`course_id`) USING BTREE,
  INDEX `fk_rela_student_course`(`student_num`) USING BTREE,
  CONSTRAINT `fk_rela_course_student` FOREIGN KEY (`course_id`) REFERENCES `coursepart` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_rela_student_course` FOREIGN KEY (`student_num`) REFERENCES `student` (`number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  INDEX `fk_req_college`(`college_id`) USING BTREE,
  CONSTRAINT `fk_req_college` FOREIGN KEY (`college_id`) REFERENCES `college` (`college_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_req_id` FOREIGN KEY (`request_id`) REFERENCES `req_teacher` (`request_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of req_coursepart
-- ----------------------------
INSERT INTO `req_coursepart` VALUES (1, 'SOFT130050.01', '高级web技术', '30', '2', '（已被审核）', '30', '通选课程', '2021-2022', '春', 6, '20000002');
INSERT INTO `req_coursepart` VALUES (2, 'SOFT130050.02', '卓越软件', '30', '2', '无', '40', '通选课程', '2021-2022', '春', 6, '20000002');
INSERT INTO `req_coursepart` VALUES (3, 'SOFT130077.01', '计算机组成原理', '40', '2', '无', '20', '专业课程', '2021-2022', '春', 5, '20000002');

-- ----------------------------
-- Table structure for req_rela_course_major
-- ----------------------------
DROP TABLE IF EXISTS `req_rela_course_major`;
CREATE TABLE `req_rela_course_major`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `request_id` int(0) NULL DEFAULT NULL,
  `major_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_rela_course`(`request_id`) USING BTREE,
  INDEX `fk_rela_major`(`major_id`) USING BTREE,
  CONSTRAINT `fk_req_major` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_req_request_major` FOREIGN KEY (`request_id`) REFERENCES `req_teacher` (`request_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of req_rela_course_major
-- ----------------------------
INSERT INTO `req_rela_course_major` VALUES (1, 3, 8);

-- ----------------------------
-- Table structure for req_student
-- ----------------------------
DROP TABLE IF EXISTS `req_student`;
CREATE TABLE `req_student`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '选课申请id',
  `course_id` int(0) NOT NULL COMMENT '课程',
  `student_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请理由',
  `dealt` tinyint(0) NULL DEFAULT NULL COMMENT '处理状态',
  `approved` tinyint(0) NULL DEFAULT NULL COMMENT '审批是否通过',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `fk_request_stu_student`(`student_num`) USING BTREE,
  CONSTRAINT `fk_request_stu_course` FOREIGN KEY (`course_id`) REFERENCES `coursepart` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_request_stu_student` FOREIGN KEY (`student_num`) REFERENCES `student` (`number`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of req_student
-- ----------------------------
INSERT INTO `req_student` VALUES (8, 1, '210003', '理由', 0, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of req_teacher
-- ----------------------------
INSERT INTO `req_teacher` VALUES (1, 'add', NULL, '20000002', 1, 0, 1);
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
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `student` VALUES ('student', '190001', '311321200206071023', 'Andy', 'xlxlll', '', '190001@fudan.edu.cn', 'studying', 7, 5);
INSERT INTO `student` VALUES ('student', '190002', '320683200110300602', 'Jessica', '111222', '', '190002@qq.com', 'studying', 5, 3);
INSERT INTO `student` VALUES ('student', '190003', '311321200106070021', 'Grace', '123456', '', '190003@fudan.edu.cn', 'graduated', 9, 6);
INSERT INTO `student` VALUES ('student', '200010', '311321200106070022', '阿乖', 'xlxlll', '13111702898', '200010@fudan.edu.cn', 'studying', 10, 7);
INSERT INTO `student` VALUES ('student', '200011', '533298200110034561', 'Amy', '123456', '19012127754', '200011@fudan.edu.cn', 'studying', 5, 3);
INSERT INTO `student` VALUES ('student', '200012', '210321200111034561', 'Josh', '123456', '19012128981', '200012@fudan.edu.cn', 'studying', 6, 4);
INSERT INTO `student` VALUES ('student', '210001', '533298200110034563', '小武', '123456', '19012127754', '210001@fudan.edu.cn', 'studying', 5, 3);
INSERT INTO `student` VALUES ('student', '210002', '210321200111034563', '阿罗', '123456', '17869897754', '210002@fudan.edu.cn', 'studying', 6, 4);
INSERT INTO `student` VALUES ('student', '210003', '311321200206070022', '阿俞', 'xlxlll', '13251702891', '210003@fudan.edu.cn', 'studying', 7, 5);
INSERT INTO `student` VALUES ('student', '210004', '320683200110300602', '阿文', '111222', '18068982265', '210004@qq.com', 'studying', 8, 5);
INSERT INTO `student` VALUES ('student', '220001', '533298200110034568', '小罗', '123456', '19012127754', '220001@fudan.edu.cn', 'studying', 1, 1);
INSERT INTO `student` VALUES ('student', '220002', '210321200111034562', '小俞', '123456', '17869897754', '220002@fudan.edu.cn', 'studying', 2, 2);
INSERT INTO `student` VALUES ('student', '220003', '311321200206070023', '小李', 'xlxlll', '13111702898', '220003@fudan.edu.cn', 'studying', 3, 2);
INSERT INTO `student` VALUES ('student', '220004', '320683200110300603', '小文', '111222', '19850336668', 'wwen75421@qq.com', 'studying', 4, 3);
INSERT INTO `student` VALUES ('student', '220005', '311321200106070024', '小滴', 'xlxlll', '13111702898', '220005@fudan.edu.cn', 'studying', 9, 6);

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
INSERT INTO `teacher` VALUES ('teacher', '20000001', '354683199008097641', '老师1', 'yyk', '18958772236', 'pxx@163.com', 'working', 1, 1);
INSERT INTO `teacher` VALUES ('teacher', '20000002', '354683199008097640', '老师2', 'yyk', '18958772236', 'pxx@163.com', 'working', 2, 2);
INSERT INTO `teacher` VALUES ('teacher', '20000003', '276408200207252211', '老师3', '123456', '13127686548', 'Martin@fudan.edu.cn', 'quit', 3, 2);
INSERT INTO `teacher` VALUES ('teacher', '20000004', '321622197912093492', '老师4', '123456', '17318222222', 'zd@fudan.edu.cn', 'quit', 4, 3);
INSERT INTO `teacher` VALUES ('teacher', '20000005', '211298200110034567', '老师5', '123456', '17318271111', 'zdd@fudan.edu.cn', 'working', 5, 3);
INSERT INTO `teacher` VALUES ('teacher', '20000006', '354683199008097641', '朱老师', '123456', '18958772236', 'pxx@163.com', 'working', 6, 4);
INSERT INTO `teacher` VALUES ('teacher', '20000007', '354683199008097640', '李老师', '123456', '18958772236', 'pxx@163.com', 'working', 7, 5);
INSERT INTO `teacher` VALUES ('teacher', '20000008', '276408200207252211', '孙老师', '123456', '13127686548', 'Martin@fudan.edu.cn', 'quit', 8, 5);
INSERT INTO `teacher` VALUES ('teacher', '20000009', '321622197912093492', '陈老师', '123456', '17318222222', 'zd@fudan.edu.cn', 'quit', 9, 6);
INSERT INTO `teacher` VALUES ('teacher', '20000010', '211298200110034567', '赵老师', '123456', '17318271111', 'zdd@fudan.edu.cn', 'working', 10, 7);
INSERT INTO `teacher` VALUES ('teacher', '20000011', '354683199008097641', 'David', '123456', '18958772236', 'pxx@163.com', 'working', 1, 1);
INSERT INTO `teacher` VALUES ('teacher', '20000012', '354683199008097640', '何老师', '123456', '18958772236', 'pxx@163.com', 'working', 2, 2);
INSERT INTO `teacher` VALUES ('teacher', '20000013', '276408200207252211', '叶老师', '123456', '13127686548', 'Martin@fudan.edu.cn', 'working', 3, 2);
INSERT INTO `teacher` VALUES ('teacher', '20000014', '321622197912093492', '周老师', '123456', '17318222222', 'zd@fudan.edu.cn', 'working', 4, 3);
INSERT INTO `teacher` VALUES ('teacher', '20000015', '211298200110034567', '张老师', '123456', '17318271111', 'zdd@fudan.edu.cn', 'working', 5, 3);
INSERT INTO `teacher` VALUES ('teacher', '20000016', '354683199008097641', '彭老师', '123456', '18958772236', 'pxx@163.com', 'working', 6, 4);
INSERT INTO `teacher` VALUES ('teacher', '20000017', '354683199008097640', '肖老师', '123456', '18958772236', '', 'working', 7, 5);
INSERT INTO `teacher` VALUES ('teacher', '20000018', '276408200207252211', 'Alice', '123456', '13127686548', 'Alice@fudan.edu.cn', 'working', 8, 5);
INSERT INTO `teacher` VALUES ('teacher', '20000019', '321622197912093492', '陈老师', '123456', '17318222222', 'zd@fudan.edu.cn', 'working', 9, 6);
INSERT INTO `teacher` VALUES ('teacher', '20000020', '211298200110034567', '赵老师', '123456', '17318271111', 'zdd@fudan.edu.cn', 'working', 10, 7);

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
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of timepart
-- ----------------------------
INSERT INTO `timepart` VALUES (1, 1, '20000001', 0, '1 2 3', 1);
INSERT INTO `timepart` VALUES (2, 2, '20000002', 1, '7', 2);
INSERT INTO `timepart` VALUES (3, 3, '20000003', 2, '1 2', 3);
INSERT INTO `timepart` VALUES (4, 4, '20000003', 3, '4 5', 3);
INSERT INTO `timepart` VALUES (5, 5, '20000014', 4, '1 2 3', 4);
INSERT INTO `timepart` VALUES (6, 6, '20000004', 5, '4 5', 4);
INSERT INTO `timepart` VALUES (7, 7, '20000007', 6, '1 2', 5);
INSERT INTO `timepart` VALUES (8, 8, '20000007', 0, '4 5', 5);
INSERT INTO `timepart` VALUES (9, 9, '20000018', 1, '1 2', 5);
INSERT INTO `timepart` VALUES (10, 10, '20000018', 2, '4 5', 5);
INSERT INTO `timepart` VALUES (11, 11, '20000018', 3, '1 2', 5);
INSERT INTO `timepart` VALUES (12, 12, '20000018', 4, '6 7', 5);
INSERT INTO `timepart` VALUES (13, 13, '20000018', 5, '1 2 3', 5);
INSERT INTO `timepart` VALUES (14, 14, '20000018', 4, '9 10', 5);
INSERT INTO `timepart` VALUES (15, 15, '20000019', 2, '1 2', 5);
INSERT INTO `timepart` VALUES (16, 16, '20000019', 4, '8 9', 6);
INSERT INTO `timepart` VALUES (17, 17, '20000019', 1, '9 10', 6);
INSERT INTO `timepart` VALUES (18, 18, '20000019', 3, '4 5', 6);
INSERT INTO `timepart` VALUES (19, 19, '20000019', 5, '9 10', 6);
INSERT INTO `timepart` VALUES (20, 20, '20000020', 6, '4 5', 7);
INSERT INTO `timepart` VALUES (21, 21, '20000020', 2, '1 2 3', 7);
INSERT INTO `timepart` VALUES (22, 22, '20000010', 1, '6 7', 7);
INSERT INTO `timepart` VALUES (23, 23, '20000010', 3, '1 2', 7);
INSERT INTO `timepart` VALUES (24, 24, '20000020', 3, '4 5', 7);
INSERT INTO `timepart` VALUES (25, 25, '20000010', 1, '1 2', 7);
INSERT INTO `timepart` VALUES (26, 26, '20000010', 2, '8 9', 7);
INSERT INTO `timepart` VALUES (27, 27, '20000010', 4, '1 2', 7);
INSERT INTO `timepart` VALUES (28, 28, '20000020', 5, '3 4', 7);

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
INSERT INTO `times` VALUES (6, '第六节', '14:25', '15:10');
INSERT INTO `times` VALUES (7, '第七节', '15:25', '16:05');
INSERT INTO `times` VALUES (8, '第八节', '16:20', '17:05');
INSERT INTO `times` VALUES (9, '第九节', '17:15', '18:00');
INSERT INTO `times` VALUES (10, '第十节', '18:30', '19:15');

SET FOREIGN_KEY_CHECKS = 1;