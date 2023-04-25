package com.example.kiwi_community_mall_back;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.kiwi_community_mall_back.dto.user.UserRegisterDTO;
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
        UserSalt userSalt = userSaltMapper.selectOne(new QueryWrapper<UserSalt>().eq("user_id", "44002332322235232311"));
        System.out.println(userSalt);
    }

    @Test
    void getUserSalt2() {
        System.out.println(userSaltService.getUserSalt("alice_green"));
        System.out.println(userSaltService.getUserSalt("13415000000"));
        System.out.println(userSaltService.getUserSalt("Kiwi2333"));
    }

    @Test
    void toRegister() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("233333");
        userRegisterDTO.setPassword("123456");

        System.out.println(usersService.toRegister(userRegisterDTO));
    }


}
