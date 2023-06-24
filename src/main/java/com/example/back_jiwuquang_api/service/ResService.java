package com.example.back_jiwuquang_api.service;

import com.example.back_jiwuquang_api.util.FileOSSUpDownUtil;
import com.example.back_jiwuquang_api.util.FileUtil;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * 收货地址业务层
 *
 * @className: UserWalletService
 * @author: Kiwi23333
 * @description: 收货地址的增删查改
 * @date: 2023/4/30 15:49
 */
@Service
@Slf4j
public class ResService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    FileOSSUpDownUtil fileOSSUpDownUtil;

    public Result uploadImage(MultipartFile file) {
        // 图片格式
        if (!FileUtil.isImage(file.getOriginalFilename())) return Result.fail("图片格式错误！");
        // 上传
        String imgKey = fileOSSUpDownUtil.uploadImage(file);
        if (StringUtil.isNullOrEmpty(imgKey)) return Result.fail("图片上传失败！");
        // 清除缓存
        return Result.ok("上传图片成功，10分钟有效期！", null);
    }
}
