package com.example.kiwi_community_mall_back;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.kiwi_community_mall_back.pojo.UserSalt;
import com.example.kiwi_community_mall_back.repository.UserMapper;
import com.example.kiwi_community_mall_back.repository.UserSaltMapper;
import com.example.kiwi_community_mall_back.service.UserSaltService;
import com.example.kiwi_community_mall_back.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Slf4j
class ServiceTest {
    @Autowired
    UserSaltMapper userSaltMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService usersService;
    @Autowired
    UserSaltService userSaltService;
    @Autowired
    RedisTemplate redisTemplate;


    @Test
    void getUserSalt() {
        UserSalt userSalt = userSaltMapper.selectOne(new QueryWrapper<UserSalt>().eq("users_id","44002332322235232311"));
        System.out.println(userSalt);
    }
    @Test
    void getUserSalt2() {
        System.out.println(usersService.getUserSalt("alice_green"));
        System.out.println(usersService.getUserSalt("13415000000"));
//        System.out.println(usersService.getUserSalt("Kiwi2333"));
//        System.out.println(redisTemplate.opsForValue().get("user_check::Kiwi2333"));// 工具盐判断
    }






}
