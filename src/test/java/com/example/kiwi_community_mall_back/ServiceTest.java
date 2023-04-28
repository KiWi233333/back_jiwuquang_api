package com.example.kiwi_community_mall_back;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.kiwi_community_mall_back.dto.user.UserRegisterDTO;
import com.example.kiwi_community_mall_back.pojo.UserSalt;
import com.example.kiwi_community_mall_back.repository.UserMapper;
import com.example.kiwi_community_mall_back.repository.UserSaltMapper;
import com.example.kiwi_community_mall_back.service.MailService;
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
    @Autowired
    MailService mailService;


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
        userRegisterDTO.setPhone("13415000001");
        userRegisterDTO.setUsername("lulu233333");
        userRegisterDTO.setPassword("123456");
        userRegisterDTO.setCode("123456");
        userRegisterDTO.setType(0);
        System.out.println(usersService.toRegister(userRegisterDTO));
    }
    @Test
    void toRegister2() {
        System.out.println(usersService.getLoginCodeByPhone("13415000000"));
        System.out.println(usersService.getRegisterCodeByPhone("13415000001"));
        System.out.println(usersService.getLoginCodeByEmail("1329634@qq.com"));
        System.out.println(usersService.getRegisterCodeByEmail("1329634233@qq.com"));
    }


    @Test
    void sendEmail() {
        try {
            mailService.sendTextMail("2701398270@qq.com","验证码","123456");
//            mailService.sendCodeMail("1329634286@qq.com","验证码","123456");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
