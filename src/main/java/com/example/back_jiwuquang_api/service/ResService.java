package com.example.back_jiwuquang_api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.back_jiwuquang_api.dto.sys.UserAddressDTO;
import com.example.back_jiwuquang_api.pojo.sys.User;
import com.example.back_jiwuquang_api.pojo.sys.UserAddress;
import com.example.back_jiwuquang_api.repository.sys.UserAddressMapper;
import com.example.back_jiwuquang_api.util.FileOSSUpDownUtil;
import com.example.back_jiwuquang_api.util.FileUtil;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import com.example.back_jiwuquang_api.vo.UserVO;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.back_jiwuquang_api.domain.constant.UserConstant.USER_ADDRESS_PAGE_KEY;
import static com.example.back_jiwuquang_api.domain.constant.UserConstant.USER_KEY;

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
        String imgKey = fileOSSUpDownUtil.uploadImages(file);
        if (StringUtil.isNullOrEmpty(imgKey)) return Result.fail("图片上传失败！");
        // 清除缓存
        return Result.ok("上传图片成功，10分钟有效期！", null);
    }
}
