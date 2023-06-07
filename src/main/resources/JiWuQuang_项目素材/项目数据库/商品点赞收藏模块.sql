
-- 5、商品点赞/收藏表
DROP TABLE IF EXISTS goods_action;
CREATE TABLE goods_action (
                              id CHAR(20) NOT NULL COMMENT '唯一标识',
                              user_id CHAR(20) NOT NULL COMMENT '用户 ID',
                              goods_id CHAR(20) NOT NULL COMMENT '商品 ID',
                              type TINYINT(1) NOT NULL COMMENT '行为类型，0 表示点赞，1 表示收藏',
                              create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              PRIMARY KEY (id),
                              UNIQUE INDEX unique_index (user_id, goods_id, type)
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='商品点赞/收藏表';

-- 生成测试数据
INSERT INTO goods_action (id, user_id, goods_id, type) VALUES
('1666275306549604351', '1666275306549604354', '104215909657394688', 0),
('1666275306549604352', '1666278297176412162', '104215909657394689', 0),
('1666275306549604353', '2163652592439853323', '104215909657394690', 1),
('1666275306549604354', '1666275306549604354', '104215909657394691', 1),
('1666275306549604355', '1666278297176412162', '104215909657394692', 0),
('1666275306549604356', '2163652592439853323', '104215909657394693', 0);