/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.110
Source Server Version : 50717
Source Host           : 192.168.0.110:3306
Source Database       : fbstockdb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-01-15 15:52:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_wxpay_trade`
-- ----------------------------
DROP TABLE IF EXISTS `tb_wxpay_trade`;
CREATE TABLE `tb_wxpay_trade` (
  `trade_no` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `member_id` varchar(255) NOT NULL,
  `total_fee` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `type` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_wxpay_trade
-- ----------------------------
INSERT INTO `tb_wxpay_trade` VALUES ('Fri Jul 05 16:36:26 CST 2019', '1', '10', '10.0', '0', '2019-07-05 16:36:26', null, '');
INSERT INTO `tb_wxpay_trade` VALUES ('Fri Jul 05 16:48:17 CST 2019', '1', '10', '10.0', '0', '2019-07-05 16:48:17', null, '');
INSERT INTO `tb_wxpay_trade` VALUES ('Fri Jul 05 17:01:07 CST 2019', '1', '10', '10', '0', '2019-07-05 17:01:07', null, '');
INSERT INTO `tb_wxpay_trade` VALUES ('Fri Jul 05 17:03:25 CST 2019', '1', '10', '10', '0', '2019-07-05 17:03:25', null, '');
INSERT INTO `tb_wxpay_trade` VALUES (':07:29 CST 2019', '1', '10', '10', '0', '2019-07-05 17:07:29', null, '');
INSERT INTO `tb_wxpay_trade` VALUES ('20190705171033', '1', '10', '10', '0', '2019-07-05 17:10:33', null, '');
INSERT INTO `tb_wxpay_trade` VALUES ('20190705171128', '1', '10', '10', '0', '2019-07-05 17:11:28', null, '');
INSERT INTO `tb_wxpay_trade` VALUES ('20190705172132', '1', '10', '10.0', '0', '2019-07-05 17:21:32', null, '');
INSERT INTO `tb_wxpay_trade` VALUES ('20190708140442', '1', '10', '10.0', '0', '2019-07-08 14:04:42', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190708150410', '1', '10', '10.0', '0', '2019-07-08 15:04:10', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190708152635', '1', '10', '10.0', '0', '2019-07-08 15:26:35', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190708152800', '15209183609', '1001', '0.01', '0', '2019-07-08 15:28:00', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190708152839', '15209183609', '1001', '0.01', '0', '2019-07-08 15:28:39', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190708152955', '15209183609', '1001', '0.01', '0', '2019-07-08 15:29:55', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190708153538', '15209183609', '1001', '0.01', '0', '2019-07-08 15:35:38', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190708153646', '15209183609', '1001', '0.01', '0', '2019-07-08 15:36:46', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190708154049', '15209183609', '1001', '0.01', '0', '2019-07-08 15:40:49', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190708154303', '15209183609', '1001', '0.01', '0', '2019-07-08 15:43:03', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190712112201', '15209183609', '1001', '0.01', '0', '2019-07-12 11:22:01', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190712113034', '15209183609', '1001', '0.01', '0', '2019-07-12 11:30:34', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190716135238', '15209183609', '1001', '0.01', '0', '2019-07-16 13:52:38', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20190717152058', '15209183609', '1001', '0.01', '0', '2019-07-17 15:20:58', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20200113152413', '8', '1002', '0.1', '0', '2020-01-13 15:24:13', null, '0');
INSERT INTO `tb_wxpay_trade` VALUES ('20200115145039', '8', '1002', '6', '0', '2020-01-15 14:50:39', null, '1');
INSERT INTO `tb_wxpay_trade` VALUES ('20200115145130', '8', '1002', '6', '0', '2020-01-15 14:51:30', null, '0');
