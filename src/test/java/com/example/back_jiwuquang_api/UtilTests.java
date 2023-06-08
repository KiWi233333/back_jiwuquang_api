package com.example.back_jiwuquang_api;

import com.example.back_jiwuquang_api.enums.Gender;
import com.example.back_jiwuquang_api.pojo.sys.User;
import com.example.back_jiwuquang_api.repository.comm.CommCategoryMapper;
import com.example.back_jiwuquang_api.repository.goods.GoodsCategoryMapper;
import com.example.back_jiwuquang_api.util.BcryptPwdUtil;
import com.example.back_jiwuquang_api.util.CheckValidUtil;
import com.example.back_jiwuquang_api.util.FileUtil;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
    @Test
    void StringTest() {
        String a = "wew12e3.png";
        System.out.println(FileUtil.isImage(a));
    }




    @Autowired
    ResourceLoader resourceLoader;

    @Value("${emailTemplate}")
    private String emailTemplatePath;

    @Test
     void readConfig() throws IOException {
        Resource resource = resourceLoader.getResource(emailTemplatePath);
        System.out.println(resource);
    }

    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    CommCategoryMapper categoryMapperg;
@Test
    void selectALlCategoryTree() {
    System.out.println(goodsCategoryMapper.selectALlCategoryTree());
    System.out.println(categoryMapperg.selectALlCategoryTree());
    }

}
