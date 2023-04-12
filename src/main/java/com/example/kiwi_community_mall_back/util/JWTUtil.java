package com.example.kiwi_community_mall_back.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.kiwi_community_mall_back.dto.UserTokenDto;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 *
 * @className: JWTUtil
 * @author: Author作者
 * @description: TODO描述
 * @date: 2023/4/13 1:17
 */
@Slf4j
public class JWTUtil {

    static ObjectMapper objectMapper  = new ObjectMapper();
    // 私钥
    private static final String TOKEN_SECRET = "@KIWI2333.132xx96we34Wgh286";
    // 有效期
    private static final Long TOKEN_LONG = 1000L * 60 *2 ;

    /**
     * 1、生成Token
     * @param userTokenDto
     * @return token
     */
    public static String createToken(UserTokenDto userTokenDto) {
        try {
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");

            String userJson = objectMapper.writeValueAsString(userTokenDto);
            return JWT.create()
                    .withHeader(header)
                    .withClaim("user", userJson) // 存入user信息
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("生成token失败！\ncreate token occur error, error is:{}", e);
            return null;
        }
    }

    /**
     * 检验token是否正确
     * @param token
     * @return JsonParser
     */
    public static JsonParser parseToken(String token) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        String tokenInfo = jwt.getClaim("user").asString();// 存入user
        // System.out.println(tokenInfo); // 用户部分信息
        return new JsonFactory().createParser(tokenInfo);
    }

    /**
     * 解析token的信息
     * @param jsonParser
     * @return UserTokenDto
     */
    public static UserTokenDto getTokenInfo(JsonParser jsonParser)  {
        try {
            return objectMapper.readValue(jsonParser,UserTokenDto.class);
        } catch (IOException e) {
            return null;
        }
    }
}
