package com.example.kiwi_community_mall_back.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.kiwi_community_mall_back.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@Slf4j
//public class JwtFilterConfig implements HandlerInterceptor  {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        String token = request.getHeader("token");
//        if(StringUtils.isEmpty(token)){
//            throw new ExceptionInInitializerError("token不能为空");
//        }
//        try {
//            JWTUtil.checkToken(token);
//        } catch (SignatureVerificationException e) {
//            log.error("无效签名！ 错误 ->", e);
//            return false;
//        } catch (TokenExpiredException e) {
//            log.error("token过期！ 错误 ->", e);
//            return false;
//        } catch (AlgorithmMismatchException e) {
//            log.error("token算法不一致！ 错误 ->", e);
//            return false;
//        } catch (Exception e) {
//            log.error("token无效！ 错误 ->", e);
//            return false;
//        }
//        return true;
//    }
//}
