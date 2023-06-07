-- 社区圈分类表 comm_category
DROP TABLE IF EXISTS comm_category;
CREATE TABLE comm_category  (
                                id char(20)  NOT NULL,
                                name varchar(50)  NOT NULL COMMENT '分类名称',
                                parent_id char(20)  NULL DEFAULT NULL COMMENT '父id',
                                icon varchar(255)  NULL DEFAULT NULL COMMENT '图标',
                                sort_order int NULL DEFAULT 0 COMMENT '权重0,1,2,3增加权重',
                                is_show tinyint(1) NULL DEFAULT 1 COMMENT '是否展示',
                                create_time datetime NULL DEFAULT CURRENT_TIMESTAMP,
                                update_time datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (id) USING BTREE,
                                UNIQUE INDEX name(name ASC) USING BTREE,
                                INDEX comm_category_parent_id(parent_id ASC) USING BTREE
) COMMENT '社区圈分类表';

INSERT INTO comm_category VALUES ('5998270576966933222', '编程', NULL, NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_category VALUES ('5998270576877733218', '机械键盘', NULL, NULL, 1, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_category VALUES ('5998270576900033219', '键帽', '5998270576877733218', NULL, 1, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_category VALUES ('5998270576922333220', '航插线', '5998270576877733218', NULL, 2, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_category VALUES ('5998270576966933223', '摄影', NULL, NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_category VALUES ('5198270576966933224', '游戏', NULL, NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_category VALUES ('5298270576966933213', '数码', NULL, NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_category VALUES ('5398270576966933214', '生活', NULL, NULL, 3, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');

-- 2、社区帖子表
DROP TABLE IF EXISTS comm_post;
CREATE TABLE comm_post  (
                            id CHAR(20)  NOT NULL COMMENT '帖子ID',
                            user_id CHAR(20)  NOT NULL COMMENT '发布用户ID',
                            category_id char(20)  NOT NULL COMMENT '帖子分类ID',
                            title varchar(255)  NOT NULL COMMENT '帖子标题',
                            content text  NOT NULL COMMENT '帖子内容',
                            images text  NOT NULL COMMENT '图片集合',
                            tags VARCHAR(255) COMMENT '帖子标签',
                            is_essence TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否加精，0-不加精，1-加精',
                            comment_top CHAR(20) COMMENT '评论置顶的评论ID',
                            views int(11) DEFAULT 0 COMMENT '浏览次数',
                            comments int(11) DEFAULT 0 COMMENT '评论次数',
                            likes int(11) DEFAULT 0 COMMENT '点赞次数',
                            status tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态：0-草稿，1-已发布，2-已删除',
                            create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (id) USING BTREE,
                            INDEX comm_post_user_id(user_id ASC) USING BTREE,
                            INDEX comm_category_id(category_id ASC) USING BTREE
) COMMENT '社区帖子表';

INSERT INTO comm_post VALUES ('2163652592439853321', '2163652592439853323', '2163652592439853321', '如何学习编程', '编程是一项很有趣的技能，但是新手可能会遇到很多困难，大家有什么好的学习方法和经验分享一下吗？', '', '编程, 学习', 1, NULL, 100, 20, 50, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_post VALUES ('2163652592439853322', '1666278297176412162', '2163652592439853323', '分享一下摄影技巧', '我是一名摄影爱好者，分享一下我的拍照技巧和经验，希望能帮助到大家。', 'default.png', '摄影, 技巧', 1, NULL, 200, 30, 80, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_post VALUES ('2163652592439853323', '1666275306549604354', '2163652592439853328', '家里的新家具很漂亮', '最近家里买了一些新家具，很漂亮，分享一下给大家看看。', 'default.png', '生活, 家具', 0, NULL, 50, 10, 20, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_post VALUES ('2163652592439853324', '1666275306549604354', '2163652592439853326', '最近玩的一款游戏很好玩', '最近玩的一款游戏很好玩，推荐给大家。', '', '游戏, 推荐', 0, NULL, 80, 15, 30, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_post VALUES ('2163652592439853325', '2163652592439853323', '2163652592439853324', '新买的机械键盘很好用', '最近新买了一款机械键盘，手感很好，打字很舒服。', 'default.png', '机械键盘, 体验', 1, NULL, 120, 25, 50, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_post VALUES ('2163652592439853326', '1666278297176412162', '2163652592439853322', '推荐一些好看的键帽', '最近收集了一些好看的键帽，分享给大家。', 'default.png', '键帽, 收藏', 0, NULL, 60, 12, 25, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_post VALUES ('2163652592439853327', '1666275306549604354', '2163652592439853327', '推荐一些好用的数码产品', '最近用了一些好用的数码产品，分享给大家。', '', '数码, 推荐', 0, NULL, 90, 18, 40, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');
INSERT INTO comm_post VALUES ('2163652592439853328', '2163652592439853323', '2163652592439853324', '推荐一些好玩的游戏', '最近玩了一些好玩的游戏，推荐给大家。', '', '游戏, 推荐', 0, NULL, 70, 14, 35, 1, '2023-05-12 21:46:08', '2023-05-12 21:46:08');

-- 3、社区评论表
DROP TABLE IF EXISTS comm_post_comment;
CREATE TABLE comm_post_comment (
                                   id CHAR(20)  NOT NULL COMMENT '评论ID',
                                   post_id CHAR(20)  NOT NULL COMMENT '所属帖子ID',
                                   user_id CHAR(20)  NOT NULL COMMENT '评论用户ID',
                                   parent_id CHAR(20)   COMMENT '父级评论ID',
                                   content text NOT NULL COMMENT '评论内容',
                                   images text  COMMENT '评论携带图片',
                                   views int(11) unsigned NOT NULL DEFAULT '0' COMMENT '浏览量',
                                   likes int(11) unsigned NOT NULL DEFAULT '0' COMMENT '点赞数',
                                   create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   PRIMARY KEY (id) USING BTREE ,
                                   KEY post_id (post_id) USING BTREE,
                                   KEY user_id (user_id) USING BTREE,
                                   INDEX parent_id_i (parent_id)
) COMMENT='社区评论表';

INSERT INTO comm_post_comment (id, post_id, user_id, parent_id, content, images, views, likes, create_time, update_time)
VALUES ('3163652592439853231', '2163652592439853321', '2163652592439853323', NULL, '这篇文章写得真好！', NULL, 10, 5, '2022-01-01 10:00:00', '2022-01-01 10:00:00'),
       ('2163652592439853302', '2163652592439853322', '2163652592439853323', NULL, '我觉得这篇文章有些问题，需要改进', NULL, 8, 3, '2022-01-02 12:00:00', '2022-01-02 12:00:00'),
       ('2163652592439853233', '2163652592439853321', '2163652592439853323', '3163652592439853231', '同意楼上的观点，写得非常好', NULL, 5, 2, '2022-01-03 14:00:00', '2022-01-03 14:00:00'),
       ('3163652592439853324', '2163652592439853322', '2163652592439853323', NULL, '这篇文章让我受益匪浅，感谢作者', NULL, 12, 8, '2022-01-04 16:00:00', '2022-01-04 16:00:00'),
       ('3365259243985332305', '2163652592439853324', '2163652592439853323', '3163652592439853231', '我觉得这篇文章还有些不足之处，希望作者能够改进', NULL, 6, 1, '2022-01-05 18:00:00', '2022-01-05 18:00:00'),
       ('3163652592439853326', '2163652592439853325', '2163652592439853323', '3163652592439853324', '非常感谢作者的分享，对我的工作有很大帮助', NULL, 18, 10, '2022-01-06 20:00:00', '2022-01-06 20:00:00'),
       ('3163652592439852307', '2163652592439853321', '2163652592439853323', NULL, '我觉得这篇文章非常好，已经转发给我的朋友们了', NULL, 15, 7, '2022-01-07 22:00:00', '2022-01-07 22:00:00'),
       ('3163652592439853308', '2163652592439853322', '2163652592439853323', '3163652592439852307', '我也非常喜欢这篇文章，已经转发到我的朋友圈了', NULL, 3, 1, '2022-01-08 00:00:00', '2022-01-08 00:00:00');


-- 4、点赞/收藏表
DROP TABLE IF EXISTS comm_post_action;
CREATE TABLE comm_post_action (
                                  id CHAR(20) NOT NULL COMMENT '唯一标识',
                                  user_id CHAR(20) NOT NULL COMMENT '用户 ID',
                                  post_id CHAR(20) NOT NULL COMMENT '帖子 ID',
                                  type TINYINT(1) NOT NULL COMMENT '行为类型，0 表示点赞，1 表示收藏',
                                  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  PRIMARY KEY (id),
                                  UNIQUE INDEX unique_index (user_id, post_id, type)
) ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='点赞/收藏表';

INSERT INTO comm_post_action (id, user_id, post_id, type, create_time) VALUES
('616365259243985321', '2163652592439853323', '2163652592439853321', 0, '2023-01-01 12:00:00'),
('663652592439853222', '2163652592439853323', '2163652592439853323', 0, '2023-01-02 13:00:00'),
('616365259243983323', '2163652592439853323', '2163652592439853321', 1, '2023-01-03 14:00:00'),
('616365259243953324', '2163652592439853323', '2163652592439853322', 1, '2023-01-04 15:00:00'),
('616365259243853325', '2163652592439853323', '2163652592439853324', 1, '2023-01-06 17:00:00');



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
  