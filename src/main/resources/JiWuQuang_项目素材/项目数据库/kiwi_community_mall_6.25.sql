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

 Date: 26/06/2023 01:37:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comm_action
-- ----------------------------
DROP TABLE IF EXISTS `comm_action`;
CREATE TABLE `comm_action`  (
  `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一标识',
  `user_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户 ID',
  `post_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子 ID',
  `type` tinyint(1) NOT NULL COMMENT '行为类型，0 表示点赞，1 表示收藏',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_index`(`user_id` ASC, `post_id` ASC, `type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '点赞/收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comm_action
-- ----------------------------

-- ----------------------------
-- Table structure for comm_category
-- ----------------------------
DROP TABLE IF EXISTS `comm_category`;
CREATE TABLE `comm_category`  (
  `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `parent_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父id',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `sort_order` int NULL DEFAULT 0 COMMENT '权重0,1,2,3增加权重',
  `is_show` tinyint(1) NULL DEFAULT 1 COMMENT '是否展示',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE,
  INDEX `sort_order_i`(`sort_order` DESC) USING BTREE,
  INDEX `comm_category_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '社区圈分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comm_category
-- ----------------------------
INSERT INTO `comm_category` VALUES ('5198270576966933224', '游戏', NULL, NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_category` VALUES ('5298270576966933213', '数码', NULL, NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_category` VALUES ('5398270576966933214', '生活', NULL, NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_category` VALUES ('5998270576877733218', '机械键盘', NULL, NULL, 1, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_category` VALUES ('5998270576900033219', '键帽', '5998270576877733218', NULL, 1, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_category` VALUES ('5998270576922333220', '航插线', '5998270576877733218', NULL, 2, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_category` VALUES ('5998270576966933222', '编程', NULL, NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_category` VALUES ('5998270576966933223', '摄影', NULL, NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');

-- ----------------------------
-- Table structure for comm_post
-- ----------------------------
DROP TABLE IF EXISTS `comm_post`;
CREATE TABLE `comm_post`  (
  `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子ID',
  `user_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发布用户ID',
  `category_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子分类ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子内容',
  `images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片集合',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '帖子标签',
  `is_essence` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否加精，0-不加精，1-加精',
  `comment_top_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论置顶的评论ID',
  `views` int NULL DEFAULT 0 COMMENT '浏览次数',
  `comments` int NULL DEFAULT 0 COMMENT '评论次数',
  `likes` int NULL DEFAULT 0 COMMENT '点赞次数',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态：0-草稿，1-已发布，2-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `comm_post_user_id`(`user_id` ASC) USING BTREE,
  INDEX `comm_category_id`(`category_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '社区帖子表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comm_post
-- ----------------------------
INSERT INTO `comm_post` VALUES ('2163652592439853321', '2163652592439853323', '2163652592439853321', '如何学习编程', '编程是一项很有趣的技能，但是新手可能会遇到很多困难，大家有什么好的学习方法和经验分享一下吗？', '', '编程, 学习', 1, NULL, 100, 20, 50, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_post` VALUES ('2163652592439853322', '1666278297176412162', '2163652592439853323', '分享一下摄影技巧', '我是一名摄影爱好者，分享一下我的拍照技巧和经验，希望能帮助到大家。', 'default.png', '摄影, 技巧', 1, NULL, 200, 30, 80, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_post` VALUES ('2163652592439853323', '1666275306549604354', '2163652592439853328', '家里的新家具很漂亮', '最近家里买了一些新家具，很漂亮，分享一下给大家看看。', 'default.png', '生活, 家具', 0, NULL, 50, 10, 20, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_post` VALUES ('2163652592439853324', '1666275306549604354', '2163652592439853326', '最近玩的一款游戏很好玩', '最近玩的一款游戏很好玩，推荐给大家。', '', '游戏, 推荐', 0, NULL, 80, 15, 30, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_post` VALUES ('2163652592439853325', '2163652592439853323', '2163652592439853324', '新买的机械键盘很好用', '最近新买了一款机械键盘，手感很好，打字很舒服。', 'default.png', '机械键盘, 体验', 1, NULL, 120, 25, 50, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_post` VALUES ('2163652592439853326', '1666278297176412162', '2163652592439853322', '推荐一些好看的键帽', '最近收集了一些好看的键帽，分享给大家。', 'default.png', '键帽, 收藏', 0, NULL, 60, 12, 25, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_post` VALUES ('2163652592439853327', '1666275306549604354', '2163652592439853327', '推荐一些好用的数码产品', '最近用了一些好用的数码产品，分享给大家。', '', '数码, 推荐', 0, NULL, 90, 18, 40, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO `comm_post` VALUES ('2163652592439853328', '2163652592439853323', '2163652592439853324', '推荐一些好玩的游戏', '最近玩了一些好玩的游戏，推荐给大家。', '', '游戏, 推荐', 0, NULL, 70, 14, 35, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');

-- ----------------------------
-- Table structure for comm_post_action
-- ----------------------------
DROP TABLE IF EXISTS `comm_post_action`;
CREATE TABLE `comm_post_action`  (
  `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一标识',
  `user_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户 ID',
  `post_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子 ID',
  `type` tinyint(1) NOT NULL COMMENT '行为类型，0 表示点赞，1 表示收藏',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_index`(`user_id` ASC, `post_id` ASC, `type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '点赞/收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comm_post_action
-- ----------------------------
INSERT INTO `comm_post_action` VALUES ('616365259243853325', '2163652592439853323', '2163652592439853324', 1, '2023-01-06 17:00:00');
INSERT INTO `comm_post_action` VALUES ('616365259243953324', '2163652592439853323', '2163652592439853322', 1, '2023-01-04 15:00:00');
INSERT INTO `comm_post_action` VALUES ('616365259243983323', '2163652592439853323', '2163652592439853321', 1, '2023-01-03 14:00:00');
INSERT INTO `comm_post_action` VALUES ('616365259243985321', '2163652592439853323', '2163652592439853321', 0, '2023-01-01 12:00:00');
INSERT INTO `comm_post_action` VALUES ('663652592439853222', '2163652592439853323', '2163652592439853323', 0, '2023-01-02 13:00:00');

-- ----------------------------
-- Table structure for comm_post_comment
-- ----------------------------
DROP TABLE IF EXISTS `comm_post_comment`;
CREATE TABLE `comm_post_comment`  (
  `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论ID',
  `post_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属帖子ID',
  `user_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论用户ID',
  `parent_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父级评论ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评论携带图片',
  `views` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '浏览量',
  `likes` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `post_id`(`post_id` ASC) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `parent_id_i`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '社区评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comm_post_comment
-- ----------------------------
INSERT INTO `comm_post_comment` VALUES ('2163652592439853233', '2163652592439853321', '2163652592439853323', '3163652592439853231', '同意楼上的观点，写得非常好', NULL, 5, 2, '2022-01-03 14:00:00', '2022-01-03 14:00:00');
INSERT INTO `comm_post_comment` VALUES ('2163652592439853302', '2163652592439853322', '2163652592439853323', NULL, '我觉得这篇文章有些问题，需要改进', NULL, 8, 3, '2022-01-02 12:00:00', '2022-01-02 12:00:00');
INSERT INTO `comm_post_comment` VALUES ('3163652592439852307', '2163652592439853321', '2163652592439853323', NULL, '我觉得这篇文章非常好，已经转发给我的朋友们了', NULL, 15, 7, '2022-01-07 22:00:00', '2022-01-07 22:00:00');
INSERT INTO `comm_post_comment` VALUES ('3163652592439853231', '2163652592439853321', '2163652592439853323', NULL, '这篇文章写得真好！', NULL, 10, 5, '2022-01-01 10:00:00', '2022-01-01 10:00:00');
INSERT INTO `comm_post_comment` VALUES ('3163652592439853308', '2163652592439853322', '2163652592439853323', '3163652592439852307', '我也非常喜欢这篇文章，已经转发到我的朋友圈了', NULL, 3, 1, '2022-01-08 00:00:00', '2022-01-08 00:00:00');
INSERT INTO `comm_post_comment` VALUES ('3163652592439853324', '2163652592439853322', '2163652592439853323', NULL, '这篇文章让我受益匪浅，感谢作者', NULL, 12, 8, '2022-01-04 16:00:00', '2022-01-04 16:00:00');
INSERT INTO `comm_post_comment` VALUES ('3163652592439853326', '2163652592439853325', '2163652592439853323', '3163652592439853324', '非常感谢作者的分享，对我的工作有很大帮助', NULL, 18, 10, '2022-01-06 20:00:00', '2022-01-06 20:00:00');
INSERT INTO `comm_post_comment` VALUES ('3365259243985332305', '2163652592439853324', '2163652592439853323', '3163652592439853231', '我觉得这篇文章还有些不足之处，希望作者能够改进', NULL, 6, 1, '2022-01-05 18:00:00', '2022-01-05 18:00:00');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商城活动表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of event
-- ----------------------------
INSERT INTO `event` VALUES ('2018072115284300903', '限时秒杀，只为更好的你', '## 活动细则\n\n1. 活动时间7月21日至7月24日\n2. 每日10:00、15:00、20:00准时开抢\n3. 商品数量有限，先到先得\n4. 如遇活动页面崩溃或者无法下单等问题，可联系客服进行处理。', 'event_miaosha.png', 0, '2023-05-16 21:43:09', '2023-05-18 21:43:09', -1, '2023-05-19 21:43:09', '2023-06-11 16:23:46');
INSERT INTO `event` VALUES ('2018072211041601402', '全场5折，疯狂抢购，数码特卖', '## 活动细则\n\n1. 活动时间7月22日至7月29日\n2. 家居类商品满299元包邮（港澳台及部分偏远地区除外）\n3. 如有任何疑问，请联系客服解决。', 'event_quanchangwuzhe.png', 0, '2023-05-18 21:43:09', '2023-08-25 21:43:09', 1, '2023-05-19 21:43:09', '2023-06-12 21:41:38');
INSERT INTO `event` VALUES ('2018072309121200101', '全场8折，数码疯狂购物季', '## 活动细则\n\n1. 活动时间7月23日至8月23日\n2. 全场商品8折优惠，数量有限，先到先得。\n3. 如遇活动页面崩溃或者无法下单等问题，可联系客服进行处理。', 'event_80persent.png', 0, '2023-05-17 21:43:09', '2023-07-23 21:43:09', 1, '2023-05-19 21:43:09', '2023-06-14 14:44:38');
INSERT INTO `event` VALUES ('2018072511282102304', '618大促，苹果产品特价献礼', '## 活动细则\n\n1. 活动时间6月18日至7月2日\n2. 苹果品牌商品7折优惠，仅限618大促期间。\n3. 如有任何疑问，请联系客服解决。', 'event_apple-iufmpmp1002563.png', 0, '2023-05-14 21:43:09', '2023-07-21 21:43:09', 1, '2023-05-19 21:43:09', '2023-06-12 21:41:15');
INSERT INTO `event` VALUES ('2018073008354504605', '购物狂欢节，快来参加吧', '## 活动细则\n\n1. 活动时间7月30日至8月5日\n2. 参与本活动即可享受免费领取商品一次。\n3. 每个账户仅限参加一次，请珍惜机会。\n4. 如遇活动页面崩溃或者无法下单等问题，可联系客服进行处理。', 'event_kuanhuang.png', 0, '2023-07-20 21:43:09', '2023-12-20 21:43:09', 0, '2023-05-19 21:43:09', '2023-06-12 21:40:52');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动商品关联表' ROW_FORMAT = DYNAMIC;

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
  `postage` decimal(10, 2) NOT NULL COMMENT '运费',
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
INSERT INTO `goods` VALUES ('104215909657394688', 'K75机械键盘', 'goods_k75_des1.png,goods_k75_des2.png,goods_k75_des3.png', '7998270576900033219', 479.00, 499.00, 0.00, 'goods_k75.png,goods_k75_1.png,goods_k75_2.png', 'goods1_mps20230609_020201.mp4', '广东省', '广州市', '越秀区', 1, 0, 2310, 2310, 3, 7, '2023-05-12 21:46:08', '2023-06-18 12:56:44');
INSERT INTO `goods` VALUES ('104215909657394689', 'Razer雷蛇毒蝰迷你版', 'goods2_des.png', '7998270576900033219', 119.00, 159.00, 0.00, 'goods2_des.png,goods2_sku.png,goods2_sku2.png', '', '广东省', '广州市', '天河区', 1, 0, 2322, 2322, 3, 7, '2023-05-12 21:46:08', '2023-06-24 17:40:05');
INSERT INTO `goods` VALUES ('104215909657394690', '小新pro16 2022', '联想笔记本电脑小新Pro16 2022游戏轻薄本(8核标压R7-6800H 16G 512G 2.5K 120Hz RTX3050Ti独显)灰 商务办公', '7998270576922333220', 2999.00, 3999.00, 0.00, 'xiaoxingpro16_1.png', 'xiaoxingpro16.mp4', '广东省', '广州市', '黄浦区', 1, 0, 2872, 2872, 3, 7, '2023-05-12 21:46:08', '2023-06-20 04:39:57');
INSERT INTO `goods` VALUES ('104215909657394691', 'FBB 视物所 Stone Age 定制数据线', '航插数据线 Stone Age 配色', '7998270576989233223', 40.00, 49.99, 0.00, 'hcxddxdaa.png,hcxddxdaa2.png', '', '广东省', '广州市', '天河区', 1, 0, 2872, 2872, 3, 7, '2023-05-12 21:46:08', '2023-06-24 17:19:13');
INSERT INTO `goods` VALUES ('104215909657394692', 'Type AppleDC充电器', '这是一个充电器的商品详情', '7998270577011533224', 129.99, 139.99, 3.00, 'applecdq.png', '', '广东省', '广州市', '黄浦区', 1, 1, 2020, 2020, 3, 7, '2023-05-12 21:46:08', '2023-06-20 05:09:25');
INSERT INTO `goods` VALUES ('104215909657394693', 'HuaWeiP40', '这是一个HuaWei的商品详情', '7998270577123033229', 3999.00, 4999.00, 0.00, 'huaweip401.png,huaweip402.png', '', '广东省', '广州市', '天河区', 1, 1, 2872, 2872, 3, 7, '2023-05-12 21:46:08', '2023-06-20 05:04:50');
INSERT INTO `goods` VALUES ('204215909657392293', 'SP SA Strong Spirit 陆军', '## SP SA Strong Spirit 陆军 \r\n![这是图片](goods_spsa_des.webp \"SP SA Strong Spirit 陆军\")\r\n', '7998270576877733323', 899.00, 899.00, 0.00, 'goods_spsa1.webp,goods_spsa2.webp,goods_spsa3.webp,goods_spsa_des.webp', NULL, '广东省', '广州市', '天河区', 1, 1, 1299, 1299, 3, 7, '2023-06-14 14:52:51', '2023-06-18 12:56:44');
INSERT INTO `goods` VALUES ('204215909657392294', 'KiWi K8 Alice alice配列 杏仁', '## KiWi K8 Alice alice配列 杏仁', '7998270576900033219', 688.00, 788.00, 0.00, 'goods_aclice1.webp,goods_aclice2.webp,goods_aclice3.webp,goods_aclice4.webp', NULL, '广东省', '广州市', '黄浦区', 1, 1, 3029, 3029, 3, 7, '2023-06-14 15:07:04', '2023-06-24 17:38:36');
INSERT INTO `goods` VALUES ('204215909657392295', 'Sirius 天狼星 Manta 矮轴 40% 蓝牙双模机械键盘套件', '## Sirius 天狼星 Manta 矮轴 40% 蓝牙双模机械键盘套件 ', '7998270576900033219', 1488.00, 1488.00, 0.00, 'goods_manta1.webp,goods_manta2.webp,goods_manta3.jpg,goods_manta4.jpg', NULL, '广东省', '广州市', '黄浦区', 1, 1, 4233, 4233, 3, 7, '2023-06-14 15:00:53', '2023-06-18 12:56:44');

-- ----------------------------
-- Table structure for goods_action
-- ----------------------------
DROP TABLE IF EXISTS `goods_action`;
CREATE TABLE `goods_action`  (
  `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一标识',
  `user_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户 ID',
  `goods_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品 ID',
  `type` tinyint(1) NOT NULL COMMENT '行为类型，0 表示点赞，1 表示收藏',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_index`(`user_id` ASC, `goods_id` ASC, `type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品点赞/收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods_action
-- ----------------------------
INSERT INTO `goods_action` VALUES ('1672554946555392001', '2163652592439853323', '104215909657394689', 1, '2023-06-24 18:39:08');
INSERT INTO `goods_action` VALUES ('1672656904230281218', '2163652592439853323', '204215909657392295', 1, '2023-06-25 01:24:17');
INSERT INTO `goods_action` VALUES ('1672656932839628801', '2163652592439853323', '204215909657392294', 1, '2023-06-25 01:24:24');
INSERT INTO `goods_action` VALUES ('1672656956579389442', '2163652592439853323', '104215909657394693', 1, '2023-06-25 01:24:30');
INSERT INTO `goods_action` VALUES ('1672827768426999810', '2163652592439853323', '104215909657394690', 1, '2023-06-25 12:43:14');

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
INSERT INTO `goods_category` VALUES ('7998270576877733218', '数码', NULL, 'shuma.gif', 1, 1, '2023-05-12 21:46:08', '2023-06-18 11:40:50');
INSERT INTO `goods_category` VALUES ('7998270576877733323', '键帽', '7998270576877733218', 'jianmao.webp', 1, 1, '2023-06-14 14:51:48', '2023-06-18 11:56:09');
INSERT INTO `goods_category` VALUES ('7998270576900033219', '键盘', '7998270576877733218', 'jianpang.webp', 1, 1, '2023-05-12 21:46:08', '2023-06-18 11:43:47');
INSERT INTO `goods_category` VALUES ('7998270576900033329', '鼠标', '7998270576900033218', 'shubiao.png', 1, 1, '2023-06-18 11:46:20', '2023-06-18 11:46:20');
INSERT INTO `goods_category` VALUES ('7998270576922333220', '电脑', '7998270576877733218', 'diannao.png', 2, 1, '2023-05-12 21:46:08', '2023-06-18 11:51:27');
INSERT INTO `goods_category` VALUES ('7998270576966933222', '配件', NULL, 'peijian.png', 3, 1, '2023-05-12 21:46:08', '2023-06-18 11:55:58');
INSERT INTO `goods_category` VALUES ('7998270576989233223', '数据线', '7998270576966933222', 'shujuxiang.png', 1, 1, '2023-05-12 21:46:08', '2023-06-18 11:53:22');
INSERT INTO `goods_category` VALUES ('7998270577011533224', '充电器', '7998270576966933222', 'cdtou.png', 2, 1, '2023-05-12 21:46:08', '2023-06-18 11:54:23');
INSERT INTO `goods_category` VALUES ('7998270577033833225', '游戏', '', 'youxi.png', 3, 1, '2023-05-12 21:46:08', '2023-06-18 11:54:27');
INSERT INTO `goods_category` VALUES ('7998270577056133226', '手机/平板', NULL, 'shoji.png', 2, 1, '2023-05-12 21:46:08', '2023-06-18 11:55:13');
INSERT INTO `goods_category` VALUES ('7998270577078433227', 'IQOO', '7998270577056133226', 'iqoo.gif', 1, 1, '2023-05-12 21:46:08', '2023-06-18 11:40:58');
INSERT INTO `goods_category` VALUES ('7998270577100733228', 'Samsung', '7998270577056133226', 'samsung.png', 2, 1, '2023-05-12 21:46:08', '2023-06-18 11:41:36');
INSERT INTO `goods_category` VALUES ('7998270577123033229', 'HuaWei', '7998270577056133226', 'huawei.png', 3, 1, '2023-05-12 21:46:08', '2023-06-18 11:42:42');
INSERT INTO `goods_category` VALUES ('7998270577145333230', 'XIAOMI', '7998270577123033229', 'xiaomi.png', 1, 1, '2023-05-12 21:46:08', '2023-06-18 11:56:38');

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
  INDEX `goods_id_index`(`goods_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods_sku
-- ----------------------------
INSERT INTO `goods_sku` VALUES ('8752215909657394694', '204215909657392293', 'base配列', NULL, '盒装', 200, 899.00, 'base配列 基本配列', 899.00, 'goods_spsa1.webp', 1, '2023-06-14 14:56:54', '2023-06-14 15:01:07');
INSERT INTO `goods_sku` VALUES ('8998270577145333231', '104215909657394688', '81键', '游戏机', 'TTC烈焰紫轴', 91, 479.00, 'K75机械键盘，104键，墨黑色，青轴', 499.00, 'goods_k75_1.png', 1, '2023-05-12 21:46:09', '2023-06-09 01:29:30');
INSERT INTO `goods_sku` VALUES ('8998270577145333232', '104215909657394688', '81键', '时光机', 'TTC金粉轴v2', 120, 479.00, 'K75机械键盘，104键，白色，红轴', 499.00, 'goods_k75_2.png', 1, '2023-05-12 21:46:09', '2023-06-09 01:29:34');
INSERT INTO `goods_sku` VALUES ('8998270577145333233', '104215909657394689', '常规', '黑色', '无线', 200, 119.00, '无线鼠标，黑色', 159.00, 'goods2_sku.png', 1, '2023-05-12 21:46:09', '2023-06-09 01:57:52');
INSERT INTO `goods_sku` VALUES ('8998270577145333234', '104215909657394689', '常规', '白色', '无线', 174, 119.00, '无线鼠标，白色', 159.00, 'goods2_sku2.png', 1, '2023-05-12 21:46:09', '2023-06-09 01:57:54');
INSERT INTO `goods_sku` VALUES ('8998270577145333235', '104215909657394690', '14寸', '银色', 'i5', 50, 2999.00, '小新pro，14寸，银色，i5处理器', 3999.00, 'xiaoxingpro16_1.png', 1, '2023-05-12 21:46:09', '2023-06-20 04:45:56');
INSERT INTO `goods_sku` VALUES ('8998270577145333236', '104215909657394690', '14寸', '银色', 'i7', 30, 3299.00, '小新pro，14寸，银色，i7处理器', 3999.00, 'xiaoxingpro16_1.png', 1, '2023-05-12 21:46:09', '2023-06-20 04:46:01');
INSERT INTO `goods_sku` VALUES ('8998270577145333237', '104215909657394691', '1m', '浅绿色', 'Type-C', 1000, 39.99, 'Type数据线，1米，浅绿色，Type-C', 19.99, 'hcxddxdaa.png', 1, '2023-05-12 21:46:09', '2023-06-22 15:58:36');
INSERT INTO `goods_sku` VALUES ('8998270577145333238', '104215909657394691', '1.5m', '浅绿色', 'Type-C', 800, 49.99, 'Type数据线，1.5米，浅绿色，Type-C', 19.99, 'hcxddxdaa2.png', 1, '2023-05-12 21:46:09', '2023-06-22 15:57:08');
INSERT INTO `goods_sku` VALUES ('8998270577145333239', '104215909657394692', '单口', '白色', 'Type-C', 800, 129.99, 'Type通用充电器，单口，白色，Type-C', 139.99, 'applecdq.png', 1, '2023-05-12 21:46:09', '2023-06-20 05:11:09');
INSERT INTO `goods_sku` VALUES ('8998270577145333240', '104215909657394692', '双口', '白色', 'Type-C', 600, 134.99, 'Type通用充电器，双口，白色，Type-C', 149.99, 'applecdq.png', 1, '2023-05-12 21:46:09', '2023-06-20 05:11:15');
INSERT INTO `goods_sku` VALUES ('8998270577145333241', '104215909657394693', '128GB', '黑色', 'P40', 300, 3999.00, 'HuaWeiP40，128GB，黑色，P40版本', 4999.00, 'huaweip402.png', 1, '2023-05-12 21:46:09', '2023-06-20 05:10:44');
INSERT INTO `goods_sku` VALUES ('8998270577145333242', '104215909657394693', '256GB', '黑色', 'P40', 200, 4199.00, 'HuaWeiP40，256GB，黑色，P40版本', 4999.00, 'huaweip402.png', 1, '2023-05-12 21:46:09', '2023-06-20 05:10:46');
INSERT INTO `goods_sku` VALUES ('9082847909657392295', '204215909657392295', '40%配列', '海军蓝', NULL, 300, 1488.00, '小巧轻薄，便携灵活的旋钮+矮轴键入体验', 1488.00, 'goods_manta1.webp', 1, '2023-06-14 15:04:47', '2023-06-14 15:04:47');
INSERT INTO `goods_sku` VALUES ('9082847909657392296', '204215909657392295', '40%配列', '浪花银', NULL, 300, 1488.00, '小巧轻薄，便携灵活的旋钮+矮轴键入体验', 1488.00, 'goods_manta4.jpg', 1, '2023-06-14 15:04:47', '2023-06-22 16:30:16');
INSERT INTO `goods_sku` VALUES ('9082847909657392297', '204215909657392294', 'Alice配列', '杏仁白', NULL, 300, 688.00, '', 1488.00, 'goods_aclice4.webp', 1, '2023-06-14 15:04:47', '2023-06-22 16:30:17');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单id',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `address_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货地址id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `paid_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `spend_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '付款价格',
  `total_price` decimal(10, 2) NOT NULL COMMENT '订单总价',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单状态，0表示待付款，1:已付款，2:已发货，3:待收货，4:已收货，5:已评价，6:已取消，7:已超时取消，8:发起退款，9:退款成功并取消',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id_i`(`user_id` ASC) USING BTREE,
  INDEX `address_id_i`(`address_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1662298744652484610', '2163652592439853323', '503399947050422325', '麻烦包装好一点！', '2023-05-27 11:26:56', 1685.00, 1695.00, 1, '2023-05-27 11:24:39', '2023-05-27 11:26:56');
INSERT INTO `orders` VALUES ('1662298780325040130', '2163652592439853323', '503399947050422325', '麻烦包装好一点！', NULL, NULL, 1695.00, 0, '2023-05-27 11:24:48', '2023-05-27 11:24:48');
INSERT INTO `orders` VALUES ('1662299369184342017', '2163652592439853323', '503399947050422325', '麻烦包装好一点！', '2023-05-27 11:27:16', 1685.00, 1695.00, 8, '2023-05-27 11:27:08', '2023-05-27 18:25:56');
INSERT INTO `orders` VALUES ('4998270577145333242', '2163652592439853323', '503399947050422323', '包装好！', NULL, NULL, 488.99, 0, '2023-05-25 17:34:53', '2023-05-25 17:34:53');
INSERT INTO `orders` VALUES ('4998270577145333243', '2163652592439853323', '503399947050422325', '', '2023-05-25 17:34:53', 129.99, 129.99, 6, '2023-05-25 17:34:53', '2023-06-06 22:52:09');

-- ----------------------------
-- Table structure for orders_comment
-- ----------------------------
DROP TABLE IF EXISTS `orders_comment`;
CREATE TABLE `orders_comment`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `orders_item_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单id',
  `sku_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '规格id',
  `is_recommend` int NULL DEFAULT 0 COMMENT '是否推荐（0:不推荐,1:推荐）',
  `is_anonymous` int NULL DEFAULT 0 COMMENT '是否匿名（0:不匿名,1:匿名）',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评价内容',
  `rate` decimal(2, 1) NOT NULL COMMENT '星级（0-5分）',
  `images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '图片集合',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `video` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id_i`(`user_id` ASC) USING BTREE,
  INDEX `sku_id_i`(`sku_id` ASC) USING BTREE,
  INDEX `orders_item_id_i`(`orders_item_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders_comment
-- ----------------------------
INSERT INTO `orders_comment` VALUES ('5998270577145333244', '2163652592439853323', '4998270577145333243', '8998270577145333234', 1, 1, '感觉很不错！强烈推荐！', 4.0, 'default.png', '2023-06-06 23:04:35', '2023-06-06 23:29:14', 'default.mp4');

-- ----------------------------
-- Table structure for orders_delivery
-- ----------------------------
DROP TABLE IF EXISTS `orders_delivery`;
CREATE TABLE `orders_delivery`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发货id',
  `orders_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单id',
  `delivery_num` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '快递编号',
  `send_address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发货地址',
  `deliver_address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发货时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orders_id_i`(`orders_id` ASC) USING BTREE,
  INDEX `delivery_num_i`(`delivery_num` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单发货表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders_delivery
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
  `coupon_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '优惠卷id',
  `reduce_price` decimal(10, 2) NOT NULL COMMENT '优惠额度',
  `final_price` decimal(10, 2) NOT NULL COMMENT '子订单最终总价',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orders_id_i`(`orders_id` ASC) USING BTREE,
  INDEX `sku_id_i`(`sku_id` ASC) USING BTREE,
  INDEX `shop_id_i`(`shop_id` ASC) USING BTREE,
  INDEX `activity_id_i`(`activity_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表项目表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders_item
-- ----------------------------
INSERT INTO `orders_item` VALUES ('1662298744677650433', '1662298744652484610', '8998270577145333231', 3, NULL, NULL, NULL, 0.00, 1437.00, '2023-05-27 11:24:39', '2023-05-27 11:24:39');
INSERT INTO `orders_item` VALUES ('1662298744677650434', '1662298744652484610', '8998270577145333234', 2, NULL, NULL, NULL, 0.00, 258.00, '2023-05-27 11:24:39', '2023-05-27 11:24:39');
INSERT INTO `orders_item` VALUES ('1662298780325040131', '1662298780325040130', '8998270577145333231', 3, NULL, NULL, NULL, 0.00, 1437.00, '2023-05-27 11:24:48', '2023-05-27 11:24:47');
INSERT INTO `orders_item` VALUES ('1662298780325040132', '1662298780325040130', '8998270577145333234', 2, NULL, NULL, NULL, 0.00, 258.00, '2023-05-27 11:24:48', '2023-05-27 11:24:47');
INSERT INTO `orders_item` VALUES ('1662299369196924930', '1662299369184342017', '8998270577145333231', 3, NULL, NULL, NULL, 0.00, 1437.00, '2023-05-27 11:27:08', '2023-05-27 11:27:08');
INSERT INTO `orders_item` VALUES ('1662299369196924931', '1662299369184342017', '8998270577145333234', 2, NULL, NULL, NULL, 0.00, 258.00, '2023-05-27 11:27:08', '2023-05-27 11:27:08');
INSERT INTO `orders_item` VALUES ('5998270577145333242', '4998270577145333242', '8998270577145333231', 1, NULL, NULL, NULL, 0.00, 479.00, '2023-05-25 17:34:53', '2023-05-25 17:34:53');
INSERT INTO `orders_item` VALUES ('5998270577145333243', '4998270577145333242', '8998270577145333237', 1, NULL, NULL, NULL, 0.00, 9.99, '2023-05-25 17:34:53', '2023-05-25 17:34:53');
INSERT INTO `orders_item` VALUES ('5998270577145333244', '4998270577145333243', '8998270577145333234', 1, NULL, NULL, NULL, 0.00, 129.00, '2023-05-25 17:34:53', '2023-05-25 17:34:53');

-- ----------------------------
-- Table structure for recharge_combo
-- ----------------------------
DROP TABLE IF EXISTS `recharge_combo`;
CREATE TABLE `recharge_combo`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '套餐名称',
  `discount` float NOT NULL DEFAULT 0 COMMENT '折扣',
  `amount` decimal(10, 2) NOT NULL COMMENT '充值额度',
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
INSERT INTO `shop_cart` VALUES ('1672542478869323777', '1672541592562556929', '9082847909657392297', 9, NULL, NULL, '2023-06-24 17:49:36', '2023-06-25 17:23:59');
INSERT INTO `shop_cart` VALUES ('1672678707405271042', '2163652592439853323', '8998270577145333241', 1, NULL, NULL, '2023-06-25 02:50:55', '2023-06-25 14:17:59');
INSERT INTO `shop_cart` VALUES ('1672827741944164354', '2163652592439853323', '8998270577145333236', 4, NULL, NULL, '2023-06-25 12:43:08', '2023-06-25 16:17:44');
INSERT INTO `shop_cart` VALUES ('1672893031268757506', '1672892799952891905', '9082847909657392295', 2, NULL, NULL, '2023-06-25 17:02:34', '2023-06-25 17:02:43');

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
INSERT INTO `sys_role` VALUES ('5819236053864939521', NULL, '超级管理员', 'ROLE_ADMIN', '系统的全部权限,包括增删查改管理员', 1, '2023-05-06 01:44:29', '2023-05-27 11:21:53');
INSERT INTO `sys_role` VALUES ('5819236053864939522', '5819236053864939521', '用户管理员', 'ROLE_ADMIN', '对普通数据增删查改', 1, '2023-05-06 01:44:29', '2023-05-27 11:21:54');
INSERT INTO `sys_role` VALUES ('5819236053864939523', '5819236053864939522', '查看管理员', 'ROLE_ADMIN', '检查查看数据的管理员', 1, '2023-05-06 01:44:29', '2023-05-27 11:21:54');
INSERT INTO `sys_role` VALUES ('5819236053864939524', '5819236053864939522', '客服', 'ROLE_SERVICE', '客服', 2, '2023-05-06 01:44:29', '2023-05-27 11:22:01');
INSERT INTO `sys_role` VALUES ('5819236053864939525', NULL, '普通用户', 'ROLE_CUSTOMER', '普通购买无优惠无额外奖励', 0, '2023-05-06 01:44:29', '2023-05-27 11:22:08');
INSERT INTO `sys_role` VALUES ('5819236053864939526', '', '1级会员', 'ROLE_CUSTOMER', '有对应代金卷、优惠卷，额度4%', 0, '2023-05-06 01:44:29', '2023-05-27 11:23:59');
INSERT INTO `sys_role` VALUES ('5819236053864939527', '', '2级会员', 'ROLE_CUSTOMER', '有对应代金卷、优惠卷，额度10%', 0, '2023-05-06 01:44:29', '2023-05-27 11:23:57');
INSERT INTO `sys_role` VALUES ('5819236053864939528', '', '3级会员', 'ROLE_CUSTOMER', '有对应代金卷、优惠卷，额度15%', 0, '2023-05-06 01:44:29', '2023-05-27 11:23:57');

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
INSERT INTO `sys_user` VALUES ('1652246616668012545', 'Lulu2333', '$2a$10$DjJfKH8I5j7EGBdlA5d.CeX/DPjYMyb978hT7EZgv9pnDz3IAkcbe', NULL, '13415000001', '新用户', '保密', '', NULL, 1, '2023-04-29 09:41:05', '2023-06-18 00:28:58', NULL, NULL, 1, 0, 1);
INSERT INTO `sys_user` VALUES ('1653240351484801026', 'admin233', '$2a$10$trFdiRCBradkZdD7S.xesupAXTj7xwwD1u3KSrgTaq436EmilDPRa', NULL, '13415048700', '新用户', '保密', '', NULL, 1, '2023-05-02 11:29:50', '2023-06-18 00:29:06', '2023-06-14 16:43:30', NULL, 1, 0, 1);
INSERT INTO `sys_user` VALUES ('1666275306549604354', 'huya2333', '$2a$10$4uXuEtoFNmWoyfxoNGSPSuPwWzrrToRteDcCNaZp6R/0ymeu4gA3C', NULL, '13415000002', '新用户', '保密', '', NULL, 0, '2023-06-07 10:46:06', '2023-06-25 17:19:09', '2023-06-25 17:19:09', NULL, 1, 0, 1);
INSERT INTO `sys_user` VALUES ('1670096328636944385', 'Zxcvvfg', '$2a$10$e3VWHnD.ugtJM0vt2r6iz.BUQNOhzUckYDH28j.eO3FzrKK9/Chve', NULL, '13412233479', '新用户', '保密', '04b47d044eee4902aa1cc9041ffdea9e.png', NULL, 0, '2023-06-17 23:49:28', '2023-06-23 10:54:25', '2023-06-17 23:49:32', NULL, 1, 0, 1);
INSERT INTO `sys_user` VALUES ('1670124400689999874', 'kiwi233', '$2a$10$ofgdkcHa3FU0WgC9FrqvzOX74LD/uV63ExcAjobTEHWkYTRXIUrfm', NULL, '13415000023', '新用户', '保密', '356a7cb268884f9996fdaaa597eb109d.png', NULL, 0, '2023-06-18 01:41:01', '2023-06-23 10:54:18', '2023-06-18 03:41:52', NULL, 1, 0, 1);
INSERT INTO `sys_user` VALUES ('1672541592562556929', 'zhangsan', '$2a$10$KnxQRGu.V4x7Rqwd.m80a.vwCwergwS3kSs5DfBX4ELnGhkXg146S', NULL, '13415000022', '新用户', '保密', '7b49c1661497415d800fb674dcd1220d.jpg', NULL, 0, '2023-06-24 17:46:05', '2023-06-25 17:22:20', '2023-06-25 17:22:20', NULL, 1, 0, 1);
INSERT INTO `sys_user` VALUES ('1672892799952891905', 'ZF2333', '$2a$10$WpkmG3auzcWrQcgNssabLeMgza0corRW3LPjeKx.g.57P5pDIi4VK', NULL, '13415000033', '新用户', '保密', 'b416502c564e4254b57b8dbcd863903d.png', NULL, 0, '2023-06-25 17:01:39', '2023-06-25 17:04:40', '2023-06-25 17:04:40', NULL, 1, 0, 1);
INSERT INTO `sys_user` VALUES ('2163652592439853323', 'Kiwi2333', '$2a$10$s68J2cbazN3oL9Ag8tFO5.GtzVF5Ns26fgTqrgLC1hD2oxKuCP30y', '1329634286@qq.com', '13415000000', 'Kiwi2333', '男', '3baf4d47980f44df8d4c9066a3c2102e.png', NULL, 0, '2022-03-01 10:00:00', '2023-06-25 17:22:14', '2023-06-25 17:22:14', '192.168.1.1', 1, 1, 1);

-- ----------------------------
-- Table structure for sys_user_address
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_address`;
CREATE TABLE `sys_user_address`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `is_default` int NOT NULL DEFAULT 0 COMMENT '是否默认',
  `province` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省份',
  `city` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '城市',
  `county` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区/县',
  `address` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `postal_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮编',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `address_user_id`(`user_id` ASC) USING BTREE,
  INDEX `address_phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户收货地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_address
-- ----------------------------
INSERT INTO `sys_user_address` VALUES ('1672900686569811970', '张三', '1672541592562556929', 1, '北京市', '市辖区', '东城区', '天寿路', '110101', '13415000000', '2023-06-25 17:32:59', '2023-06-25 17:32:59');
INSERT INTO `sys_user_address` VALUES ('1672900739996856321', '张三', '1672541592562556929', 1, '北京市', '市辖区', '东城区', '天寿路', '110101', '13415000000', '2023-06-25 17:33:12', '2023-06-25 17:33:12');
INSERT INTO `sys_user_address` VALUES ('503399947050422324', 'kiwi233', '1652246616668012545', 0, '江苏省', '南京市', '白下区', '长白路456号', '210000', '13912113421', '2023-05-16 18:40:10', '2023-05-16 18:40:30');
INSERT INTO `sys_user_address` VALUES ('503399947050422325', '猕猴桃', '2163652592439853323', 1, '广东省', '汕头市', '潮阳区', '谷饶石门', '515159', '13912113421', '2023-05-16 18:40:10', '2023-05-16 22:28:04');

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
INSERT INTO `sys_user_role` VALUES ('1669928450705035265', '1669928450642120706', '5819236053864939525', NULL, '2023-06-17 12:42:23', '2023-06-17 12:42:23');
INSERT INTO `sys_user_role` VALUES ('1670096328666304514', '1670096328636944385', '5819236053864939525', NULL, '2023-06-17 23:49:28', '2023-06-17 23:49:28');
INSERT INTO `sys_user_role` VALUES ('1670124400790663170', '1670124400689999874', '5819236053864939525', NULL, '2023-06-18 01:41:01', '2023-06-18 01:41:01');
INSERT INTO `sys_user_role` VALUES ('1672541592604499969', '1672541592562556929', '5819236053864939525', NULL, '2023-06-24 17:46:05', '2023-06-24 17:46:05');
INSERT INTO `sys_user_role` VALUES ('1672892800015806465', '1672892799952891905', '5819236053864939525', NULL, '2023-06-25 17:01:39', '2023-06-25 17:01:39');
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
INSERT INTO `sys_user_salt` VALUES ('1666275306549604354', 'Q/DtH9DdYCRRHw==');
INSERT INTO `sys_user_salt` VALUES ('1670096328636944385', 'IcFgFSaTreNHAw==');
INSERT INTO `sys_user_salt` VALUES ('1670124400689999874', '7pToUC6BRBxynw==');
INSERT INTO `sys_user_salt` VALUES ('1672541592562556929', '+LoGJvkFmapozA==');
INSERT INTO `sys_user_salt` VALUES ('1672892799952891905', 'zBG0bGNRmp4XWg==');
INSERT INTO `sys_user_salt` VALUES ('2163652592439853323', '4wechxge23ex21');

-- ----------------------------
-- Table structure for user_bills
-- ----------------------------
DROP TABLE IF EXISTS `user_bills`;
CREATE TABLE `user_bills`  (
  `id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账单id',
  `user_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `orders_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单id',
  `voucher_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '代金卷id',
  `amount` decimal(10, 2) NOT NULL COMMENT '收支额度',
  `title` char(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '日常消费' COMMENT '消费类型名称',
  `type` int NOT NULL DEFAULT 0 COMMENT '收支类型，0:支出 1:收入',
  `currency_type` int NOT NULL DEFAULT 0 COMMENT '类型，0:金钱,1:积分',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id_i`(`user_id` ASC) USING BTREE,
  INDEX `orders_id_i`(`orders_id` ASC) USING BTREE,
  INDEX `voucher_id_i`(`voucher_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_bills
-- ----------------------------
INSERT INTO `user_bills` VALUES ('1662404762208243714', '2163652592439853323', '1662299369184342017', NULL, 1685.00, '退款收入', 1, 0, '2023-05-27 18:25:56', '2023-05-27 18:25:56');
INSERT INTO `user_bills` VALUES ('1662404762208243715', '2163652592439853323', '1662299369184342017', NULL, 1000.00, '积分收入', 1, 1, '2023-05-27 18:25:56', '2023-05-27 18:25:56');
INSERT INTO `user_bills` VALUES ('2163652592439853333', '2163652592439853323', '1662299369184342017', NULL, 1685.00, '购物消费', 0, 0, '2023-05-27 16:11:23', '2023-05-27 16:11:29');
INSERT INTO `user_bills` VALUES ('2163652592439853334', '2163652592439853323', '1662299369184342017', NULL, 1000.00, '积分消费', 0, 1, '2023-05-27 16:12:42', '2023-05-27 16:13:00');
INSERT INTO `user_bills` VALUES ('2662298744652484622', '2163652592439853323', '1662298744652484610', NULL, 1685.00, '购物消费', 0, 0, '2023-05-27 16:04:52', '2023-05-27 16:10:27');
INSERT INTO `user_bills` VALUES ('2998270577145333233', '2163652592439853323', '1662298744652484610', NULL, 1000.00, '积分消费', 0, 1, '2023-05-27 16:09:50', '2023-05-27 16:09:50');
INSERT INTO `user_bills` VALUES ('4998270577145333243', '2163652592439853323', '4998270577145333243', NULL, 129.99, '购物消费', 0, 0, '2023-05-27 16:13:29', '2023-05-27 16:13:42');

-- ----------------------------
-- Table structure for user_wallet
-- ----------------------------
DROP TABLE IF EXISTS `user_wallet`;
CREATE TABLE `user_wallet`  (
  `user_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `balance` decimal(10, 2) NOT NULL COMMENT '余额',
  `recharge` decimal(10, 2) NOT NULL COMMENT '充值总额',
  `spend` decimal(10, 2) NOT NULL COMMENT '消费总额',
  `points` bigint NOT NULL DEFAULT 0 COMMENT '总积分',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `user_wallet_index`(`balance` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_wallet
-- ----------------------------
INSERT INTO `user_wallet` VALUES ('1652246616668012545', 3000.00, 3000.00, 0.00, 12230, '2023-04-27 18:22:54', '2023-05-25 18:25:43');
INSERT INTO `user_wallet` VALUES ('1666275306549604354', 0.00, 0.00, 0.00, 200, '2023-06-07 10:48:43', '2023-06-07 10:48:43');
INSERT INTO `user_wallet` VALUES ('1666278297176412162', 0.00, 0.00, 0.00, 0, '2023-06-07 10:57:59', '2023-06-07 10:57:59');
INSERT INTO `user_wallet` VALUES ('1670096328636944385', 48.00, 48.00, 48.00, 500, '2023-06-17 23:49:28', '2023-06-17 23:49:28');
INSERT INTO `user_wallet` VALUES ('1670124400689999874', 48.00, 48.00, 48.00, 500, '2023-06-18 01:41:01', '2023-06-18 01:41:01');
INSERT INTO `user_wallet` VALUES ('1672541592562556929', 48.00, 48.00, 48.00, 500, '2023-06-24 17:46:05', '2023-06-24 17:46:05');
INSERT INTO `user_wallet` VALUES ('1672892799952891905', 48.00, 48.00, 48.00, 500, '2023-06-25 17:01:39', '2023-06-25 17:01:39');
INSERT INTO `user_wallet` VALUES ('2163652592439853323', 5685.00, 4000.00, 0.00, 25010, '2023-04-28 18:22:54', '2023-05-27 18:25:55');

SET FOREIGN_KEY_CHECKS = 1;
