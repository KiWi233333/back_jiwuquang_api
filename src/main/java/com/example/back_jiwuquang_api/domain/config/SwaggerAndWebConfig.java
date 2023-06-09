package com.example.back_jiwuquang_api.domain.config;

import com.example.back_jiwuquang_api.domain.config.interceptor.Interceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


@Slf4j
@Configuration
@EnableWebMvc // 解决spring>2.6.0版本 适配Swagger
@EnableSwagger2 // 开启Swagger
public class SwaggerAndWebConfig implements WebMvcConfigurer { // 覆写addResourceHandlers跨域

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }


    // 配置Swagger bean实例
    @Bean
    public Docket SwaggerDocket(Environment environment) {
        // 获取当前生产环境
        Profiles profiles = Profiles.of("dev", "pro");
        boolean isDev = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .enable(isDev)//  是否启用Swagger :默认开启
                .groupName("Kiwi2333组") // 分组（多人协作）
                // 配置扫描的接口 Select
                .select()
                // 扫描包中的注解
                .apis(RequestHandlerSelectors.basePackage("com.example.back_jiwuquang_api.controller"))
                // .paths() // 过滤
                .build();
    }

    // 配置Swagger apiInfo信息
    private ApiInfo apiInfo() {
        return new ApiInfo("Kiwi社区商城",
                "各模块的api文档~",
                "1.0",
                "https://www.kiwi2333.top",
                new Contact("Kiwi2333个人网站", "http://fun.kiwi2333.top", "1329634286@qq.com"), // 作者信息
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
    }

    // 跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有接口
                .allowCredentials(true) // 是否发送 Cookie
                .allowedOriginPatterns("*") // 支持域
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 支持方法
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    // 拦截器
    @Autowired
    Interceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor) // 注册拦截器
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns(
                        // 公共
                        "/res/**",// 公共资源
                        // 前台
                        "/user/login/**",// 登录
                        "/user/register/**",// 注册
                        "/user/exist/**",// 用户查询
                        "/user/wallet/combo",// 充值套餐
                        "/goods/category/list/**",// 分类
                        "/goods/category/one/**",// 分类单个
                        "/goods/list/**", // 商品分页
                        "/goods/item/**", // 商品详情
                        "/goods/comments/**", // 商品评论列表
                        "/goods/sku/**",
                        "/community/post/list/**", // 社区帖子
                        "/community/user", // 社区用户
                        "/community/category/list/**", // 社区分类
                        "/event/**", // 前台活动
                        // 后台
                        "/admin/login/**"// 管理员登录
                );
        log.info("登录拦截器注册！");
    }
}
