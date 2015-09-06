/*
Navicat MySQL Data Transfer

Source Server         : Songda Mycai
Source Server Version : 50543
Source Host           : 120.26.37.219:3306
Source Database       : crab

Target Server Type    : MYSQL
Target Server Version : 50543
File Encoding         : 65001

Date: 2015-09-06 20:19:44
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
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bill_order
-- ----------------------------
INSERT INTO `bill_order` VALUES ('82', 'songda user', 'o5Irvt5957jQ4xmdHmDp59epk0UU', '{\"items\":[{\"$$hashKey\":\"object:47\",\"amount\":2,\"description\":\"至尊型5对（雌雄各5只）雌5.0两 雄6.0两\",\"picurl\":\"product_images/zhizunka.jpg\",\"productId\":404,\"productName\":\"至尊卡\",\"productPrice\":3588,\"productUnit\":\"张\"}],\"totalAmount\":2,\"totalPrice\":7176}', '2015-09-01 19:36:44', '2015/09/01 19:36', '新中源大楼', '长阳路1930号', '灵达', '13402188638', '未配送', '862040575', null, null);
INSERT INTO `bill_order` VALUES ('83', '灵达', 'oHHOIjjsul-m8wfqgifTa0aPc18A', '{\"items\":[{\"$$hashKey\":\"object:47\",\"amount\":1,\"description\":\"至尊型5对（雌雄各5只）雌5.0两 雄6.0两\",\"picurl\":\"product_images/zhizunka.jpg\",\"productId\":404,\"productName\":\"至尊卡\",\"productPrice\":3588,\"productUnit\":\"张\"}],\"totalAmount\":1,\"totalPrice\":3588}', '2015-09-01 19:55:48', '2015/09/01 19:55', '新中源大楼', '长阳路1930号', '灵达', '13402188638', '未配送', '814249118', null, null);
INSERT INTO `bill_order` VALUES ('85', '灵达', 'oHHOIjjsul-m8wfqgifTa0aPc18A', '{\"items\":[{\"$$hashKey\":\"object:36\",\"amount\":\"1\",\"description\":\"至尊型5对（雌雄各5只）雌5.0两 雄6.0两\",\"picurl\":\"product_images/zhizunka.jpg\",\"productId\":404,\"productName\":\"至尊卡\",\"productPrice\":3588,\"productUnit\":\"张\"}],\"totalAmount\":1,\"totalPrice\":3588}', '2015-09-05 20:11:13', '2015/09/05 20:11', '新中源大楼', '长阳路1930号', '灵达', '13402188638', '未配送', '094756031', null, null);
INSERT INTO `bill_order` VALUES ('86', 'lingda', 'oHHOIjjsul-m8wfqgifTa0aPc18A', '{\"items\":[{\"$$hashKey\":\"object:30\",\"amount\":1,\"description\":\"精品型3对（雌雄各3只）雌3.0两 雄4.0两\",\"picurl\":\"product_images/jingpinka.jpg\",\"productId\":419,\"productName\":\"精品卡\",\"productPrice\":518,\"productUnit\":\"张\"}],\"totalAmount\":1,\"totalPrice\":518}', '2015-09-05 22:01:18', '2015/09/05 22:01', '新中源大楼', '长阳路1930号', '灵达', '13402188638', '未配送', '333205910', null, null);

-- ----------------------------
-- Table structure for card_code
-- ----------------------------
DROP TABLE IF EXISTS `card_code`;
CREATE TABLE `card_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of card_code
-- ----------------------------
INSERT INTO `card_code` VALUES ('9', '792782056445076', '1234', '0', '2015-09-01 20:25:33', '3920-09-01 20:25:33', 'oHHOIjl7ggEbK38j3Y_-y2-myuXA', '413', null, null, null, null);
INSERT INTO `card_code` VALUES ('11', '604304483097529', '1234', '0', '2015-09-05 20:11:13', '3920-09-05 20:11:13', 'oHHOIjjsul-m8wfqgifTa0aPc18A', '404', null, null, null, null);
INSERT INTO `card_code` VALUES ('12', '123', '123', '1', '2015-09-05 21:36:34', '3920-09-05 21:36:34', 'oHHOIjjsul-m8wfqgifTa0aPc18A', '419', '123', '123', '2015-09-05 22:04:00', '123');
INSERT INTO `card_code` VALUES ('13', '1234', '1234', '1', '2015-09-05 22:09:09', '3920-09-05 22:09:09', 'oHHOIjjsul-m8wfqgifTa0aPc18A', '419', '1234', '1234', '2015-09-05 22:23:00', '1234');

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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of coupon
-- ----------------------------
INSERT INTO `coupon` VALUES ('24', 'discount', 'oHHOIjgoKhz6y_l8yaAhPh25VBp0', '2015-09-01 21:20:00', '2019-09-01 21:20:00', null, null, '0.80', '0');
INSERT INTO `coupon` VALUES ('25', 'discount', 'oHHOIjjsul-m8wfqgifTa0aPc18A', '2015-09-01 21:20:00', '2019-09-01 21:20:00', null, null, '0.80', '0');
INSERT INTO `coupon` VALUES ('26', 'discount', 'oHHOIjnjWH7jr24LigfZ1DxolH08', '2015-09-01 21:20:00', '2019-09-01 21:20:00', null, null, '0.80', '0');
INSERT INTO `coupon` VALUES ('28', 'discount', 'oHHOIjhomaY0OanRPYSu3IHr6AN8', '2015-09-01 21:20:00', '2019-09-01 21:20:00', null, null, '0.80', '0');
INSERT INTO `coupon` VALUES ('29', 'discount', 'oHHOIjl7ggEbK38j3Y_-y2-myuXA', '2015-09-01 21:20:00', '2019-09-01 21:20:00', null, null, '0.80', '0');
INSERT INTO `coupon` VALUES ('30', 'voucher', 'oHHOIjgoKhz6y_l8yaAhPh25VBp0', '2015-09-01 21:20:00', '2018-09-01 21:20:00', '10000.00', '200.00', null, '0');
INSERT INTO `coupon` VALUES ('31', 'voucher', 'oHHOIjjsul-m8wfqgifTa0aPc18A', '2015-09-01 21:20:00', '2018-09-01 21:20:00', '10000.00', '200.00', null, '0');
INSERT INTO `coupon` VALUES ('32', 'voucher', 'oHHOIjnjWH7jr24LigfZ1DxolH08', '2015-09-01 21:20:00', '2018-09-01 21:20:00', '10000.00', '200.00', null, '0');
INSERT INTO `coupon` VALUES ('34', 'voucher', 'oHHOIjhomaY0OanRPYSu3IHr6AN8', '2015-09-01 21:20:00', '2018-09-01 21:20:00', '10000.00', '200.00', null, '0');
INSERT INTO `coupon` VALUES ('35', 'voucher', 'oHHOIjl7ggEbK38j3Y_-y2-myuXA', '2015-09-01 21:20:00', '2018-09-01 21:20:00', '10000.00', '200.00', null, '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('20', 'oHHOIjjsul-m8wfqgifTa0aPc18A', '欢迎您关注我们', '2015-08-31 21:36:36', '0');
INSERT INTO `message` VALUES ('21', 'oHHOIjl7ggEbK38j3Y_-y2-myuXA', '欢迎您关注我们', '2015-08-31 22:12:07', '0');
INSERT INTO `message` VALUES ('22', 'oHHOIjhomaY0OanRPYSu3IHr6AN8', '欢迎您关注我们', '2015-09-01 19:39:28', '0');
INSERT INTO `message` VALUES ('23', 'oHHOIjhZGGtOce1vEDkS7vho4Eek', '欢迎您关注我们', '2015-09-02 10:40:13', '0');
INSERT INTO `message` VALUES ('24', 'oHHOIjlsK9uPz8FPSZ6w91V6W6V8', '欢迎您关注我们', '2015-09-02 11:53:56', '0');
INSERT INTO `message` VALUES ('25', 'oHHOIjnjWH7jr24LigfZ1DxolH08', '欢迎您关注我们', '2015-09-05 19:50:16', '0');
INSERT INTO `message` VALUES ('26', 'oHHOIjsbBvCgShAK3uZEMJlDrPMc', '欢迎您关注我们', '2015-09-06 07:58:29', '0');

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
INSERT INTO `product` VALUES ('216', '豪华卡', '豪华型4对（雌雄各4只）雌3.8两 雄4.8两', 'CARD', 'HAOHUAKA', '1388', '张', 'product_images/haohuaka.jpg', '0', '2015-09-05 19:11:57', '9', '豪华型4对（雌雄各4只）雌3.8两 雄4.8两');
INSERT INTO `product` VALUES ('403', '精品卡', '精品型3对（雌雄各3只）雌3.2两 雄4.2两', 'CARD', 'JINGPINKA', '628', '张', 'product_images/jingpinka.jpg', '0', '2015-09-05 19:12:15', '16', '精品型3对（雌雄各3只）雌3.2两 雄4.2两');
INSERT INTO `product` VALUES ('404', '至尊卡', '至尊型5对（雌雄各5只）雌5.0两 雄6.0两', 'CARD', 'ZHIZUNKA', '3588', '张', 'product_images/zhizunka.jpg', '0', '2015-09-05 19:11:43', '0', '至尊型5对（雌雄各5只）雌5.0两 雄6.0两');
INSERT INTO `product` VALUES ('406', '至尊卡', '至尊型5对（雌雄各5只）雌4.6两 雄5.6两', 'CARD', 'ZHIZUNKA', '2688', '张', 'product_images/zhizunka.jpg', '0', '2015-09-05 19:11:43', '1', '至尊型5对（雌雄各5只）雌4.6两 雄5.6两');
INSERT INTO `product` VALUES ('407', '至尊卡', '至尊型5对（雌雄各5只）雌4.2两 雄5.2两', 'CARD', 'ZHIZUNKA', '1988', '张', 'product_images/zhizunka.jpg', '0', '2015-09-05 19:11:43', '2', '至尊型5对（雌雄各5只）雌4.2两 雄5.2两');
INSERT INTO `product` VALUES ('408', '至尊卡', '至尊型5对（雌雄各5只）雌3.8两 雄4.8两', 'CARD', 'ZHIZUNKA', '1588', '张', 'product_images/zhizunka.jpg', '0', '2015-09-05 19:11:43', '3', '至尊型5对（雌雄各5只）雌3.8两 雄4.8两');
INSERT INTO `product` VALUES ('409', '至尊卡', '至尊型5对（雌雄各5只）雌3.6两 雄4.6两', 'CARD', 'ZHIZUNKA', '1188', '张', 'product_images/zhizunka.jpg', '0', '2015-09-05 19:11:43', '4', '至尊型5对（雌雄各5只）雌3.6两 雄4.6两');
INSERT INTO `product` VALUES ('410', '至尊卡', '至尊型5对（雌雄各5只）雌3.4两 雄4.4两', 'CARD', 'ZHIZUNKA', '988', '张', 'product_images/zhizunka.jpg', '0', '2015-09-05 19:11:43', '5', '至尊型5对（雌雄各5只）雌3.4两 雄4.4两');
INSERT INTO `product` VALUES ('411', '至尊卡', '至尊型5对（雌雄各5只）雌3.2两 雄4.2两', 'CARD', 'ZHIZUNKA', '788', '张', 'product_images/zhizunka.jpg', '0', '2015-09-05 19:11:43', '6', '至尊型5对（雌雄各5只）雌3.2两 雄4.2两');
INSERT INTO `product` VALUES ('412', '至尊卡', '至尊型5对（雌雄各5只）雌2.6两 雄3.6两', 'CARD', 'ZHIZUNKA', '588', '张', 'product_images/zhizunka.jpg', '0', '2015-09-05 19:11:43', '7', '至尊型5对（雌雄各5只）雌2.6两 雄3.6两');
INSERT INTO `product` VALUES ('413', '至尊卡', '至尊型5对（雌雄各5只）雌2.2两 雄3.2两', 'CARD', 'ZHIZUNKA', '388', '张', 'product_images/zhizunka.jpg', '0', '2015-09-05 19:11:43', '8', '至尊型5对（雌雄各5只）雌2.2两 雄3.2两');
INSERT INTO `product` VALUES ('414', '豪华卡', '豪华型4对（雌雄各4只）雌3.6两 雄4.6两', 'CARD', 'HAOHUAKA', '1168', '张', 'product_images/haohuaka.jpg', '0', '2015-09-05 19:11:57', '10', '豪华型4对（雌雄各4只）雌3.6两 雄4.6两');
INSERT INTO `product` VALUES ('415', '豪华卡', '豪华型4对（雌雄各4只）雌3.2两 雄4.2两', 'CARD', 'HAOHUAKA', '868', '张', 'product_images/haohuaka.jpg', '0', '2015-09-05 19:11:57', '11', '豪华型4对（雌雄各4只）雌3.2两 雄4.2两');
INSERT INTO `product` VALUES ('416', '豪华卡', '豪华型4对（雌雄各4只）雌3.0两 雄4.0两', 'CARD', 'HAOHUAKA', '668', '张', 'product_images/haohuaka.jpg', '0', '2015-09-05 19:11:57', '12', '豪华型4对（雌雄各4只）雌3.0两 雄4.0两');
INSERT INTO `product` VALUES ('417', '豪华卡', '豪华型4对（雌雄各4只）雌2.6两 雄3.6两', 'CARD', 'HAOHUAKA', '488', '张', 'product_images/haohuaka.jpg', '0', '2015-09-05 19:11:57', '13', '豪华型4对（雌雄各4只）雌2.6两 雄3.6两');
INSERT INTO `product` VALUES ('418', '豪华卡', '豪华型4对（雌雄各4只）雌2.2两 雄3.2两', 'CARD', 'HAOHUAKA', '368', '张', 'product_images/haohuaka.jpg', '0', '2015-09-05 19:11:57', '14', '豪华型4对（雌雄各4只）雌2.2两 雄3.2两');
INSERT INTO `product` VALUES ('419', '精品卡', '精品型3对（雌雄各3只）雌3.0两 雄4.0两', 'CARD', 'JINGPINKA', '518', '张', 'product_images/jingpinka.jpg', '0', '2015-09-05 19:12:15', '15', '精品型3对（雌雄各3只）雌3.0两 雄4.0两');
INSERT INTO `product` VALUES ('420', '精品卡', '精品型3对（雌雄各3只）雌2.6两 雄3.6两', 'CARD', 'JINGPINKA', '398', '张', 'product_images/jingpinka.jpg', '0', '2015-09-05 19:12:15', '17', '精品型3对（雌雄各3只）雌2.6两 雄3.6两');
INSERT INTO `product` VALUES ('421', '精品卡', '精品型3对（雌雄各3只）雌2.2两 雄3.2两', 'CARD', 'JINGPINKA', '298', '张', 'product_images/jingpinka.jpg', '0', '2015-09-05 19:12:15', '18', '精品型3对（雌雄各3只）雌2.2两 雄3.2两');

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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('26', 'oHHOIjgoKhz6y_l8yaAhPh25VBp0', '对方正在输入…', 'USER', 'http://wx.qlogo.cn/mmopen/ajNVdqHZLLBomvjSlYuFu6mFuRicrtAErRkc2yZ9FHAfHSFoOfic8CBDiaSO4mHG4w2z7QNibeoz24Pn0lgBex7OTg/0', null, null, null, null);
INSERT INTO `user` VALUES ('27', 'oHHOIjjsul-m8wfqgifTa0aPc18A', '灵达', 'USER', 'http://wx.qlogo.cn/mmopen/KydxAIB52xnM0ozFxzeICRxZdzYgD7tW3e7BMZOj2r3iae6HJQSNLUBactpp4fxCUFeZQVYdqaWbVLeoa1SOpvQ4PDb39YcH0/0', '灵达', '13402188638', '新中源大楼', '长阳路1930号');
INSERT INTO `user` VALUES ('28', 'oHHOIjnjWH7jr24LigfZ1DxolH08', '蟹宝旺', 'USER', 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM735BSMF5X6kNDP2Ms6o4IGCpRngnczfdTqDUibmfOpviafOSOUExIUuwQH2o4uXvdFt0Kp4gI9DhnA4zfJshJhElQl3NxkNh7Ts/0', null, null, null, null);
INSERT INTO `user` VALUES ('30', 'oHHOIjhomaY0OanRPYSu3IHr6AN8', '开心每一天', 'USER', 'http://wx.qlogo.cn/mmopen/uzQkLfLR1czl7m9xFch7sAiaDGBAIYtwiaa81fWx06oho4yzOTicxWwoBKwxelDVpGJIScEyFP1sGAiblG1AGd2bibibbT45qd8cQY/0', null, null, null, null);
INSERT INTO `user` VALUES ('31', 'oHHOIjl7ggEbK38j3Y_-y2-myuXA', 'songda user', null, null, '是', '傻子', '傻子', '达');
INSERT INTO `user` VALUES ('32', 'oHHOIjsbBvCgShAK3uZEMJlDrPMc', '上海滩', 'USER', 'http://wx.qlogo.cn/mmopen/uzQkLfLR1czl7m9xFch7sDB0urk5hiaRUd2jkHzb8OfHaQAV42HwemWicSlLBzGJmxoB1Eic2nWfmZQJGwvAF2rp98haTzARd5Z/0', null, null, null, null);
INSERT INTO `user` VALUES ('33', 'oHHOIjvV08rrg4UxjB0FUynHbRIo', ' 滚雪球', 'USER', 'http://wx.qlogo.cn/mmopen/uzQkLfLR1czl7m9xFch7sHBKNQWRibVcLicu4tywstdzTsR4ibtIfL8llkUjdvKNavicibOzN2nJG3xOP2ia8pvQaK2hAUcyx6955A/0', null, null, null, null);
INSERT INTO `user` VALUES ('34', 'oHHOIjiG2ByL-Bn28a72irw6hsUY', '阳澄美蟹', 'USER', 'http://wx.qlogo.cn/mmopen/q0PMfkCibT5De3BfYS6KnQs2NFbRK84MibfCwl6piarnABZs74coxw5gHDMoRLP8PhyOicCXE4oasCITBMBqribeE4Q/0', null, null, null, null);
