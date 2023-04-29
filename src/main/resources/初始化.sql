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

INSERT INTO `user` VALUES ('1652246616668012545', 'Lulu2333', '$2a$10$DjJfKH8I5j7EGBdlA5d.CeX/DPjYMyb978hT7EZgv9pnDz3IAkcbe', NULL, '13415000001', '新用户', '保密', 'default.png', '2023-04-29 09:41:05', '2023-04-29 09:41:05', NULL, NULL, '正常', 0, 1, 1);
INSERT INTO `user` VALUES ('2163652592439853323', 'Kiwi2333', '$2a$10$s68J2cbazN3oL9Ag8tFO5.GtzVF5Ns26fgTqrgLC1hD2oxKuCP30y', '1329634286@qq.com', '13415000000', 'Kiwi2333', '男', 'default.png', '2022-03-01 10:00:00', '2023-04-27 18:40:54', '2022-03-01 10:00:00', '192.168.1.1', '正常', 1, 1, 1);


-- 盐表
CREATE TABLE user_salt (
                           user_id CHAR(20) NOT NULL PRIMARY KEY,
                           salt VARCHAR(100) NOT NULL
);
INSERT INTO `user_salt` VALUES ('1652246616668012545', 'LXgp/T13eSGkQg==');
INSERT INTO `user_salt` VALUES ('2163652592439853323', '4wechxge23ex21');

-- 角色表
CREATE TABLE role (
                      id CHAR(20) NOT NULL PRIMARY KEY,
                      type INT NOT NULL DEFAULT 0,
                      name VARCHAR(20) UNIQUE
);
INSERT INTO role VALUES("2163652592439223322",0,"0级会员");
INSERT INTO role VALUES("2163652592439813323",1,"1级会员");
INSERT INTO role VALUES("2163652592439823324",2,"2级会员");
INSERT INTO role VALUES("2163652592439853325",3,"3级会员");


-- 用户-角色关联表
CREATE TABLE user_role (
                           user_id CHAR(20) NOT NULL PRIMARY KEY,
                           role_id CHAR(20) NOT NULL
);
INSERT INTO user_role VALUES("1652246616668012545","2163652592439223322");
INSERT INTO user_role VALUES("2163652592439853323","2163652592439853325");

-- 管理员
-- 管理员表
CREATE TABLE admin (
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
                       status ENUM('正常', '禁用') NOT NULL DEFAULT '正常'
);

CREATE TABLE auth (
                      id CHAR(20) NOT NULL PRIMARY KEY,
                      type INT NOT NULL DEFAULT 0,
                      name VARCHAR(20) UNIQUE
);


-- 管理员-角色关联表
CREATE TABLE admin_auth (
                            admin_id CHAR(20) NOT NULL PRIMARY KEY,
                            auth_id CHAR(20) NOT NULL
);
