package com.example.kiwi_community_mall_back.interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.kiwi_community_mall_back.constant.JwtConstant;
import com.example.kiwi_community_mall_back.util.JWTUtil;
import com.example.kiwi_community_mall_back.util.JacksonUtil;
import com.example.kiwi_community_mall_back.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("-----------------身份验证中------------------");
        // 1、获取token
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        response.setContentType("application/json;charset=UTF-8");
        // 2、获取token
        if (token != null ) {
            try {
                return JWTUtil.getTokenInfoByToken(token) != null;
            } catch (TokenExpiredException e1) {
                log.info("身份验证已过期"+e1.getMessage());
                response.getWriter().write(JacksonUtil.toJSON(Result.fail("身份验证已过期，请重新登陆！")));
                return false;
            }catch (IOException e){
                log.info("身份验证错误"+e.getMessage());
                response.getWriter().write(JacksonUtil.toJSON(Result.fail("身份验证失败！")));
                return false;
            }
        }
        // 3、身份错误
        response.getWriter().write(JacksonUtil.toJSON(Result.fail("token不能为空！")));
        return false; // Token验证失败，请求中止
    }
}