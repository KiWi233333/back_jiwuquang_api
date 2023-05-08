package com.example.back_jiwuquang_api.domain.interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.back_jiwuquang_api.domain.constant.JwtConstant;
import com.example.back_jiwuquang_api.domain.constant.UserConstant;
import com.example.back_jiwuquang_api.dto.sys.UserTokenDTO;
import com.example.back_jiwuquang_api.util.JWTUtil;
import com.example.back_jiwuquang_api.util.JacksonUtil;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.example.back_jiwuquang_api.domain.constant.JwtConstant.REDIS_TOKEN_TIME;

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
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("-----------------身份验证中------------------");
        // 1、获取token
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        response.setContentType("application/json;charset=UTF-8");
        // 2、获取token
        UserTokenDTO userTokenDTO;
        if (StringUtils.isNotBlank(token)) {// token不为空
            try {
                // 1、验证用户
                userTokenDTO = JWTUtil.getTokenInfoByToken(token);
                // redis过期
                if (userTokenDTO==null) log.info("身份已过期 长时间未操作 !");
                // 2、redis_token续期
                redisUtil.expire(UserConstant.USER_REFRESH_TOKEN_KEY +token, REDIS_TOKEN_TIME, TimeUnit.MINUTES);
                // 将用户id放入头部 用于业务使用
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