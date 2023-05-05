package com.example.back_jiwuquang_api.repository.sys;

import com.example.back_jiwuquang_api.dto.sys.*;
import com.example.back_jiwuquang_api.pojo.sys.*;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends MPJBaseMapper<User> {

    // 查询链表查询用户的 id、盐、密码
    default UserCheckDTO selectUserRJoinSaltByUsername(String username, Integer userType) {
        MPJLambdaWrapper<User> qw = new MPJLambdaWrapper<>();
        qw.select(User::getId, User::getPassword) // 用户表
                .select(UserSalt::getSalt)// 盐表
                .eq(User::getUserType, userType).and(q -> q.eq(User::getUsername, username).or().eq(User::getEmail, username).or().eq(User::getPhone, username))

                .rightJoin(UserSalt.class, UserSalt::getUserId, User::getId); // 右表
        // 返回该用户对应的盐值
        return this.selectJoinOne(UserCheckDTO.class, qw);
    }

    // 查询用户的角色、权限
    @Select("SELECT " +
//            " ur.role_id AS roleId," +
//            " r.name AS roleName," +
            " r.code AS roleCode," + " r.type AS roleType," +
//            " p.id AS permissionId," +
//            " p.name AS permissionName," +
            " p.code AS permissionCode," + " p.type AS permissionType," + " p.url AS permissionUrl" + " FROM" + " sys_user_role ur" + " LEFT JOIN sys_role r ON ur.role_id = r.id" + " LEFT JOIN sys_role_permission rp ON r.id = rp.role_id" + " LEFT JOIN sys_permission p ON rp.permission_id = p.id" + " WHERE" + " ur.user_id = #{userId}")
    List<UserRolePermissionDTO> selectRoleAndPermissionByUserId(String userId);

    // 查询并排序好对应的角色、权限数组
    default UserTokenDTO selectUserRoleAndPermissionByUserId(String userId) {
        List<UserRolePermissionDTO> list = selectRoleAndPermissionByUserId(userId);
        return new UserTokenDTO(userId, list.stream().map(UserRolePermissionDTO::getRoleCode).distinct().toArray(String[]::new), list.stream().map(UserRolePermissionDTO::getRoleType).distinct().toArray(Integer[]::new), list.stream().map(p -> p.getPermissionUrl() + "|" + p.getPermissionType()).distinct().toArray(String[]::new), list.stream().map(UserRolePermissionDTO::getPermissionType).distinct().toArray(Integer[]::new));
    }

    /**
     * 获取用户的角色
     *
     * @param userId 用户id
     * @return UserRolePermissionDTO
     */
    default UserRolePermissionDTO selectUserRoleById(String userId) {
        return this.selectJoinOne(UserRolePermissionDTO.class, new MPJLambdaWrapper<User>().select(User::getId).select(Role::getName, Role::getType).eq(User::getId, userId).leftJoin(UserRole.class, UserRole::getUserId, User::getId).leftJoin(Role.class, Role::getId, UserRole::getRoleId));
    }

    /**
     * 获取所有的角色
     *
     * @return List<RoleDTO>
     */
    @Select("SELECT ur.role_id AS id, r.name, r.code, r.type FROM sys_user_role ur LEFT JOIN sys_role r ON ur.role_id = r.id")
    List<RoleDTO> selectAllRole();

    /**
     * 获取所有权限
     *
     * @return
     */
    @Select("SELECT p.id , p.name , p.code, p.type, p.url FROM sys_role_permission rp LEFT JOIN sys_permission p ON rp.permission_id = p.id")
    List<PermissionDTO> selectAllPermission();
}
