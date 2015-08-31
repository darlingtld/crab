/*
Navicat MySQL Data Transfer

Source Server         : Songda Mycai
Source Server Version : 50543
Source Host           : 120.26.37.219:3306
Source Database       : crab

Target Server Type    : MYSQL
Target Server Version : 50543
File Encoding         : 65001

Date: 2015-09-01 07:38:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator` (
  `username` varchar(45) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES ('admin', 'crab', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for bill_order
-- ----------------------------
DROP TABLE IF EXISTS `bill_order`;
CREATE TABLE `bill_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `wechat_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `bill` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `order_ts` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `delivery_ts` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyer_info` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `buyer_address` text,
  `consignee` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `consignee_contact` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `confirm_code` varchar(45) DEFAULT NULL,
  `confirm_bill` text,
  `confirm_ts` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bill_order
-- ----------------------------

-- ----------------------------
-- Table structure for card_code
-- ----------------------------
DROP TABLE IF EXISTS `card_code`;
CREATE TABLE `card_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL,
  `used` tinyint(4) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `owner_id` varchar(255) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `consignee` varchar(255) DEFAULT NULL,
  `consignee_contact` varchar(255) DEFAULT NULL,
  `consignee_datetime` datetime DEFAULT NULL,
  `buyer_address` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of card_code
-- ----------------------------
INSERT INTO `card_code` VALUES ('2', '1440578883092', '1', '2015-08-26 16:48:03', '2015-08-26 16:48:03', 'o5Irvt5957jQ4xmdHmDp59epk0UU', '216', '123', '123', '0000-00-00 00:00:00', '123');
INSERT INTO `card_code` VALUES ('3', '1440578883233', '0', '2015-08-26 16:48:03', '2015-08-26 16:48:03', null, '403', null, null, null, null);
INSERT INTO `card_code` VALUES ('4', '1440578883264', '0', '2015-08-26 16:48:03', '2015-08-26 16:48:03', null, '404', null, null, null, null);
INSERT INTO `card_code` VALUES ('5', '1440578883274', '1', '2015-08-26 16:48:03', '2015-08-26 16:48:03', 'o5Irvt5957jQ4xmdHmDp59epk0UU', '405', '123', '123', '2015-08-29 19:16:00', '123');

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_type` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `openid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `reached_money` double(255,2) DEFAULT NULL,
  `deducted_money` double(255,2) DEFAULT NULL,
  `discount_factor` double(255,2) DEFAULT NULL,
  `used` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of coupon
-- ----------------------------

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) DEFAULT NULL,
  `content` text,
  `ts` datetime DEFAULT NULL,
  `has_read` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('20', 'oHHOIjjsul-m8wfqgifTa0aPc18A', '欢迎您关注我们', '2015-08-31 21:36:36', '0');
INSERT INTO `message` VALUES ('21', 'oHHOIjl7ggEbK38j3Y_-y2-myuXA', '欢迎您关注我们', '2015-08-31 22:12:07', '0');

-- ----------------------------
-- Table structure for procurement
-- ----------------------------
DROP TABLE IF EXISTS `procurement`;
CREATE TABLE `procurement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `procprice` double DEFAULT NULL,
  `procindex` double DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of procurement
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `category` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  `unit` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `picurl` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `onsale` int(1) DEFAULT '0',
  `data_change_last_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `order_index` int(11) DEFAULT NULL,
  `detail` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=422 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('215', '大礼包', '大礼包大礼包大礼包大礼包大礼包大礼包大礼包大礼包大礼包大礼包大礼包', 'SHUICHANDONGHUO', 'HAIXIANSHUICHAN', '888', '盒', 'product_images/dalibao.jpg', '0', '2015-08-31 20:58:10', '1', '大礼包大礼包大礼包大礼包大礼包大礼包大礼包大礼包大礼包大礼包大礼包');
INSERT INTO `product` VALUES ('216', '豪华卡', '豪华型4对（雌雄各4只）雌3.8两 雄4.8两', 'CARD', 'LIPINKA', '1388', '张', 'product_images/haohuaka.jpg', '0', '2015-08-31 21:09:15', '9', '豪华型4对（雌雄各4只）雌3.8两 雄4.8两');
INSERT INTO `product` VALUES ('403', '精品卡', '精品型3对（雌雄各3只）雌3.2两 雄4.2两', 'CARD', 'LIPINKA', '628', '张', 'product_images/jingpinka.jpg', '0', '2015-08-31 21:09:15', '16', '精品型3对（雌雄各3只）雌3.2两 雄4.2两');
INSERT INTO `product` VALUES ('404', '至尊卡', '至尊型5对（雌雄各5只）雌5.0两 雄6.0两', 'CARD', 'LIPINKA', '3588', '张', 'product_images/zhizunka.jpg', '0', '2015-08-31 21:09:14', '0', '至尊型5对（雌雄各5只）雌5.0两 雄6.0两');
INSERT INTO `product` VALUES ('406', '至尊卡', '至尊型5对（雌雄各5只）雌4.6两 雄5.6两', 'CARD', 'LIPINKA', '2688', '张', 'product_images/zhizunka.jpg', '0', '2015-08-31 21:09:14', '1', '至尊型5对（雌雄各5只）雌4.6两 雄5.6两');
INSERT INTO `product` VALUES ('407', '至尊卡', '至尊型5对（雌雄各5只）雌4.2两 雄5.2两', 'CARD', 'LIPINKA', '1988', '张', 'product_images/zhizunka.jpg', '0', '2015-08-31 21:09:14', '2', '至尊型5对（雌雄各5只）雌4.2两 雄5.2两');
INSERT INTO `product` VALUES ('408', '至尊卡', '至尊型5对（雌雄各5只）雌3.8两 雄4.8两', 'CARD', 'LIPINKA', '1588', '张', 'product_images/zhizunka.jpg', '0', '2015-08-31 21:09:14', '3', '至尊型5对（雌雄各5只）雌3.8两 雄4.8两');
INSERT INTO `product` VALUES ('409', '至尊卡', '至尊型5对（雌雄各5只）雌3.6两 雄4.6两', 'CARD', 'LIPINKA', '1188', '张', 'product_images/zhizunka.jpg', '0', '2015-08-31 20:58:10', '4', '至尊型5对（雌雄各5只）雌3.6两 雄4.6两');
INSERT INTO `product` VALUES ('410', '至尊卡', '至尊型5对（雌雄各5只）雌3.4两 雄4.4两', 'CARD', 'LIPINKA', '988', '张', 'product_images/zhizunka.jpg', '0', '2015-08-31 21:09:14', '5', '至尊型5对（雌雄各5只）雌3.4两 雄4.4两');
INSERT INTO `product` VALUES ('411', '至尊卡', '至尊型5对（雌雄各5只）雌3.2两 雄4.2两', 'CARD', 'LIPINKA', '788', '张', 'product_images/zhizunka.jpg', '0', '2015-08-31 21:09:14', '6', '至尊型5对（雌雄各5只）雌3.2两 雄4.2两');
INSERT INTO `product` VALUES ('412', '至尊卡', '至尊型5对（雌雄各5只）雌2.6两 雄3.6两', 'CARD', 'LIPINKA', '588', '张', 'product_images/zhizunka.jpg', '0', '2015-08-31 21:09:14', '7', '至尊型5对（雌雄各5只）雌2.6两 雄3.6两');
INSERT INTO `product` VALUES ('413', '至尊卡', '至尊型5对（雌雄各5只）雌2.2两 雄3.2两', 'CARD', 'LIPINKA', '388', '张', 'product_images/zhizunka.jpg', '0', '2015-08-31 21:09:14', '8', '至尊型5对（雌雄各5只）雌2.2两 雄3.2两');
INSERT INTO `product` VALUES ('414', '豪华卡', '豪华型4对（雌雄各4只）雌3.6两 雄4.6两', 'CARD', 'LIPINKA', '1168', '张', 'product_images/haohuaka.jpg', '0', '2015-08-31 21:09:15', '10', '豪华型4对（雌雄各4只）雌3.6两 雄4.6两');
INSERT INTO `product` VALUES ('415', '豪华卡', '豪华型4对（雌雄各4只）雌3.2两 雄4.2两', 'CARD', 'LIPINKA', '868', '张', 'product_images/haohuaka.jpg', '0', '2015-08-31 21:09:15', '11', '豪华型4对（雌雄各4只）雌3.2两 雄4.2两');
INSERT INTO `product` VALUES ('416', '豪华卡', '豪华型4对（雌雄各4只）雌3.0两 雄4.0两', 'CARD', 'LIPINKA', '668', '张', 'product_images/haohuaka.jpg', '0', '2015-08-31 21:09:15', '12', '豪华型4对（雌雄各4只）雌3.0两 雄4.0两');
INSERT INTO `product` VALUES ('417', '豪华卡', '豪华型4对（雌雄各4只）雌2.6两 雄3.6两', 'CARD', 'LIPINKA', '488', '张', 'product_images/haohuaka.jpg', '0', '2015-08-31 21:09:15', '13', '豪华型4对（雌雄各4只）雌2.6两 雄3.6两');
INSERT INTO `product` VALUES ('418', '豪华卡', '豪华型4对（雌雄各4只）雌2.2两 雄3.2两', 'CARD', 'LIPINKA', '368', '张', 'product_images/haohuaka.jpg', '0', '2015-08-31 21:09:15', '14', '豪华型4对（雌雄各4只）雌2.2两 雄3.2两');
INSERT INTO `product` VALUES ('419', '精品卡', '精品型3对（雌雄各3只）雌3.0两 雄4.0两', 'CARD', 'LIPINKA', '518', '张', 'product_images/jingpinka.jpg', '0', '2015-08-31 21:09:15', '15', '精品型3对（雌雄各3只）雌3.0两 雄4.0两');
INSERT INTO `product` VALUES ('420', '精品卡', '精品型3对（雌雄各3只）雌2.6两 雄3.6两', 'CARD', 'LIPINKA', '398', '张', 'product_images/jingpinka.jpg', '0', '2015-08-31 21:09:15', '17', '精品型3对（雌雄各3只）雌2.6两 雄3.6两');
INSERT INTO `product` VALUES ('421', '精品卡', '精品型3对（雌雄各3只）雌2.2两 雄3.2两', 'CARD', 'LIPINKA', '298', '张', 'product_images/jingpinka.jpg', '0', '2015-08-31 21:09:15', '18', '精品型3对（雌雄各3只）雌2.2两 雄3.2两');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wechat_id` varchar(255) NOT NULL DEFAULT '0',
  `nickname` varchar(255) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `headimgurl` text,
  `consignee` varchar(45) DEFAULT NULL,
  `consignee_contact` varchar(45) DEFAULT NULL,
  `buyer_info` varchar(255) DEFAULT NULL,
  `buyer_address` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('26', 'oHHOIjgoKhz6y_l8yaAhPh25VBp0', '对方正在输入…', 'USER', 'http://wx.qlogo.cn/mmopen/ajNVdqHZLLBomvjSlYuFu6mFuRicrtAErRkc2yZ9FHAfHSFoOfic8CBDiaSO4mHG4w2z7QNibeoz24Pn0lgBex7OTg/0', null, null, null, null);
INSERT INTO `user` VALUES ('27', 'oHHOIjjsul-m8wfqgifTa0aPc18A', '灵达', 'USER', 'http://wx.qlogo.cn/mmopen/KydxAIB52xnM0ozFxzeICRxZdzYgD7tW3e7BMZOj2r3iae6HJQSNLUBactpp4fxCUFeZQVYdqaWbVLeoa1SOpvQ4PDb39YcH0/0', null, null, null, null);
