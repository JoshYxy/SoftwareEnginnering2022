/*
 Navicat Premium Data Transfer

 Source Server         : ww本机
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : lab3

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 02/04/2022 01:43:05
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
INSERT INTO `admin` VALUES ('admin', '1', '111', 0);

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building`  (
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `abbr_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  INDEX `abbr_name`(`abbr_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of building
-- ----------------------------
INSERT INTO `building` VALUES ('第一教学楼', 'H1');
INSERT INTO `building` VALUES ('第二教学楼', 'H2');
INSERT INTO `building` VALUES ('第三教学楼', 'H3');
INSERT INTO `building` VALUES ('第四教学楼', 'H4');
INSERT INTO `building` VALUES ('第五教学楼', 'H5');
INSERT INTO `building` VALUES ('第六教学楼', 'H6');

-- ----------------------------
-- Table structure for classroom
-- ----------------------------
DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom`  (
  `building` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `class_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  INDEX `fk_class_building`(`building`) USING BTREE,
  CONSTRAINT `fk_class_building` FOREIGN KEY (`building`) REFERENCES `building` (`abbr_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classroom
-- ----------------------------
INSERT INTO `classroom` VALUES ('H2', '110');
INSERT INTO `classroom` VALUES ('H3', '106');

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `college_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学院名',
  PRIMARY KEY (`college_id`) USING BTREE,
  UNIQUE INDEX `uk_college_name`(`name`) USING BTREE,
  INDEX `college_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES (1, '微电子学院');
INSERT INTO `college` VALUES (2, '物理学院');
INSERT INTO `college` VALUES (3, '管理学院');
INSERT INTO `college` VALUES (4, '经济学院');
INSERT INTO `college` VALUES (5, '计算机科学技术学院');

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `major_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
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
INSERT INTO `major` VALUES (3, '软件工程', '计算机科学技术学院');
INSERT INTO `major` VALUES (4, '经济学', '经济学院');

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
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态',
  `major` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
  `college` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学院',
  PRIMARY KEY (`number`) USING BTREE,
  INDEX `fk_student_college_major`(`major`, `college`) USING BTREE,
  INDEX `fk_student_college`(`college`) USING BTREE,
  CONSTRAINT `fk_student_college` FOREIGN KEY (`college`) REFERENCES `college` (`name`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `fk_student_major` FOREIGN KEY (`major`) REFERENCES `major` (`name`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('student', '220001', '533298200110034568', '小罗', '123456', '19012127754', '220001@fudan.edu.cn', 'studying', '软件工程', '计算机科学技术学院');
INSERT INTO `student` VALUES ('student', '220002', '210321200111034562', '小俞', '123456', '17869897754', '220002@fudan.edu.cn', 'studying', '计算机科学与技术', '计算机科学技术学院');
INSERT INTO `student` VALUES ('student', '220003', '311321200206070023', '小李', '123456', '13111702898', '220003@fudan.edu.cn', 'studying', '软件工程', '计算机科学技术学院');
INSERT INTO `student` VALUES ('student', '220004', '320683200110300603', '小文', 'Ww111', '19850336668', 'wwen75421@qq.com', 'studying', '软件工程', '计算机科学技术学院');
INSERT INTO `student` VALUES ('student', '220005', '421798200207253765', '旺仔', '123456', '', '', 'studying', NULL, '计算机科学技术学院');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'teacher',
  `number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工号',
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '123456',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `major` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
  `college` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学院',
  PRIMARY KEY (`number`) USING BTREE,
  INDEX `fk_teacher_college`(`college`) USING BTREE,
  INDEX `fk_teacher_major`(`major`) USING BTREE,
  CONSTRAINT `fk_teacher_college` FOREIGN KEY (`college`) REFERENCES `college` (`name`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `fk_teacher_major` FOREIGN KEY (`major`) REFERENCES `major` (`name`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('teacher', '20000001', '410622197912093492', '朱东东', '123456', '17318271111', 'zdd@fudan.edu.cn', 'working', '计算机科学与技术', '计算机科学技术学院');
INSERT INTO `teacher` VALUES ('teacher', '20000002', '354683199008097640', '彭小新', '123456', '18958772236', 'pxx@163.com', 'working', '计算机科学与技术', '计算机科学技术学院');
INSERT INTO `teacher` VALUES ('teacher', '20000003', '276408200207252211', '马丁', '123456', '13127686548', 'Martin@fudan.edu.cn', 'quit', '计算机科学与技术', '计算机科学技术学院');
INSERT INTO `teacher` VALUES ('teacher', '20000004', '321622197912093492', '朱东', '123456', '17318222222', 'zd@fudan.edu.cn', 'quit', '计算机科学与技术', '计算机科学技术学院');

SET FOREIGN_KEY_CHECKS = 1;
