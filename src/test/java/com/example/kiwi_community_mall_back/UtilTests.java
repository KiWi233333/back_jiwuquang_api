package com.example.kiwi_community_mall_back;

import com.example.kiwi_community_mall_back.enums.Gender;
import com.example.kiwi_community_mall_back.pojo.user.User;
import com.example.kiwi_community_mall_back.util.BcryptPwdUtil;
import com.example.kiwi_community_mall_back.util.CheckValidUtil;
import com.example.kiwi_community_mall_back.util.RedisUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
class UtilTests {

    @Test
    void JSONTest() {
        User user = new User();
        user.setUsername("23333");
        user.setGender(Gender.男);
        try {
            String json = new ObjectMapper().writeValueAsString(user);
            System.out.println(json);
            System.out.println(new JsonFactory().createParser(json).toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void tokenTest() {
//        UserTokenDTO userTokenDTO = new UserTokenDTO();
//        userTokenDTO.setId("2333333");
//        String token = JWTUtil.createToken(userTokenDTO);
//        String token2 = JWTUtil.createToken(userTokenDTO);
//        System.out.println(token);
//        System.out.println(token2);
//
//        // 验证
//        JsonParser obj = JWTUtil.checkToken(token);
//        System.out.println(JWTUtil.getTokenInfo(obj));
        // 续签
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyMTIzIiwiZXhwIjoxNjIwMDQ2NjUyLCJpYXQiOjE2MTk0MzA2NTJ9.8M3jJbZpE1OfJzD0m8q3DuYTBmO9X9ZzQJZ8WopHJ1I";

    }

    // 测试密码加密
    @Test
    void BcyTest() {
        String rand = BcryptPwdUtil.getRandomSalt();

        System.out.println(rand);
        System.out.println(BcryptPwdUtil.encodeBySalt("13131313131", rand));
        System.out.println("_________________________");
        System.out.println(
                BcryptPwdUtil.matches("1234567", "$2a$10$QwT5Eok/05MRIyQIQhrC5eLb6GZ8O631X20ewjnlSL0m7Jfb2k5IC", "H6qu2h8nPfPifQ==")
        );

    }

    @Test
    void getUserEncodedPwd() {
//        System.out.println(BcryptPwdUtil.encode("123456","4wechxge23ex21"));
        // 验证
        System.out.println(BcryptPwdUtil.matches("123456", "$2a$10$s68J2cbazN3oL9Ag8tFO5.GtzVF5Ns26fgTqrgLC1hD2oxKuCP30y", "4wechxge23ex21"));
    }

    @Test
    void checkValidUtil() {
        System.out.println(CheckValidUtil.checkEmail("13232sca@qq;,cox"));
        System.out.println(CheckValidUtil.checkEmail("13232@qq.com"));
        System.out.println(CheckValidUtil.checkEmail("13232@qqcom"));
        System.out.println(CheckValidUtil.checkPhone("134150389237223"));
        System.out.println(CheckValidUtil.checkPhone("13415237223"));
        System.out.println(CheckValidUtil.checkPhone("13415037223"));
    }

    @Autowired
    RedisUtil redisUtil;

    @Test
    void redisUtil() {
        redisUtil.set("23333", "23333");
    }
}
