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

 Date: 22/05/2023 02:45:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for event
-- ----------------------------
DROP TABLE IF EXISTS `event`;
CREATE TABLE `event`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动标题',
  `details` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '活动详情描述md',
  `images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '活动图片集(,隔开)',
  `level` tinyint(1) NOT NULL DEFAULT 0 COMMENT '权重 越大权重越高',
  `start_time` datetime NOT NULL COMMENT '活动开始时间',
  `end_time` datetime NOT NULL COMMENT '活动结束时间',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '活动状态，0表示未开始，1表示正在进行，-1表示已结束',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商城活动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of event
-- ----------------------------
INSERT INTO `event` VALUES ('2018072115284300903', '限时秒杀，只为更好的你', '## 活动细则\n\n1. 活动时间7月21日至7月24日\n2. 每日10:00、15:00、20:00准时开抢\n3. 商品数量有限，先到先得\n4. 如遇活动页面崩溃或者无法下单等问题，可联系客服进行处理。', 'default.png', 0, '2023-05-16 21:43:09', '2023-05-18 21:43:09', -1, '2023-05-19 21:43:09', '2023-05-19 21:43:09');
INSERT INTO `event` VALUES ('2018072211041601402', '299元起包邮，家居特卖', '## 活动细则\n\n1. 活动时间7月22日至7月29日\n2. 家居类商品满299元包邮（港澳台及部分偏远地区除外）\n3. 如有任何疑问，请联系客服解决。', 'default.png', 0, '2023-05-18 21:43:09', '2023-05-25 21:43:09', 1, '2023-05-19 21:43:09', '2023-05-19 21:43:09');
INSERT INTO `event` VALUES ('2018072309121200101', '全场8折，疯狂购物季', '## 活动细则\n\n1. 活动时间7月23日至8月23日\n2. 全场商品8折优惠，数量有限，先到先得。\n3. 如遇活动页面崩溃或者无法下单等问题，可联系客服进行处理。', 'default.png', 0, '2023-05-17 21:43:09', '2023-05-23 21:43:09', 1, '2023-05-19 21:43:09', '2023-05-19 21:43:09');
INSERT INTO `event` VALUES ('2018072511282102304', '618大促，苹果产品特价献礼', '## 活动细则\n\n1. 活动时间6月18日至7月2日\n2. 苹果品牌商品7折优惠，仅限618大促期间。\n3. 如有任何疑问，请联系客服解决。', 'default.png', 0, '2023-05-14 21:43:09', '2023-05-21 21:43:09', 1, '2023-05-19 21:43:09', '2023-05-19 21:43:09');
INSERT INTO `event` VALUES ('2018072809375302506', '夏日清凉，买就送空调', '## 活动细则\n\n1. 活动时间7月28日至8月31日\n2. 购买价值2999元及以上家电类商品，即可获赠可调节风速空调一台。\n3. 活动期间，赠品数量有限，先到先得。\n4. 如有任何疑问，请联系客服解决。', 'default.png', 0, '2023-05-18 21:43:09', '2023-05-29 21:43:09', 0, '2023-05-19 21:43:09', '2023-05-19 21:43:09');
INSERT INTO `event` VALUES ('2018073008354504605', '零元购物狂欢节，快来参加吧', '## 活动细则\n\n1. 活动时间7月30日至8月5日\n2. 参与本活动即可享受免费领取商品一次。\n3. 每个账户仅限参加一次，请珍惜机会。\n4. 如遇活动页面崩溃或者无法下单等问题，可联系客服进行处理。', 'default.png', 0, '2023-05-17 21:43:09', '2023-05-21 21:43:09', 0, '2023-05-19 21:43:09', '2023-05-19 21:43:09');

-- ----------------------------
-- Table structure for event_goods
-- ----------------------------
DROP TABLE IF EXISTS `event_goods`;
CREATE TABLE `event_goods`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `event_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动id',
  `goods_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品id',
  `event_price` decimal(10, 2) NOT NULL COMMENT '活动价',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX `event_id_i`(`event_id` ASC) USING BTREE,
  INDEX `goods_id_i`(`goods_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动商品关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of event_goods
-- ----------------------------
INSERT INTO `event_goods` VALUES ('5018072309121200101', '2018072309121200101', '104215909657394688', 299.00, '2023-05-19 21:43:09', '2023-05-19 21:43:09');
INSERT INTO `event_goods` VALUES ('5118072309121200102', '2018072309121200101', '104215909657394689', 99.00, '2023-05-19 21:43:09', '2023-05-19 21:43:09');
INSERT INTO `event_goods` VALUES ('5218072309121200103', '2018072309121200101', '104215909657394690', 2899.00, '2023-05-19 21:43:09', '2023-05-19 21:43:09');

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
  `warranty_time` int NULL DEFAULT 3 COMMENT '保修时间(day)',
  `refund_time` int NULL DEFAULT 7 COMMENT '包换时间(day)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_index`(`category_id` ASC) USING BTREE,
  INDEX `name_index`(`name` ASC) USING BTREE,
  INDEX `description_index`(`description` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('104215909657394688', 'K75机械键盘', '这是一个外设的商品详情', '7998270576900033219', 479.00, 499.00, 0.00, 'image1.jpg,image2.jpg', 'video1.mp4', NULL, NULL, NULL, 1, 0, 100, 210, 3, 7, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods` VALUES ('104215909657394689', '无线鼠标', '这是无线鼠标的商品详情', '7998270576900033219', 129.00, 159.00, 0.00, 'image3.jpg,image4.jpg', 'video2.mp4', NULL, NULL, NULL, 1, 1, 200, 302, 3, 7, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods` VALUES ('104215909657394690', '小新pro', '这是一个电脑的商品详情', '27998270576922333220', 2999.00, 3999.00, 0.00, 'image5.jpg,image6.jpg', 'video3.mp4', NULL, NULL, NULL, 1, 1, 500, 1023, 3, 7, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods` VALUES ('104215909657394691', 'Type数据线', '这是一个数据线的商品详情', '7998270576989233223', 9.99, 19.99, 0.00, 'image7.jpg,image8.jpg', 'video4.mp4', NULL, NULL, NULL, 1, 1, 1000, 2100, 3, 7, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods` VALUES ('104215909657394692', 'Type通用充电器', '这是一个充电器的商品详情', '7998270577011533224', 29.99, 39.99, 0.00, 'image9.jpg,image10.jpg', 'video5.mp4', NULL, NULL, NULL, 1, 1, 800, 1100, 3, 7, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods` VALUES ('104215909657394693', 'HuaWeiP40', '这是一个HuaWei的商品详情', '7998270577078433227', 3999.00, 4999.00, 0.00, 'image11.jpg,image12.jpg', 'video6.mp4', NULL, NULL, NULL, 1, 1, 300, 520, 3, 7, '2023-05-12 21:46:08', '2023-05-12 21:46:08');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods_category
-- ----------------------------
INSERT INTO `goods_category` VALUES ('7998270576877733218', '数码', NULL, NULL, 1, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods_category` VALUES ('7998270576900033219', '键盘', '7998270576877733218', NULL, 1, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods_category` VALUES ('7998270576922333220', '电脑', '7998270576877733218', NULL, 2, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods_category` VALUES ('7998270576966933222', '配件', NULL, NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods_category` VALUES ('7998270576989233223', '数据线', '7998270576966933222', NULL, 1, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods_category` VALUES ('7998270577011533224', '充电器', '7998270576966933222', NULL, 2, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods_category` VALUES ('7998270577033833225', '耳机', '7998270576966933222', NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods_category` VALUES ('7998270577056133226', '手机', NULL, NULL, 2, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods_category` VALUES ('7998270577078433227', 'HuaWei', '7998270577056133226', NULL, 1, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods_category` VALUES ('7998270577100733228', 'OPPO', '7998270577056133226', NULL, 2, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods_category` VALUES ('7998270577123033229', '三星', '7998270577056133226', NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `goods_category` VALUES ('7998270577145333230', 'Vivo', '7998270577123033229', NULL, 1, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');

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
  `is_show` int NOT NULL DEFAULT 0 COMMENT '状态：0-下架；1-正常；2-删除',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `goods_id_index`(`goods_id` ASC) USING BTREE,
  CONSTRAINT `goods_sku_chk_1` CHECK (`price` <= `cost_price`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods_sku
-- ----------------------------
INSERT INTO `goods_sku` VALUES ('8998270577145333231', '104215909657394688', '104键', '墨黑色', '青轴', 100, 479.00, 'K75机械键盘，104键，墨黑色，青轴', 499.00, 'k75_104_black_blue.jpg', 1, '2023-05-12 21:46:09', '2023-05-12 21:46:09');
INSERT INTO `goods_sku` VALUES ('8998270577145333232', '104215909657394688', '104键', '白色', '红轴', 120, 479.00, 'K75机械键盘，104键，白色，红轴', 499.00, 'k75_104_white_blue.jpg', 1, '2023-05-12 21:46:09', '2023-05-12 21:46:09');
INSERT INTO `goods_sku` VALUES ('8998270577145333233', '104215909657394689', '小', '黑色', '无线', 200, 129.00, '无线鼠标，小，黑色', 159.00, 'wireless_mouse_small_black.jpg', 1, '2023-05-12 21:46:09', '2023-05-12 21:46:09');
INSERT INTO `goods_sku` VALUES ('8998270577145333234', '104215909657394689', '大', '黑色', '无线', 180, 129.00, '无线鼠标，大，黑色', 159.00, 'wireless_mouse_large_black.jpg', 1, '2023-05-12 21:46:09', '2023-05-12 21:46:09');
INSERT INTO `goods_sku` VALUES ('8998270577145333235', '104215909657394690', '14寸', '银色', 'i5', 50, 2999.00, '小新pro，14寸，银色，i5处理器', 3999.00, 'xiaoxin_pro_14_silver_i5.jpg', 1, '2023-05-12 21:46:09', '2023-05-12 21:46:09');
INSERT INTO `goods_sku` VALUES ('8998270577145333236', '104215909657394690', '14寸', '银色', 'i7', 30, 3299.00, '小新pro，14寸，银色，i7处理器', 3999.00, 'xiaoxin_pro_14_silver_i7.jpg', 1, '2023-05-12 21:46:09', '2023-05-12 21:46:09');
INSERT INTO `goods_sku` VALUES ('8998270577145333237', '104215909657394691', '1m', '白色', 'Type-C', 1000, 9.99, 'Type数据线，1米，白色，Type-C', 19.99, 'type_cable_1m_white.jpg', 1, '2023-05-12 21:46:09', '2023-05-12 21:46:09');
INSERT INTO `goods_sku` VALUES ('8998270577145333238', '104215909657394691', '1.5m', '白色', 'Type-C', 800, 12.99, 'Type数据线，1.5米，白色，Type-C', 19.99, 'type_cable_1.5m_white.jpg', 1, '2023-05-12 21:46:09', '2023-05-12 21:46:09');
INSERT INTO `goods_sku` VALUES ('8998270577145333239', '104215909657394692', '单口', '白色', 'Type-C', 800, 29.99, 'Type通用充电器，单口，白色，Type-C', 39.99, 'type_charger_single_white.jpg', 1, '2023-05-12 21:46:09', '2023-05-12 21:46:09');
INSERT INTO `goods_sku` VALUES ('8998270577145333240', '104215909657394692', '双口', '白色', 'Type-C', 600, 34.99, 'Type通用充电器，双口，白色，Type-C', 39.99, 'type_charger_double_white.jpg', 1, '2023-05-12 21:46:09', '2023-05-12 21:46:09');
INSERT INTO `goods_sku` VALUES ('8998270577145333241', '104215909657394693', '128GB', '黑色', 'P40', 300, 3999.00, 'HuaWeiP40，128GB，黑色，P40版本', 4999.00, 'huawei_p40_128gb_black.jpg', 1, '2023-05-12 21:46:09', '2023-05-12 21:46:09');
INSERT INTO `goods_sku` VALUES ('8998270577145333242', '104215909657394693', '256GB', '黑色', 'P40', 200, 4199.00, 'HuaWeiP40，256GB，黑色，P40版本', 4999.00, 'huawei_p40_256gb_black.jpg', 1, '2023-05-12 21:46:09', '2023-05-12 21:46:09');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单id',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `address_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货地址',
  `orders_time` datetime NOT NULL COMMENT '下单时间',
  `total_price` decimal(10, 2) NOT NULL COMMENT '订单总价',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单状态，0表示待付款，1表示已付款，2表示已发货，3表示待收货，4表示已收货，5表示已评价，6表示已取消，7表示已超时取消',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id_i`(`user_id` ASC) USING BTREE,
  INDEX `address_id_i`(`address_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for orders_item
-- ----------------------------
DROP TABLE IF EXISTS `orders_item`;
CREATE TABLE `orders_item`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '附订单id',
  `orders_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主订单id',
  `sku_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品规格id',
  `quantity` int NOT NULL COMMENT '数量',
  `activity_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '活动id',
  `shop_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺id',
  `reduce_price` decimal(10, 2) NOT NULL COMMENT '优惠额度',
  `final_price` decimal(10, 2) NOT NULL COMMENT '子订单最终总价',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orders_id_i`(`orders_id` ASC) USING BTREE,
  INDEX `sku_id_i`(`sku_id` ASC) USING BTREE,
  INDEX `shop_id_i`(`shop_id` ASC) USING BTREE,
  INDEX `activity_id_i`(`activity_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders_item
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
) ENGINE = InnoDB AUTO_INCREMENT = 10016 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of recharge_combo
-- ----------------------------
INSERT INTO `recharge_combo` VALUES (10011, '充值50元送200积分', 1, 50.00, 200, '2023-05-06 01:45:31', '2023-05-06 01:45:31');
INSERT INTO `recharge_combo` VALUES (10012, '充值100元送500积分', 1, 100.00, 500, '2023-05-06 01:45:31', '2023-05-06 01:45:31');
INSERT INTO `recharge_combo` VALUES (10013, '充值200元送1000积分', 1, 200.00, 1000, '2023-05-06 01:45:31', '2023-05-06 01:45:31');
INSERT INTO `recharge_combo` VALUES (10014, '充值500元送2000积分', 1, 500.00, 2000, '2023-05-06 01:45:31', '2023-05-06 01:45:31');
INSERT INTO `recharge_combo` VALUES (10015, '充值1000元送10000积分', 1, 1000.00, 10000, '2023-05-06 01:45:31', '2023-05-06 01:45:31');

-- ----------------------------
-- Table structure for shop_cart
-- ----------------------------
DROP TABLE IF EXISTS `shop_cart`;
CREATE TABLE `shop_cart`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `sku_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品规格id',
  `quantity` int NOT NULL DEFAULT 0 COMMENT '加购数量',
  `activity_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '活动id',
  `shop_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sku_id_index`(`sku_id` ASC) USING BTREE,
  INDEX `activity_id_index`(`activity_id` ASC) USING BTREE,
  INDEX `user_id_index`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shop_cart
-- ----------------------------
INSERT INTO `shop_cart` VALUES ('3263652592439853312', '2163652592439853323', '8998270577145333231', 2, NULL, NULL, '2023-05-13 18:16:05', '2023-05-13 18:16:05');
INSERT INTO `shop_cart` VALUES ('3263652592439853323', '2163652592439853323', '8998270577145333236', 12, NULL, NULL, '2023-05-13 18:16:05', '2023-05-13 18:16:05');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('5819236053864939521', NULL, '超级管理员', 'ROLE_admin_super', '系统的全部权限,包括增删查改管理员', 1, '2023-05-06 01:44:29', '2023-05-13 20:18:40');
INSERT INTO `sys_role` VALUES ('5819236053864939522', '5819236053864939521', '用户管理员', 'ROLE_admin_user', '对普通数据增删查改', 1, '2023-05-06 01:44:29', '2023-05-13 20:18:46');
INSERT INTO `sys_role` VALUES ('5819236053864939523', '5819236053864939522', '查看管理员', 'ROLE_admin_viewer', '检查查看数据的管理员', 1, '2023-05-06 01:44:29', '2023-05-13 20:18:49');
INSERT INTO `sys_role` VALUES ('5819236053864939524', '5819236053864939522', '客服', 'ROLE_service', '客服', 2, '2023-05-06 01:44:29', '2023-05-13 20:18:52');
INSERT INTO `sys_role` VALUES ('5819236053864939525', NULL, '普通用户', 'ROLE_user_zero', '普通购买无优惠无额外奖励', 0, '2023-05-06 01:44:29', '2023-05-13 20:18:55');
INSERT INTO `sys_role` VALUES ('5819236053864939526', NULL, '1级会员', 'ROLE_user_first', '有对应代金卷、优惠卷，额度4%', 0, '2023-05-06 01:44:29', '2023-05-13 20:18:57');
INSERT INTO `sys_role` VALUES ('5819236053864939527', NULL, '2级会员', 'ROLE_user_second', '有对应代金卷、优惠卷，额度10%', 0, '2023-05-06 01:44:29', '2023-05-13 20:19:00');
INSERT INTO `sys_role` VALUES ('5819236053864939528', NULL, '3级会员', 'ROLE_user_third', '有对应代金卷、优惠卷，额度15%', 0, '2023-05-06 01:44:29', '2023-05-13 20:19:02');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色—权限表' ROW_FORMAT = DYNAMIC;

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
  `status` int NOT NULL DEFAULT 1 COMMENT '用户状态',
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1652246616668012545', 'Lulu2333', '$2a$10$DjJfKH8I5j7EGBdlA5d.CeX/DPjYMyb978hT7EZgv9pnDz3IAkcbe', NULL, '13415000001', '新用户', '保密', 'default.png', NULL, 1, '2023-04-29 09:41:05', '2023-04-29 09:41:05', NULL, NULL, 1, 0, 1);
INSERT INTO `sys_user` VALUES ('1653240351484801026', 'admin233', '$2a$10$trFdiRCBradkZdD7S.xesupAXTj7xwwD1u3KSrgTaq436EmilDPRa', NULL, '13415048700', '新用户', '保密', 'default.png', NULL, 1, '2023-05-02 11:29:50', '2023-05-20 02:37:42', '2023-05-20 02:37:42', NULL, 1, 0, 1);
INSERT INTO `sys_user` VALUES ('2163652592439853323', 'Kiwi2333', '$2a$10$s68J2cbazN3oL9Ag8tFO5.GtzVF5Ns26fgTqrgLC1hD2oxKuCP30y', '1329634286@qq.com', '13415000000', 'Kiwi2333', '男', 'default.png', NULL, 0, '2022-03-01 10:00:00', '2023-05-20 02:37:20', '2023-05-20 02:37:20', '192.168.1.1', 1, 1, 1);

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色关联表' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_salt
-- ----------------------------
INSERT INTO `sys_user_salt` VALUES ('1652246616668012545', 'LXgp/T13eSGkQg==');
INSERT INTO `sys_user_salt` VALUES ('1653240351484801026', '/eaV/OB2FaO4KQ==');
INSERT INTO `sys_user_salt` VALUES ('2163652592439853323', '4wechxge23ex21');

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `is_default` int NOT NULL DEFAULT 0 COMMENT '是否默认',
  `province` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省份',
  `city` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '城市',
  `county` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区/县',
  `address` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `postal_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮编',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `address_user_id`(`user_id` ASC) USING BTREE,
  INDEX `address_phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户收货地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_address
-- ----------------------------
INSERT INTO `user_address` VALUES ('503399947050422323', '张三', '2163652592439853323', 0, '江苏省', '南京市', '玄武区', '中山路123号', '210000', '13416231111', '2023-05-16 18:40:10', '2023-05-16 22:28:04');
INSERT INTO `user_address` VALUES ('503399947050422324', 'kiwi233', '1652246616668012545', 0, '江苏省', '南京市', '白下区', '长白路456号', '210000', '13912113421', '2023-05-16 18:40:10', '2023-05-16 18:40:30');
INSERT INTO `user_address` VALUES ('503399947050422325', '猕猴桃', '2163652592439853323', 1, '广东省', '汕头市', '潮阳区', '谷饶石门', '515159', '13912113421', '2023-05-16 18:40:10', '2023-05-16 22:28:04');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_wallet
-- ----------------------------
INSERT INTO `user_wallet` VALUES ('1652246616668012545', 0.00, 0.00, 0.00, 0, '2023-04-27 18:22:54', '2023-04-27 18:40:54');
INSERT INTO `user_wallet` VALUES ('2163652592439853323', 0.00, 0.00, 0.00, 0, '2023-04-28 18:22:54', '2023-04-28 18:40:54');

SET FOREIGN_KEY_CHECKS = 1;
