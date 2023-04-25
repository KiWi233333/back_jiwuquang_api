package com.example.kiwi_community_mall_back.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.kiwi_community_mall_back.dto.user.UserTokenDTO;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 描述
 *
 * @className: JWTUtil
 * @author: Author作者
 * @description: TODO描述
 * @date: 2023/4/13 1:17
 */
@Slf4j
@Component
public class JWTUtil {

    static ObjectMapper objectMapper = new ObjectMapper();
    // 私钥
    private static final String TOKEN_SECRET = "KIWsdaigz2xc2vw";
    // 有效期
    private static final Long TOKEN_DATE = 1000L * 60 * 60;// 1h

    /**
     * 1、生成Token
     *
     * @param userTokenDto
     * @return token
     */
    public static String createToken(UserTokenDTO userTokenDto) {
        try {
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("alg", "HS256");
            header.put("Type", "Jwt");

            String userJson = objectMapper.writeValueAsString(userTokenDto);
            return JWT.create()
                    .withHeader(header)
                    .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_DATE))// 设置过期时间
                    .withJWTId(UUID.randomUUID().toString()) // 说明标明JWT的唯一ID
                    .withClaim("user", userJson) // 存入user信息
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e) {
            log.error("生成token失败！\ncreate token occur error, error is:{}", e);
            return null;
        }
    }

    /**
     * 检验token是否正确
     *
     * @param token
     * @return JsonParser
     */
    public static JsonParser parseToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptExpiresAt(60)// 单位秒: 可以接受过期的时间长度
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            String tokenInfo = jwt.getClaim("user").asString();// 存入user
            return new JsonFactory().createParser(tokenInfo);
        } catch (JsonParseException e) {
            log.error(e.getMessage());
            return null;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }


    /**
     * 解析token的信息
     *
     * @param jsonParser
     * @return UserTokenDTO
     */
    public static UserTokenDTO getTokenInfo(JsonParser jsonParser) {
        try {
            return objectMapper.readValue(jsonParser, UserTokenDTO.class);
        } catch (IOException e) {
            return null;
        }
    }
}
