/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80033
Source Host           : localhost:3306
Source Database       : community_management

Target Server Type    : MYSQL
Target Server Version : 80033
File Encoding         : 65001

Date: 2024-04-17 08:37:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `activity_place` varchar(255) NOT NULL,
  `community_id` int NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES ('1', '篮球赛', '唱跳rap篮球', '篮球场', '1', '2024-03-13 20:22:20', '2024-03-13 20:22:23');
INSERT INTO `activity` VALUES ('2', '歌唱比赛', '每秒律动', '水上莲花', '1', '2024-04-09 08:13:03', '2024-05-15 08:13:03');
INSERT INTO `activity` VALUES ('3', '足球赛', '看牛逼足球，111111过牛逼人生,...................................................1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111', '体育场', '1', '2024-02-08 00:00:00', '2024-02-10 00:00:00');
INSERT INTO `activity` VALUES ('6', '羽毛球比赛', '羽毛球大赛一触即发', '', '2', '2024-04-10 14:00:01', '2024-04-10 16:00:01');
INSERT INTO `activity` VALUES ('8', '牛牛牛', '', '', '1', null, null);
INSERT INTO `activity` VALUES ('9', '乒乓球比赛', 'fff', 'fff', '2', '2024-04-18 00:00:00', '2024-05-15 00:00:00');
INSERT INTO `activity` VALUES ('10', '小活动', '你爱我我爱你xxx', '胡士打道', '2', '2024-04-04 11:42:24', '2024-04-06 11:42:25');
INSERT INTO `activity` VALUES ('11', 'test', '', '篮球场', '1', null, null);
INSERT INTO `activity` VALUES ('12', 'ddd', '', '', '2', null, null);
INSERT INTO `activity` VALUES ('13', '小伙的那个', '', '', '0', null, null);

-- ----------------------------
-- Table structure for activity_applications
-- ----------------------------
DROP TABLE IF EXISTS `activity_applications`;
CREATE TABLE `activity_applications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `activity_id` bigint NOT NULL COMMENT '活动id',
  `status` int DEFAULT '0' COMMENT '0待审核、1已批准、2已拒绝',
  `application_time` datetime DEFAULT NULL COMMENT '申请时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of activity_applications
-- ----------------------------

-- ----------------------------
-- Table structure for activity_user
-- ----------------------------
DROP TABLE IF EXISTS `activity_user`;
CREATE TABLE `activity_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '活动学生中间表id',
  `activity_id` int NOT NULL COMMENT '活动id',
  `user_id` int NOT NULL COMMENT '参加活动用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of activity_user
-- ----------------------------

-- ----------------------------
-- Table structure for club_applications
-- ----------------------------
DROP TABLE IF EXISTS `club_applications`;
CREATE TABLE `club_applications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `community_id` bigint NOT NULL,
  `status` int DEFAULT '0' COMMENT '0待审核、1已批准、2已拒绝',
  `application_time` datetime DEFAULT NULL COMMENT '申请时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of club_applications
-- ----------------------------
INSERT INTO `club_applications` VALUES ('1', '1', '1', '1', '2024-04-14 20:27:42', '2024-04-14 20:27:49');
INSERT INTO `club_applications` VALUES ('2', '11', '11', '1', '2024-04-14 21:24:19', null);

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `community_name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of community
-- ----------------------------
INSERT INTO `community` VALUES ('1', '篮球社', '我们是一个爱打篮球的小团体~');
INSERT INTO `community` VALUES ('3', '舞蹈社', 'dissdiss');
INSERT INTO `community` VALUES ('4', '相声社', '哇啦哇啦');
INSERT INTO `community` VALUES ('12', '1', '222');
INSERT INTO `community` VALUES ('13', '的方法的', '111');
INSERT INTO `community` VALUES ('14', '1111', '111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111');
INSERT INTO `community` VALUES ('15', '1', '121');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '社团管理员');
INSERT INTO `role` VALUES ('2', '社长');
INSERT INTO `role` VALUES ('3', '社团成员');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NOT NULL DEFAULT '123456' COMMENT '用户登录密码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户姓名',
  `sex` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `community_id` int DEFAULT NULL COMMENT '所属社团id',
  `role_id` int NOT NULL DEFAULT '2' COMMENT '用户角色（1.管理员 2.普通用户）',
  `status` int NOT NULL DEFAULT '1' COMMENT '用户是否正常（1.是 2.否）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'lisi', 'e10adc3949ba59abbe56e057f20f883e', '小明', '男', '15797369435', '1', '1', '1');
INSERT INTO `user` VALUES ('2', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '小a', '女', '111111', '2', '2', '1');
INSERT INTO `user` VALUES ('3', 'ivan', 'e10adc3949ba59abbe56e057f20f883e', '社员', '男', null, '3', '1', '1');
INSERT INTO `user` VALUES ('8', 'admin1', 'd41d8cd98f00b204e9800998ecf8427e', '社员', '男', null, '3', '3', '1');
INSERT INTO `user` VALUES ('11', 'admin2', 'e10adc3949ba59abbe56e057f20f883e', '社员', '女', null, '3', '3', '1');
INSERT INTO `user` VALUES ('13', 'admin111', 'e10adc3949ba59abbe56e057f20f883e', 'lisi', '女', null, '3', '3', '1');
INSERT INTO `user` VALUES ('41', 'admin22', 'e10adc3949ba59abbe56e057f20f883e', 'admin22', '男', '', '3', '2', '1');
INSERT INTO `user` VALUES ('43', 'lisi1', '10ed1697617fe7758b4236d5b791286c', '小明', '男', '', '1', '3', '1');
INSERT INTO `user` VALUES ('44', 'xiaohu', 'b59c67bf196a4758191e42f76670ceba', '小虎', '男', '', '3', '3', '1');
INSERT INTO `user` VALUES ('45', 'xiaohong', 'b59c67bf196a4758191e42f76670ceba', '小红', '女', '1322720', '3', '2', '2');
INSERT INTO `user` VALUES ('46', 'kamen', 'f4cc399f0effd13c888e310ea2cf5399', '卡拉米', '女', '121212121', '1', '2', '1');
INSERT INTO `user` VALUES ('47', 'yuange', '373557abfab162857cfa80465786374a', '元歌', '男', null, null, '3', '1');
