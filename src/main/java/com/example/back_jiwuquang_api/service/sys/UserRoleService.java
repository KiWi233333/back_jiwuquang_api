package com.example.back_jiwuquang_api.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.back_jiwuquang_api.dto.sys.UserCheckDTO;
import com.example.back_jiwuquang_api.pojo.sys.Role;
import com.example.back_jiwuquang_api.pojo.sys.UserRole;
import com.example.back_jiwuquang_api.pojo.sys.UserSalt;
import com.example.back_jiwuquang_api.repository.sys.RoleMapper;
import com.example.back_jiwuquang_api.repository.sys.UserMapper;
import com.example.back_jiwuquang_api.repository.sys.UserRoleMapper;
import com.example.back_jiwuquang_api.repository.sys.UserSaltMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.back_jiwuquang_api.domain.constant.UserConstant.USER_SALT_DTO_KEY;


/**
 * 用户角色表
 *
 * @className: UserSaltService
 * @author: Kiwi2333
 * @date: 2023/4/13 14:54
 */
@Service
public class UserRoleService {
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 添加用户用户角色关联
     *
     * @param useId
     * @return int
     */
    public int addUserRoleCustomer(String useId) {
        Role role = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getName,"普通用户").eq(Role::getType,0).eq(Role::getCode, "ROLE_CUSTOMER").last("LIMIT 1"));
        if (role == null) return 0;
        UserRole userRole = new UserRole();
        userRole.setUserId(useId).setRoleId(role.getId());
        return userRoleMapper.insert(userRole);
    }
}
