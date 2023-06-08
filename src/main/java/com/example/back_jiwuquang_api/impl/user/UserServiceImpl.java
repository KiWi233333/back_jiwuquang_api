//package com.example.back_jiwuquang_api.impl;
//
//import com.example.back_jiwuquang_api.dto.sys.UserTokenDTO;
//import com.example.back_jiwuquang_api.service.sys.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 描述
// *
// * @className: UserServiceImpl
// * @author: Kiwi23333
// * @description: TODO描述
// * @date: 2023/5/13 18:52
// */
//@Service
//public class UserServiceImpl implements UserDetailsService {
//
//    @Autowired
//    UserService userService;
//
//    /**
//     * 该方法的作用是查询数据库中用户为name的用户信息，将数据库查询到的用户名、用户密码和用户权限封装在UserDetails中。
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 1、通过自定义的方法获取用户及权限信息
//        UserTokenDTO userTokenDTO = userService.getUserRolePermissionByUname(username);
//        if (userTokenDTO == null) {
//            throw new UsernameNotFoundException("用户不存在！");
//        }
////        User user = UserTokenDTO.toSecurityUser(userTokenDTO);
//        // 2、
//        return new User(username,null, AuthorityUtils.createAuthorityList(userTokenDTO.getRoleCodeList()));
//    }
//}
