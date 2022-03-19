/*
 Navicat Premium Data Transfer

 Source Server         : ww本机
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : jwsystem

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 18/03/2022 15:15:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色',
  `number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '123456' COMMENT '已设置默认值为123456',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('admin', '1', '111111111111111111', 'admin', '123456', NULL, NULL);
INSERT INTO `user` VALUES ('tea', '20000001', '320683198508090021', '朱东东', '123456', '17318271111', 'zdd@fudan.cn');
INSERT INTO `user` VALUES ('tea', '20000002', '410622197912093492', '赵东东', '123456', '17111731821', '');
INSERT INTO `user` VALUES ('stu', '220001', '320683200008090021', '彭小明', '123456', '', '');
INSERT INTO `user` VALUES ('stu', '220002', '354683199008097640', '彭小鑫', 'pxx123', '18958772236', 'pxx@163.com');
INSERT INTO `user` VALUES ('stu', '220003', '340683200666809029', '彭软件', '123456', '17948009879', 'prj@oy.com');
INSERT INTO `user` VALUES ('stu', '220004', '340683200109802349', '彭工程', '123456', '17948009879', 'prj@oy.com');
INSERT INTO `user` VALUES ('stu', '220005', '340111200109802111', '朱来来', '123456', '', '');

SET FOREIGN_KEY_CHECKS = 1;
