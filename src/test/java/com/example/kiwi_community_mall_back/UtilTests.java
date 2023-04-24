package com.example.kiwi_community_mall_back;

import com.example.kiwi_community_mall_back.dto.user.UserTokenDTO;
import com.example.kiwi_community_mall_back.enums.Gender;
import com.example.kiwi_community_mall_back.pojo.User;
import com.example.kiwi_community_mall_back.util.BcryptPwdUtil;
import com.example.kiwi_community_mall_back.util.JWTUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setId("2333333");
        String token = JWTUtil.createToken(userTokenDTO);
        System.out.println(token);

        // 验证
        try {
            JsonParser obj = JWTUtil.parseToken(token);
            System.out.println(JWTUtil.getTokenInfo(obj));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void getUserEncodedPwd() {
//        System.out.println(BcryptPwdUtil.encode("123456","4wechxge23ex21"));
        // 验证
        System.out.println(BcryptPwdUtil.matches("123456","$2a$10$s68J2cbazN3oL9Ag8tFO5.GtzVF5Ns26fgTqrgLC1hD2oxKuCP30y","4wechxge23ex21"));

    }


}
