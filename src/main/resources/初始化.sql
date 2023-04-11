DROP DATABASE IF EXISTS kiwi_community_mall;

CREATE DATABASE kiwi_community_mall;

USE kiwi_community_mall;

CREATE TABLE
    users (
        id CHAR(40) NOT NULL PRIMARY KEY,
        username VARCHAR(50) NOT NULL,
        password VARCHAR(80) NOT NULL,
        email VARCHAR(50),
        phone VARCHAR(20) NOT NULL,
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

INSERT INTO
    users (
        id,
        username,
        password,
        email,
        phone,
        nickname,
        gender,
        avatar,
        create_time,
        update_time,
        last_login_time,
        last_login_ip,
        status,
        is_email_verified,
        is_phone_verified,
        member_level
    )
VALUES (
        '1e1c6a7f-3d3d-4d5f-bc64-12d47e2d15f1',
        'john_doe',
        '123456',
        'john_doe@qq.com',
        '13811112222',
        'John Doe',
        '男',
        'https://avatars.githubusercontent.com/u/18883643?v=4',
        '2022-03-01 10:00:00',
        '2022-03-01 10:00:00',
        '2022-03-01 10:00:00',
        '192.168.1.1',
        '正常',
        TRUE,
        TRUE,
        1
    ), (
        '2e6bdf0f-3cf6-4d6f-9a86-1e0b9d3be3c3',
        'jane_doe',
        '123456',
        'jane_doe@163.com',
        '13911113333',
        'Jane Doe',
        '女',
        'https://avatars.githubusercontent.com/u/18883643?v=4',
        '2022-03-02 11:00:00',
        '2022-03-02 11:00:00',
        '2022-03-02 11:00:00',
        '192.168.1.2',
        '正常',
        TRUE,
        TRUE,
        2
    ), (
        '3c39f116-7f10-4d70-8e90-cb5d6f5d6f5a',
        'bob_smith',
        '123456',
        'bob_smith@qq.com',
        '13811114444',
        'Bob Smith',
        '男',
        'https://avatars.githubusercontent.com/u/18883643?v=4',
        '2022-03-03 12:00:00',
        '2022-03-03 12:00:00',
        '2022-03-03 12:00:00',
        '192.168.1.3',
        '正常',
        TRUE,
        TRUE,
        3
    ), (
        '4dde2f2b-22f9-4a9b-9c60-1dc42c04d2b2',
        'alice_green',
        '123456',
        'alice_green@qq.com',
        '13911115555',
        'Alice Green',
        '女',
        'https://avatars.githubusercontent.com/u/18883643?v=4',
        '2022-03-04 13:00:00',
        '2022-03-04 13:00:00',
        '2022-03-04 13:00:00',
        '192.168.1.4',
        '正常',
        TRUE,
        TRUE,
        1
    ), (
        '5fda4d72-81f1-4f76-8aca-a3a3a3f3d8f1',
        'tom_jones',
        '123456',
        'tom_jones@163.com',
        '13811116666',
        'Tom Jones',
        '男',
        'https://avatars.githubusercontent.com/u/18883643?v=4',
        '2022-03-05 14:00:00',
        '2022-03-05 14:00:00',
        '2022-03-05 14:00:00',
        '192.168.1.5',
        '禁用',
        FALSE,
        TRUE,
        1
    );

SELECT *
FROM users
WHERE
    id = "1e1c6a7f-3d3d-4d5f-bc64-12d47e2d15f1";