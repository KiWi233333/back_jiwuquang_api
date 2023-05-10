/*
 Navicat Premium Data Transfer

 Source Server         : Kiwi Community Mall
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : localhost:3306
 Source Schema         : kiwi_community_mall

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 10/05/2023 01:06:58
*/

DROP DATABASE IF EXISTS kiwi_community_mall;
CREATE DATABASE kiwi_community_mall;
USE kiwi_community_mall;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
                          `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
                          `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品详情',
                          `category_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类id',
                          `price` decimal(10, 2) NOT NULL COMMENT '销售价',
                          `cost_price` decimal(10, 2) NOT NULL COMMENT '原价',
                          `postage` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '运费',
                          `images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '图片集合(,分割)',
                          `video` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频名称',
                          `province` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发货省',
                          `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发货市',
                          `district` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发货区',
                          `is_show` tinyint(1) NULL DEFAULT 0 COMMENT '是否上架',
                          `is_new` tinyint(1) NULL DEFAULT 1 COMMENT '是否新品',
                          `sales` bigint NOT NULL DEFAULT 0 COMMENT '销量',
                          `views` bigint NOT NULL DEFAULT 0 COMMENT '浏览量',
                          `warrantyTime` int NULL DEFAULT 3 COMMENT '保修时间(day)',
                          `refundTime` int NULL DEFAULT 7 COMMENT '包换时间(day)',
                          `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                          `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`) USING BTREE,
                          INDEX `category_index`(`category_id` ASC) USING BTREE,
                          INDEX `name_index`(`name` ASC) USING BTREE,
                          INDEX `description_index`(`description` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('104215909657394688', 'K75机械键盘', '这是一个外设的商品详情', '7998270576900033219', 479.00, 499.00, 0.00, 'image1.jpg,image2.jpg', 'video1.mp4', NULL, NULL, NULL, 1, 0, 100, 210, 3, 7, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods` VALUES ('104215909657394689', '无线鼠标', '这是无线鼠标的商品详情', '7998270576900033219', 129.00, 159.00, 0.00, 'image3.jpg,image4.jpg', 'video2.mp4', NULL, NULL, NULL, 1, 1, 200, 302, 3, 7, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods` VALUES ('104215909657394690', '小新pro', '这是一个电脑的商品详情', '27998270576922333220', 2999.00, 3999.00, 0.00, 'image5.jpg,image6.jpg', 'video3.mp4', NULL, NULL, NULL, 1, 1, 500, 1023, 3, 7, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods` VALUES ('104215909657394691', 'Type数据线', '这是一个数据线的商品详情', '7998270576989233223', 9.99, 19.99, 0.00, 'image7.jpg,image8.jpg', 'video4.mp4', NULL, NULL, NULL, 1, 1, 1000, 2100, 3, 7, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods` VALUES ('104215909657394692', 'Type通用充电器', '这是一个充电器的商品详情', '7998270577011533224', 29.99, 39.99, 0.00, 'image9.jpg,image10.jpg', 'video5.mp4', NULL, NULL, NULL, 1, 1, 800, 1100, 3, 7, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods` VALUES ('104215909657394693', 'HuaWeiP40', '这是一个HuaWei的商品详情', '7998270577078433227', 3999.00, 4999.00, 0.00, 'image11.jpg,image12.jpg', 'video6.mp4', NULL, NULL, NULL, 1, 1, 300, 520, 3, 7, '2023-05-06 01:45:16', '2023-05-06 01:45:16');

-- ----------------------------
-- Table structure for goods_category
-- ----------------------------
DROP TABLE IF EXISTS `goods_category`;
CREATE TABLE `goods_category`  (
                                   `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
                                   `parent_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父id',
                                   `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
                                   `sort_order` int NULL DEFAULT 0 COMMENT '权重0>',
                                   `is_show` tinyint(1) NULL DEFAULT 1 COMMENT '是否展示',
                                   `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                                   `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `name`(`name` ASC) USING BTREE,
                                   INDEX `goods_category_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_category
-- ----------------------------
INSERT INTO `goods_category` VALUES ('7998270576877733218', '数码', NULL, NULL, 1, 1, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods_category` VALUES ('7998270576900033219', '外设', '7998270576877733218', NULL, 1, 1, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods_category` VALUES ('7998270576922333220', '电脑', '7998270576877733218', NULL, 2, 1, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods_category` VALUES ('7998270576966933222', '配件', NULL, NULL, 3, 1, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods_category` VALUES ('7998270576989233223', '数据线', '7998270576966933222', NULL, 1, 1, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods_category` VALUES ('7998270577011533224', '充电器', '7998270576966933222', NULL, 2, 1, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods_category` VALUES ('7998270577033833225', '耳机', '7998270576966933222', NULL, 3, 1, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods_category` VALUES ('7998270577056133226', '手机', NULL, NULL, 2, 1, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods_category` VALUES ('7998270577078433227', 'HuaWei', '7998270577056133226', NULL, 1, 1, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods_category` VALUES ('7998270577100733228', 'OPPO', '7998270577056133226', NULL, 2, 1, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods_category` VALUES ('7998270577123033229', '三星', '7998270577056133226', NULL, 3, 1, '2023-05-06 01:45:16', '2023-05-06 01:45:16');
INSERT INTO `goods_category` VALUES ('7998270577145333230', 'Vivo', '7998270577123033229', NULL, 1, 1, '2023-05-06 01:45:16', '2023-05-06 01:45:16');

-- ----------------------------
-- Table structure for goods_sku
-- ----------------------------
DROP TABLE IF EXISTS `goods_sku`;
CREATE TABLE `goods_sku`  (
                              `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
                              `goods_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品id',
                              `size` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '大小',
                              `color` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '颜色',
                              `combo` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品版本',
                              `stock` int NOT NULL COMMENT '规格库存',
                              `price` decimal(10, 2) NOT NULL COMMENT '销售价',
                              `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '规格详情',
                              `cost_price` decimal(10, 2) NOT NULL COMMENT '原价',
                              `image` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default_sku.png' COMMENT '规格图片',
                              `is_show` int NOT NULL DEFAULT 0 COMMENT '状态：0-正常；1-下架；2-删除',
                              `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                              `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`) USING BTREE,
                              CONSTRAINT `goods_sku_chk_1` CHECK (`price` > `cost_price`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_sku
-- ----------------------------

-- ----------------------------
-- Table structure for recharge_combo
-- ----------------------------
DROP TABLE IF EXISTS `recharge_combo`;
CREATE TABLE `recharge_combo`  (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '套餐名称',
                                   `discount` float NOT NULL DEFAULT 0 COMMENT '折扣',
                                   `amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '充值额度',
                                   `points` bigint NOT NULL DEFAULT 0 COMMENT '送积分',
                                   `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                                   `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10016 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recharge_combo
-- ----------------------------
INSERT INTO `recharge_combo` VALUES (10011, '充值50元送200积分', 1, 50.00, 200, '2023-05-06 01:45:31', '2023-05-06 01:45:31');
INSERT INTO `recharge_combo` VALUES (10012, '充值100元送500积分', 1, 100.00, 500, '2023-05-06 01:45:31', '2023-05-06 01:45:31');
INSERT INTO `recharge_combo` VALUES (10013, '充值200元送1000积分', 1, 200.00, 1000, '2023-05-06 01:45:31', '2023-05-06 01:45:31');
INSERT INTO `recharge_combo` VALUES (10014, '充值500元送2000积分', 1, 500.00, 2000, '2023-05-06 01:45:31', '2023-05-06 01:45:31');
INSERT INTO `recharge_combo` VALUES (10015, '充值1000元送10000积分', 1, 1000.00, 10000, '2023-05-06 01:45:31', '2023-05-06 01:45:31');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
                                   `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限ID',
                                   `parent_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属父级权限ID',
                                   `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限唯一CODE代码',
                                   `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限名称',
                                   `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限路径',
                                   `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限介绍',
                                   `type` bigint NULL DEFAULT NULL COMMENT '权限类别(0全部 1查2增3改4删)',
                                   `creator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
                                   `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   INDEX `parent_id`(`parent_id` ASC) USING BTREE COMMENT '父级权限ID',
                                   INDEX `code`(`code` ASC) USING BTREE COMMENT '权限CODE代码'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('4032058398348814427', NULL, 'all:view', '管理员查询权限', '/admin', '管理员查询权限', 1, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348814493', NULL, 'all:all', '管理员所有权限', '/admin/**', '管理员所有权限', 0, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348824496', NULL, 'user:all', '用户全部权限', '/user/**', '用户全部权限', 0, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348884225', NULL, 'service:all', 'service:all', '/service/**', '客服全部权限', 0, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348884414', NULL, 'goods:view', '商品查看权限', '/goods/**', '商品查看权限', 1, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348884424', NULL, 'goods:add', '商品添加权限', '/goods/**', '商品添加权限', 2, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348884432', NULL, 'goods:up', '商品修改权限', '/goods/**', '商品修改权限', 3, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348884445', NULL, 'goods:del', '商品删除权限', '/goods/**', '商品删除权限', 4, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348884491', NULL, 'super:all', '增删查改所有权限', '/**', '增删查改所有权限', 0, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348884496', NULL, 'goods:all', '商品全部权限', '/goods/**', '商品全部权限', 0, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348894414', NULL, 'order:view', '订单查看权限', '/order/**', '订单查看权限', 1, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348894424', NULL, 'order:add', '订单添加权限', '/order/**', '订单添加权限', 2, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348894432', NULL, 'order:up', '订单修改权限', '/order/**', '订单修改权限', 3, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348894445', NULL, 'order:del', '订单删除权限', '/order/**', '订单删除权限', 4, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('4032058398348894492', NULL, 'order:all', '订单所有权限', '/order/**', '订单所有权限', 0, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('5032058398348824496', NULL, 'user:view', '用户查看权限', '/user/**', '用户查看权限', 1, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('6032058398348824496', NULL, 'user:add', '用户添加权限', '/user/**', '用户添加权限', 2, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('7032058398348824496', NULL, 'user:up', '用户修改权限', '/user/**', '用户修改权限', 3, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_permission` VALUES ('8032058398348824496', NULL, 'user:del', '用户删除权限', '/user/**', '用户删除权限', 4, NULL, '2023-05-06 01:44:29', '2023-05-06 01:44:29');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `parent_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属父级角色ID',
                             `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
                             `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色唯一CODE代码',
                             `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色介绍',
                             `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '角色类型：0用户 1管理员 2客服',
                             `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `parent_id`(`parent_id` ASC) USING BTREE COMMENT '父级角色ID',
                             INDEX `code`(`code` ASC) USING BTREE COMMENT '角色CODE代码'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('5819236053864939521', NULL, '超级管理员', 'admin_super', '系统的全部权限,包括增删查改管理员', 1, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role` VALUES ('5819236053864939522', '5819236053864939521', '用户管理员', 'admin_user', '对普通数据增删查改', 1, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role` VALUES ('5819236053864939523', '5819236053864939522', '查看管理员', 'admin_viewer', '检查查看数据的管理员', 1, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role` VALUES ('5819236053864939524', '5819236053864939522', '客服', 'service', '客服', 2, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role` VALUES ('5819236053864939525', NULL, '普通用户', 'user_zero', '普通购买无优惠无额外奖励', 0, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role` VALUES ('5819236053864939526', NULL, '1级会员', 'user_first', '有对应代金卷、优惠卷，额度4%', 0, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role` VALUES ('5819236053864939527', NULL, '2级会员', 'user_second', '有对应代金卷、优惠卷，额度10%', 0, '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role` VALUES ('5819236053864939528', NULL, '3级会员', 'user_third', '有对应代金卷、优惠卷，额度15%', 0, '2023-05-06 01:44:29', '2023-05-06 01:44:29');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
                                        `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限ID',
                                        `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
                                        `permission_id` bigint NULL DEFAULT NULL COMMENT '权限ID',
                                        `creator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
                                        `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        INDEX `roleid_id`(`role_id` ASC) USING BTREE COMMENT '角色ID',
                                        INDEX `permissionid_id`(`permission_id` ASC) USING BTREE COMMENT '权限ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色—权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('6762239947855022592', 5819236053864939521, 4032058398348884491, '1653240351484801026', '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role_permission` VALUES ('6819236053864939523', 5819236053864939525, 4032058398348824496, '1653240351484801026', '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role_permission` VALUES ('6819236053864939524', 5819236053864939526, 4032058398348824496, '1653240351484801026', '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role_permission` VALUES ('6819236053864939525', 5819236053864939527, 4032058398348824496, '1653240351484801026', '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role_permission` VALUES ('6819236053864939526', 5819236053864939528, 4032058398348824496, '1653240351484801026', '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role_permission` VALUES ('6819236053864939527', 5819236053864939522, 4032058398348884493, '1653240351484801026', '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role_permission` VALUES ('6819236053864939528', 5819236053864939523, 4032058398348884427, '1653240351484801026', '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role_permission` VALUES ('6819236053864939529', 5819236053864939524, 4032058398348884225, '1653240351484801026', '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_role_permission` VALUES ('6819236053864939623', 5819236053864939525, 5032058398348824496, '1653240351484801026', '2023-05-06 01:44:29', '2023-05-06 01:44:29');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                             `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
                             `password` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
                             `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
                             `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
                             `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
                             `gender` enum('男','女','保密') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '保密' COMMENT '性别',
                             `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'default.png' COMMENT '头像',
                             `birthday` datetime NULL DEFAULT NULL COMMENT '生日',
                             `user_type` int NOT NULL DEFAULT 0 COMMENT '用户类型(0前台、1后台)',
                             `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                             `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
                             `last_login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录ip',
                             `status` enum('正常','禁用') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '正常' COMMENT '用户状态',
                             `is_email_verified` int NOT NULL DEFAULT 0 COMMENT '是否邮箱验证',
                             `is_phone_verified` int NOT NULL DEFAULT 0 COMMENT '是否手机号验证',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `username`(`username` ASC) USING BTREE,
                             UNIQUE INDEX `email`(`email` ASC) USING BTREE,
                             UNIQUE INDEX `phone`(`phone` ASC) USING BTREE,
                             INDEX `user_password_index`(`password` ASC) USING BTREE,
                             INDEX `user_username_index`(`username` ASC) USING BTREE,
                             INDEX `user_email_index`(`email` ASC) USING BTREE,
                             INDEX `user_phone_index`(`phone` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1652246616668012545', 'Lulu2333', '$2a$10$DjJfKH8I5j7EGBdlA5d.CeX/DPjYMyb978hT7EZgv9pnDz3IAkcbe', NULL, '13415000001', '新用户', '保密', 'default.png', NULL, 1, '2023-04-29 09:41:05', '2023-04-29 09:41:05', NULL, NULL, '正常', 0, 1);
INSERT INTO `sys_user` VALUES ('1653240351484801026', 'admin233', '$2a$10$trFdiRCBradkZdD7S.xesupAXTj7xwwD1u3KSrgTaq436EmilDPRa', NULL, '13415048700', '新用户', '保密', 'default.png', NULL, 1, '2023-05-02 11:29:50', '2023-05-06 16:24:05', '2023-05-06 16:24:05', NULL, '正常', 0, 1);
INSERT INTO `sys_user` VALUES ('2163652592439853323', 'Kiwi2333', '$2a$10$DWdzdbF1uuiN2y4K4Jsi..0DQ8CD19DWxEJHt6qqcutVfYNc84Vl.', '1329634286@qq.com', '13415000000', 'Zxcv风格', '男', '28e00a14ff0544dbbb16144bbbf6d62a.jpg', '2005-03-24 20:33:11', 0, '2022-03-01 10:00:00', '2023-05-09 04:01:25', '2023-05-09 04:01:25', '192.168.1.1', '正常', 1, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
                                  `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  `user_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
                                  `role_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色ID',
                                  `creator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
                                  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  INDEX `userid_id`(`user_id` ASC) USING BTREE COMMENT '用户ID',
                                  INDEX `role_id`(`role_id` ASC) USING BTREE COMMENT '角色ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('4032058398348884480', '1653240351484801026', '5819236053864939521', '超级管理员', '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_user_role` VALUES ('4032058398348884481', '1652246616668012545', '5819236053864939524', '用户管理员', '2023-05-06 01:44:29', '2023-05-06 01:44:29');
INSERT INTO `sys_user_role` VALUES ('4032058398348884482', '2163652592439853323', '5819236053864939525', '用户管理员', '2023-05-06 01:44:29', '2023-05-06 01:44:29');

-- ----------------------------
-- Table structure for sys_user_salt
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_salt`;
CREATE TABLE `sys_user_salt`  (
                                  `user_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  `salt` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码盐值',
                                  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_salt
-- ----------------------------
INSERT INTO `sys_user_salt` VALUES ('1652246616668012545', 'LXgp/T13eSGkQg==');
INSERT INTO `sys_user_salt` VALUES ('1653240351484801026', '/eaV/OB2FaO4KQ==');
INSERT INTO `sys_user_salt` VALUES ('2163652592439853323', '4wechxge23ex21');

-- ----------------------------
-- Table structure for user_wallet
-- ----------------------------
DROP TABLE IF EXISTS `user_wallet`;
CREATE TABLE `user_wallet`  (
                                `user_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
                                `balance` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '余额',
                                `recharge` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '充值总额',
                                `spend` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '消费总额',
                                `points` bigint NOT NULL DEFAULT 0 COMMENT '总积分',
                                `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
                                `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (`user_id`) USING BTREE,
                                INDEX `user_wallet_index`(`balance` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_wallet
-- ----------------------------
INSERT INTO `user_wallet` VALUES ('1652246616668012545', 0.00, 0.00, 0.00, 0, '2023-04-27 18:22:54', '2023-04-27 18:40:54');
INSERT INTO `user_wallet` VALUES ('2163652592439853323', 0.00, 0.00, 0.00, 0, '2023-04-28 18:22:54', '2023-04-28 18:40:54');

SET FOREIGN_KEY_CHECKS = 1;


SHOW WARNINGS;
