package com.example.kiwi_community_mall_back.service;

import com.example.kiwi_community_mall_back.repository.UserSaltMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录个人盐值
 *
 * @className: UsersSaltSercive
 * @author: Kiwi2333
 * @date: 2023/4/13 14:54
 */
@Service
public class UserSaltService {
    @Autowired
    UserSaltMapper userSaltMapper;

}
