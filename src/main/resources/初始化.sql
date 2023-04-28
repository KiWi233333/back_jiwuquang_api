DROP DATABASE IF EXISTS  kiwi_community_mall;
CREATE DATABASE kiwi_community_mall;
USE kiwi_community_mall;

CREATE TABLE user (
                      id VARCHAR(20) NOT NULL PRIMARY KEY,
                      username VARCHAR(50) UNIQUE ,
                      password CHAR(60) NOT NULL,
                      email VARCHAR(50) UNIQUE,
                      phone VARCHAR(15) UNIQUE,
                      nickname VARCHAR(50) NOT NULL,
                      gender ENUM('男', '女', '保密') DEFAULT '保密',
                      avatar VARCHAR(128) DEFAULT 'default.png',
                      create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                      update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      last_login_time DATETIME DEFAULT NULL,
                      last_login_ip VARCHAR(50) DEFAULT NULL,
                      status ENUM('正常', '禁用') NOT NULL DEFAULT '正常',
                      is_email_verified INT NOT NULL DEFAULT 0,
                      is_phone_verified INT NOT NULL DEFAULT 0,
                      member_level INT NOT NULL DEFAULT 1
);

INSERT INTO `user` (`id`, `username`, `password`, `email`, `phone`, `nickname`, `gender`, `avatar`, `create_time`, `update_time`, `last_login_time`, `last_login_ip`, `status`, `is_email_verified`, `is_phone_verified`, `member_level`) VALUES ('2163652592439853323', 'Kiwi2333', '$2a$10$s68J2cbazN3oL9Ag8tFO5.GtzVF5Ns26fgTqrgLC1hD2oxKuCP30y', '1329634@qq.com', '13415000000', 'Kiwi2333', '男', 'default.png', '2022-03-01 10:00:00', '2023-04-27 18:40:54', '2022-03-01 10:00:00', '192.168.1.1', '正常', 1, 1, 1);

INSERT INTO `user` (`id`, `username`, `password`, `email`, `phone`, `nickname`, `gender`, `avatar`, `create_time`, `update_time`, `last_login_time`, `last_login_ip`, `status`, `is_email_verified`, `is_phone_verified`, `member_level`) VALUES ('1651536259439853569', 'lulu233333', '123456', NULL, 'lulu233333', '新用户', '保密', 'default.png', '2023-04-27 18:38:23', '2023-04-27 18:38:23', NULL, NULL, '正常', 0, 1, 1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `phone`, `nickname`, `gender`, `avatar`, `create_time`, `update_time`, `last_login_time`, `last_login_ip`, `status`, `is_email_verified`, `is_phone_verified`, `member_level`) VALUES ('1623536259439853523', 'tom_jones', '123456', 'tom_jones@163.com', '13811116666', 'Tom Jones', '男', 'default.png', '2022-03-05 14:00:00', '2023-04-27 18:40:53', '2022-03-05 14:00:00', '192.168.1.5', '禁用', 0, 1, 1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `phone`, `nickname`, `gender`, `avatar`, `create_time`, `update_time`, `last_login_time`, `last_login_ip`, `status`, `is_email_verified`, `is_phone_verified`, `member_level`) VALUES ('1623532312979853523', 'bob_smith', '123456', 'bob_smith@qq.com', '13811114444', 'Bob Smith', '男', 'default.png', '2022-03-03 12:00:00', '2023-04-27 18:40:55', '2022-03-03 12:00:00', '192.168.1.3', '正常', 1, 1, 3);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `phone`, `nickname`, `gender`, `avatar`, `create_time`, `update_time`, `last_login_time`, `last_login_ip`, `status`, `is_email_verified`, `is_phone_verified`, `member_level`) VALUES ('1223112890797523523', 'jane_doe', '123456', 'jane_doe@163.com', '13911113333', 'Jane Doe', '女', 'default.png', '2022-03-02 11:00:00', '2023-04-27 18:42:26', '2022-03-02 11:00:00', '192.168.1.2', '正常', 1, 1, 2);

-- 盐表
CREATE TABLE user_salt (
                           user_id CHAR(20) NOT NULL PRIMARY KEY,
                           salt VARCHAR(100) NOT NULL
);

INSERT INTO user_salt VALUES("2163652592439853323", "4wechxge23ex21");

-- 角色表
CREATE TABLE role (
                      id CHAR(20) NOT NULL PRIMARY KEY,
                      name VARCHAR(20)
);
-- 用户-角色关联表
CREATE TABLE user_role (
                           id CHAR(20) NOT NULL PRIMARY KEY,
                           user_id CHAR(20) NOT NULL PRIMARY KEY,
                           role_id CHAR(20) NOT NULL
);

SELECT t.id,t.password,t1.salt FROM user t left JOIN user_salt t1 ON (t1.user_id = t.id) WHERE (t.username = "Kiwi2333" OR t.email ="Kiwi2333"  OR t.phone ="Kiwi2333" )