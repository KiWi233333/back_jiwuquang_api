package com.example.back_jiwuquang_api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.back_jiwuquang_api.dto.sys.UserRegisterDTO;
import com.example.back_jiwuquang_api.pojo.sys.UserSalt;
import com.example.back_jiwuquang_api.repository.event.EventMapper;
import com.example.back_jiwuquang_api.repository.pay.UserWalletMapper;
import com.example.back_jiwuquang_api.repository.shopcart.ShopCartMapper;
import com.example.back_jiwuquang_api.repository.sys.UserMapper;
import com.example.back_jiwuquang_api.repository.sys.UserSaltMapper;
import com.example.back_jiwuquang_api.service.goods.GoodsCategoryService;
import com.example.back_jiwuquang_api.service.orders.OrdersCommentService;
import com.example.back_jiwuquang_api.service.orders.OrdersService;
import com.example.back_jiwuquang_api.service.other.MailService;
import com.example.back_jiwuquang_api.service.sys.UserSaltService;
import com.example.back_jiwuquang_api.service.sys.UserService;
import com.example.back_jiwuquang_api.service.sys.UserWalletService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;

@SpringBootTest
@Slf4j
class OrdersServiceTest {
    @Autowired
    UserSaltMapper userSaltMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService usersService;
    @Autowired
    UserSaltService userSaltService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MailService mailService;
    @Autowired
    OrdersCommentService orderComment;

    @Test
    void getOrdersComment() {
    }

}
