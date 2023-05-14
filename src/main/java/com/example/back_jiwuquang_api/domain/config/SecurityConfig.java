//package com.example.back_jiwuquang_api.domain.config;
//
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * Security拦截器
// *
// * @className: SecurityConfig
// * @author: Kiwi23333
// * @description: SecurityConfig
// * @date: 2023/5/2 20:27
// */
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 授权
//        http.authorizeRequests()
//                .antMatchers("/user/**");
//    }
//}