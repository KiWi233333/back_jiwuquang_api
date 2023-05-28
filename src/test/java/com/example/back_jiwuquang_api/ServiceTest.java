package com.example.back_jiwuquang_api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.back_jiwuquang_api.dto.sys.UserRegisterDTO;
import com.example.back_jiwuquang_api.pojo.sys.UserSalt;
import com.example.back_jiwuquang_api.repository.event.EventMapper;
import com.example.back_jiwuquang_api.repository.shopcart.ShopCartMapper;
import com.example.back_jiwuquang_api.repository.sys.UserMapper;
import com.example.back_jiwuquang_api.repository.sys.UserSaltMapper;
import com.example.back_jiwuquang_api.repository.pay.UserWalletMapper;
import com.example.back_jiwuquang_api.service.goods.GoodsCategoryService;
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
class ServiceTest {
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


    @Test
    void getUserSalt() {
        UserSalt userSalt = userSaltMapper.selectOne(new QueryWrapper<UserSalt>().eq("user_id", "44002332322235232311"));
        System.out.println(userSalt);
    }

    @Test
    void getUserSalt2() {
//        System.out.println(userSaltService.getUserSalt("alice_green"));
//        System.out.println(userSaltService.getUserSalt("13415000000"));
//        System.out.println(userSaltService.getUserSalt("Kiwi2333"));
    }

    @Test
    void toRegister() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setPhone("13415000001");
        userRegisterDTO.setUsername("lulu233333");
        userRegisterDTO.setPassword("123456");
        userRegisterDTO.setCode("123456");
        userRegisterDTO.setType(0);
        System.out.println(usersService.toRegister(userRegisterDTO));
    }
//    @Test
//    void toRegister2() {
//        System.out.println(usersService.getLoginCodeByPhone("13415000000"));
//        System.out.println(usersService.getRegisterCodeByPhone("13415000001"));
//        System.out.println(usersService.getLoginCodeByEmail("1329634@qq.com"));
//        System.out.println(usersService.getRegisterCodeByEmail("1329634233@qq.com"));
//    }


    @Test
    void sendEmail() {
        try {
//            mailService.sendTextMail("2701398270@qq.com","验证码","123456");
            mailService.sendCodeMail("1329634286@qq.com", "验证码", "登录", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Autowired
    UserWalletService userWalletService;
    @Autowired
    UserWalletMapper userWalletMapper;

    @Test
    void addWallet() {
        System.out.println(userWalletService.initUserWallet("123456"));
//        System.out.println(userWalletMapper.updateById(new UserWallet().setUserId("123456").setBalance(12.30)));
    }

    @Test
    void getRechargeCombo() {
        System.out.println(userWalletService.getAllRechargeCombo());
    }


    @Autowired
    GoodsCategoryService goodsCategoryService;

    @Test
    void getGoodsCategoryAll() {
        goodsCategoryService.getAllCategoryTree();
    }


    @Test
    void selectUserRoleAndPermissionById() {
        System.out.println(userMapper.selectUserRoleAndPermissionByUserId("2163652592439853323"));
//        System.out.println(userMapper.selectAllRole());
//        System.out.println(userMapper.selectAllPermission());
    }


    @Autowired
    ShopCartMapper shopCartMapper;

    @Test
    void selectShopCartPage() {

        System.out.println(shopCartMapper.selectShopCartPage(1, 10, "2163652592439853323"));
    }


    @Autowired
    EventMapper eventMapper;

    @Test
    void selectEventGoodsList() {
        System.out.println(eventMapper.selectEventGoodsList("2018072309121200101"));
    }



    @Test
    void userWalletMapper() {
        System.out.println(userWalletMapper.addWallet("2163652592439853323",new BigDecimal("2000.00"),new BigDecimal("1000.00")));
    }


}
