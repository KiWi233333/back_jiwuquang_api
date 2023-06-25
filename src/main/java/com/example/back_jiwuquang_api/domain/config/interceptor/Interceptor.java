package com.example.back_jiwuquang_api.domain.config.interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.back_jiwuquang_api.domain.constant.JwtConstant;
import com.example.back_jiwuquang_api.dto.sys.UserTokenDTO;
import com.example.back_jiwuquang_api.domain.annotation.Auth;
import com.example.back_jiwuquang_api.util.JWTUtil;
import com.example.back_jiwuquang_api.util.JacksonUtil;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.back_jiwuquang_api.domain.constant.JwtConstant.REDIS_TOKEN_TIME;
import static com.example.back_jiwuquang_api.domain.constant.UserConstant.USER_REFRESH_TOKEN_KEY;

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
@CrossOrigin
public class Interceptor implements HandlerInterceptor {

    @Autowired
    RedisUtil redisUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        // 检测是否有用户身份注解
//        Method method = ((HandlerMethod) handler).getMethod();
//
//        if (!method.isAnnotationPresent(Auth.class)){
//            return true;
//        }
        log.info("-----------------身份验证中------------------");
        // 1、获取token
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        response.setContentType("application/json;charset=UTF-8");

        // 2、获取token
        UserTokenDTO userTokenDTO;
        // 1) token 为空
        if (!StringUtils.isNotBlank(token)) {
            response.getWriter().write(JacksonUtil.toJSON(Result.fail("验证错误，您还未登录！")));
            response.sendError(401, "token不能为空!");
            return false; // Token验证失败，请求中止
        }

        // 2）token 不为空
        try {
            // 1、验证用户
            // 获取对应userAgent
            String userAgent = request.getHeader("User-Agent");
            userTokenDTO = JWTUtil.getTokenInfoByToken(token);
            //  请求拦截权限设定 (无权限拦截)
            if (!checkPermission(userTokenDTO, request)) {
                response.getWriter().write(JacksonUtil.toJSON(Result.fail(Result.TOKEN_ERR, "暂无权限，无法访问！")));
                log.info("暂无权限，无法访问！{}", userTokenDTO);
                return false;
            }

            //  1)redis获取当前token是否有效
            Map<String, Object> map = redisUtil.hGetAll(USER_REFRESH_TOKEN_KEY + userTokenDTO.getId());
            if (map.isEmpty()) {
                redisUtil.hPut(USER_REFRESH_TOKEN_KEY + userTokenDTO.getId(), userAgent, token);
            }
            //  2）验证登录设备是否存在
            boolean flag = false;
            for (String ua : map.keySet()) {
                if (map.get(ua).equals(token) && ua.equals(userAgent)) {
                    flag = true;
                }
            }
            if (!flag) {
                response.getWriter().write(JacksonUtil.toJSON(Result.fail(Result.TOKEN_ERR,"身份验证错误，登录设备有误！")));
                log.info("身份验证错误，登录设备有误！");
                return false;
            }
            //  3）验证是否过期
            if (redisUtil.hGet(USER_REFRESH_TOKEN_KEY + userTokenDTO.getId(), userAgent) == null) {
                response.getWriter().write(JacksonUtil.toJSON(Result.fail(Result.TOKEN_ERR,"登录已过期，请重新登陆！")));
                log.info("身份已全过期！");
                return false;
            }
            // 2、redis_token续期
            redisUtil.expire(USER_REFRESH_TOKEN_KEY + userTokenDTO.getId(), REDIS_TOKEN_TIME, TimeUnit.MINUTES);
            // 将用户id放入头部 用于业务使用
            request.setAttribute("userId", userTokenDTO.getId());
            return true;
        } catch (TokenExpiredException e1) {
            log.info("身份已过期 {}", e1.getMessage());
            response.getWriter().write(JacksonUtil.toJSON(Result.fail(Result.TOKEN_ERR,"登录已过期，请重新登陆！")));
            return false;
        } catch (IOException e) {
            // 3、登录错误
            log.info("身份错误 {}", e.getMessage());
            response.getWriter().write(JacksonUtil.toJSON(Result.fail(Result.TOKEN_ERR,"身份验证失败！")));
            return false;
        }
    }


    /**
     * 验证身份
     *
     * @param dto UserTokenDTO
     * @return boolean
     */
    private boolean checkPermission(UserTokenDTO dto, HttpServletRequest request) {
        String url = request.getServletPath();
        log.info("id: {} 请求路径url：{}", dto.getId(), url);
        for (String role : dto.getRoleCodeList()) {
            if (role.equals("ROLE_CUSTOMER") && !url.startsWith("/admin") && !url.startsWith("/service")) {
                return true;
            }
            if (role.equals("ROLE_ADMIN") && url.startsWith("/admin")) {
                return true;
            }
            if (role.equals("ROLE_SERVICE") && url.startsWith("/service")) {
                return true;
            }
        }
        return false;
    }

}