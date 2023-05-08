package com.example.back_jiwuquang_api.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 描述
 *
 * @className: FileOSSConfig
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/8 16:49
 */
@Component
@PropertySource(value = "classpath:application-qi-niu.properties")
public class FileOSSConfig {

    @Value("${qi-niu-cloud.access-key}")
    public  String accessKey;
    @Value("${qi-niu-cloud.secret-key}")
    public  String secretKey;
    @Value("${qi-niu-cloud.bucket-name}")
    public  String bucketName;
    @Value("${qi-niu-cloud.host-name}")
    public  String hostName;
}