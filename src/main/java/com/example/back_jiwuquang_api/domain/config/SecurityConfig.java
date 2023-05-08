package com.example.back_jiwuquang_api.domain.config;


/**
 * Security拦截器
 *
 * @className: SecurityConfig
 * @author: Kiwi23333
 * @description: SecurityConfig
 * @date: 2023/5/2 20:27
 */
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()  // 验证请求
//                // 路径匹配，参数是要处理的 url
//                .antMatchers("/admin/**").hasRole("admin")  // 要具有某种权限
//                .antMatchers("/user/**").hasAnyRole("admin", "user")// 要具有某种权限中的一种
//                .anyRequest().authenticated();
//
//    }
//
//}