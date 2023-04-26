DROP DATABASE IF EXISTS  kiwi_community_mall;
CREATE DATABASE kiwi_community_mall;
USE kiwi_community_mall;

CREATE TABLE user (
                      id CHAR(20) NOT NULL PRIMARY KEY,
                      username VARCHAR(50) UNIQUE NOT NULL ,
                      password VARCHAR(100) NOT NULL,
                      email VARCHAR(50) UNIQUE,
                      phone VARCHAR(15) UNIQUE NOT NULL,
                      nickname VARCHAR(50) NOT NULL,
                      gender ENUM('男', '女', '保密') DEFAULT '保密',
                      avatar VARCHAR(128) DEFAULT 'default.png',
                      create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      last_login_time DATETIME DEFAULT NULL,
                      last_login_ip VARCHAR(50) DEFAULT NULL,
                      status ENUM('正常', '禁用') NOT NULL DEFAULT '正常',
                      is_email_verified BOOLEAN NOT NULL DEFAULT FALSE,
                      is_phone_verified BOOLEAN NOT NULL DEFAULT FALSE,
                      member_level INT NOT NULL DEFAULT 1
);
INSERT INTO user (id, username, password, email, phone, nickname, gender, avatar, create_time, update_time, last_login_time, last_login_ip, status, is_email_verified, is_phone_verified, member_level) VALUES
('44002332322235232311', 'Kiwi2333', '$2a$10$s68J2cbazN3oL9Ag8tFO5.GtzVF5Ns26fgTqrgLC1hD2oxKuCP30y', '1329634@qq.com', '13415000000', 'Kiwi2333', '男', 'https://avatars.githubusercontent.com/u/18883643?v=4', '2022-03-01 10:00:00', '2022-03-01 10:00:00', '2022-03-01 10:00:00', '192.168.1.1', '正常', TRUE, TRUE, 1),
('44002332323135232322', 'jane_doe', '123456', 'jane_doe@163.com', '13911113333', 'Jane Doe', '女', 'https://avatars.githubusercontent.com/u/18883643?v=4', '2022-03-02 11:00:00', '2022-03-02 11:00:00', '2022-03-02 11:00:00', '192.168.1.2', '正常', TRUE, TRUE, 2),
('44002332322335232314', 'bob_smith', '123456', 'bob_smith@qq.com', '13811114444', 'Bob Smith', '男', 'https://avatars.githubusercontent.com/u/18883643?v=4', '2022-03-03 12:00:00', '2022-03-03 12:00:00', '2022-03-03 12:00:00', '192.168.1.3', '正常', TRUE, TRUE, 3),
('44002332328635732321', 'alice_green', '123456', 'alice_green@qq.com', '13911115555', 'Alice Green', '女', 'https://avatars.githubusercontent.com/u/18883643?v=4', '2022-03-04 13:00:00', '2022-03-04 13:00:00', '2022-03-04 13:00:00', '192.168.1.4', '正常', TRUE, TRUE, 1),
('44002332321239932321', 'tom_jones', '123456', 'tom_jones@163.com', '13811116666', 'Tom Jones', '男', 'https://avatars.githubusercontent.com/u/18883643?v=4', '2022-03-05 14:00:00', '2022-03-05 14:00:00', '2022-03-05 14:00:00', '192.168.1.5', '禁用', FALSE, TRUE, 1);

-- 盐表
CREATE TABLE user_salt (
                           user_id CHAR(20) NOT NULL PRIMARY KEY,
                           salt VARCHAR(100) NOT NULL
);

INSERT INTO user_salt VALUES("44002332322235232311", "4wechxge23ex21");

