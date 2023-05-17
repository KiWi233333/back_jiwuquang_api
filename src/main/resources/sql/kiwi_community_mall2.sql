DROP TABLE IF EXISTS event;
CREATE TABLE event (
                       id  VARCHAR(20) NOT NULL COMMENT '活动ID' PRIMARY KEY,
                       title VARCHAR(50) NOT NULL COMMENT '活动标题',
                       details TEXT NULL COMMENT "活动详情描述md",
                       images TEXT COMMENT '活动图片集(,隔开)',
                       level  TINYINT(1) NOT NULL DEFAULT 0 COMMENT '权重 越大权重越高',
                       start_time DATETIME NOT NULL COMMENT '活动开始时间',
                       end_time DATETIME NOT NULL COMMENT '活动结束时间',
                       status TINYINT(1) NOT NULL DEFAULT 0 COMMENT '活动状态，0表示未开始，1表示正在进行，-1表示已结束',
                       created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='商城活动表';
INSERT INTO event (id, title, details, images, start_time, end_time, status)
VALUES
    ("2018072309121200101", '全场8折，疯狂购物季', '## 活动细则\n\n1. 活动时间7月23日至8月23日\n2. 全场商品8折优惠，数量有限，先到先得。\n3. 如遇活动页面崩溃或者无法下单等问题，可联系客服进行处理。', 'default.png', NOW() - INTERVAL 2 DAY, NOW() + INTERVAL 4 DAY, 1),
    ("2018072211041601402", '299元起包邮，家居特卖', '## 活动细则\n\n1. 活动时间7月22日至7月29日\n2. 家居类商品满299元包邮（港澳台及部分偏远地区除外）\n3. 如有任何疑问，请联系客服解决。', 'default.png', NOW() - INTERVAL 1 DAY, NOW() + INTERVAL 6 DAY, 1),
    ("2018072115284300903", '限时秒杀，只为更好的你', '## 活动细则\n\n1. 活动时间7月21日至7月24日\n2. 每日10:00、15:00、20:00准时开抢\n3. 商品数量有限，先到先得\n4. 如遇活动页面崩溃或者无法下单等问题，可联系客服进行处理。', 'default.png', NOW() - INTERVAL 3 DAY, NOW() - INTERVAL 1 DAY, -1),
    ("2018072511282102304", '618大促，苹果产品特价献礼', '## 活动细则\n\n1. 活动时间6月18日至7月2日\n2. 苹果品牌商品7折优惠，仅限618大促期间。\n3. 如有任何疑问，请联系客服解决。', 'default.png', NOW() - INTERVAL 5 DAY, NOW() + INTERVAL 2 DAY, 1),
    ("2018073008354504605", '零元购物狂欢节，快来参加吧', '## 活动细则\n\n1. 活动时间7月30日至8月5日\n2. 参与本活动即可享受免费领取商品一次。\n3. 每个账户仅限参加一次，请珍惜机会。\n4. 如遇活动页面崩溃或者无法下单等问题，可联系客服进行处理。', 'default.png', NOW() - INTERVAL 2 DAY, NOW() + INTERVAL 2 DAY, 0),
    ("2018072809375302506", '夏日清凉，买就送空调', '## 活动细则\n\n1. 活动时间7月28日至8月31日\n2. 购买价值2999元及以上家电类商品，即可获赠可调节风速空调一台。\n3. 活动期间，赠品数量有限，先到先得。\n4. 如有任何疑问，请联系客服解决。', 'default.png', NOW() - INTERVAL 1 DAY, NOW() + INTERVAL 10 DAY, 0);



DROP TABLE IF EXISTS event_goods;
CREATE TABLE event_goods(
                            `id` VARCHAR(20) NOT NULL COMMENT "id",
                            `event_id` VARCHAR(20) NOT NULL COMMENT "活动id",
                            `goods_id` VARCHAR(20) NOT NULL COMMENT "商品id",
                            event_price decimal(10, 2) NOT NULL COMMENT '活动价',
                            created_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            updated_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            INDEX event_id_i (event_id),
                            INDEX goods_id_i (goods_id)
) COMMENT ="活动商品关联表";




-- CREATE TABLE orders (
--   id INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
--   user_id INT NOT NULL COMMENT '用户ID',
--   order_time DATETIME NOT NULL COMMENT '下单时间',
--   total_price DECIMAL(10,2) NOT NULL COMMENT '订单总价',
--   status TINYINT(1) NOT NULL DEFAULT 0 COMMENT '订单状态，0表示待付款，1表示已付款，-1表示已取消',
--   created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
--   updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
--   PRIMARY KEY (id),
--   FOREIGN KEY (user_id) REFERENCES users(id)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
                        id VARCHAR(20) NOT NULL COMMENT "订单id",
                        user_id VARCHAR(20) NOT NULL COMMENT '用户id',
                        order_time DATETIME NOT NULL COMMENT '下单时间',
                        total_price DECIMAL(10,2) NOT NULL COMMENT '订单总价',
                        address_id VARCHAR(20) NOT NULL COMMENT '收货地址',
                        status TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单状态，0表示待付款，1表示已付款，2表示已发货，3表示待收货，4表示已收货，5表示已评价，6表示已取消',
                        created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (id),
                        INDEX user_id_i (user_id),
                        INDEX status_i (status)
) COMMENT='订单表';
