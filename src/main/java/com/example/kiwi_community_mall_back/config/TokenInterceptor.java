package com.example.kiwi_community_mall_back.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.kiwi_community_mall_back.constant.JwtConstant;
import com.example.kiwi_community_mall_back.dto.user.UserTokenDTO;
import com.example.kiwi_community_mall_back.util.JWTUtil;
import com.example.kiwi_community_mall_back.util.JacksonUtil;
import com.example.kiwi_community_mall_back.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import static com.example.kiwi_community_mall_back.constant.JwtConstant.TOKEN_TIME;
import static com.example.kiwi_community_mall_back.constant.UserConstant.USER_REFRESH_TOKEN_KEY;

/**
 * 身份验证拦截器
 *
 * @className: TokenInterceptor
 * @author: Kiwi23333
 * @description: token验证拦截器
 * @date: 2023/4/29 1:47
 */
@Slf4j
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("-----------------身份验证中------------------");
        // 1、获取token
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        response.setContentType("application/json;charset=UTF-8");
        // 2、获取token
        UserTokenDTO userTokenDTO;
        if (StringUtils.isNotBlank(token)) {
            try {
                userTokenDTO = JWTUtil.getTokenInfoByToken(token);
                // 查看有效期
                Long seconds = redisTemplate.getExpire(USER_REFRESH_TOKEN_KEY + userTokenDTO.getId());
                if (seconds <= 0) { // token失效
                    log.info("身份已过期 {}", seconds);
                    response.getWriter().write(JacksonUtil.toJSON(Result.fail("身份已过期，请重新登陆！")));
                    return false;
                } else if (seconds > 0 && seconds <= TOKEN_TIME*60) {
                    // 续签 小于 TOKEN_TIME 30分钟
                    log.info("续签 剩余{}s", seconds);
                    redisTemplate.expireAt(USER_REFRESH_TOKEN_KEY + userTokenDTO.getId(), new Date(System.currentTimeMillis() + (seconds + TOKEN_TIME * 60) * 1000));
                }
                // 将用户id放入
                request.setAttribute("userId", userTokenDTO.getId());
                return true;
            } catch (TokenExpiredException e1) {
                log.info("身份已过期 {}", e1.getMessage());
                response.getWriter().write(JacksonUtil.toJSON(Result.fail("身份已过期，请重新登陆！")));
                return false;
            } catch (IOException e) {
                // 3、身份错误
                log.info("身份错误 {}", e.getMessage());
                response.getWriter().write(JacksonUtil.toJSON(Result.fail("身份验证失败！")));
                return false;
            }
        }else {
            response.sendError(400, "token不能为空!");
            return false; // Token验证失败，请求中止
        }
    }
}