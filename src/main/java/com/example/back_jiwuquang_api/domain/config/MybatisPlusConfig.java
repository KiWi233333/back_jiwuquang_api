package com.example.back_jiwuquang_api.domain.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置类
 *
 * @className: MybatisPlusConfig
 * @author: Kiwi23333
 * @description: MybatisPlus配置类
 * @date: 2023/5/1 22:41
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();// 分页查询的拦截器
        paginationInnerInterceptor.setOptimizeJoin(true);// SQL 查询优化
        paginationInnerInterceptor.setDbType(DbType.MYSQL);// 数据库类型
        paginationInnerInterceptor.setOverflow(true);// 开启溢出总页数的支持
        interceptor.addInnerInterceptor(paginationInnerInterceptor);// 添加
        OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();//实现乐观锁的拦截器
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);// 添加
        return interceptor;
    }

}
