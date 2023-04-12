package com.example.kiwi_community_mall_back;

import com.example.kiwi_community_mall_back.dto.UserTokenDto;
import com.example.kiwi_community_mall_back.enums.Gender;
import com.example.kiwi_community_mall_back.pojo.Users;
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
        Users users = new Users();
        users.setUsername("23333");
        users.setGender(Gender.BOY);
        try {
            String json = new ObjectMapper().writeValueAsString(users);
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
        UserTokenDto userTokenDto = new UserTokenDto();
        userTokenDto.setId("2333333");
        userTokenDto.setUsername("Kiwi2333");
        userTokenDto.setEmail("132564@qq.com");
        String token = JWTUtil.createToken(userTokenDto);
        System.out.println(token);

        // 验证
        try {
            JsonParser obj = JWTUtil.parseToken(token);
            System.out.println(JWTUtil.getTokenInfo(obj));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
