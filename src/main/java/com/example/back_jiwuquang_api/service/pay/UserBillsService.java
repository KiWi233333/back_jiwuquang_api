package com.example.back_jiwuquang_api.service.pay;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.back_jiwuquang_api.pojo.pay.UserBills;
import com.example.back_jiwuquang_api.repository.pay.UserBillsMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.example.back_jiwuquang_api.domain.constant.PayConstant.USER_BILLS_MAPS;

@Service
@Slf4j
public class UserBillsService {

    @Autowired
    UserBillsMapper userBillsMapper;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 保存账单
     *
     * @param incomeOrOut  收支类型 0：支出，1：收入
     * @param title        类型
     * @param orderId
     * @param currencyType
     * @param amount
     * @param voucherId
     * @return
     */
    public Boolean saveBills(String userId, Integer incomeOrOut, String title, String orderId, Integer currencyType, BigDecimal amount, String voucherId) {
        UserBills bills = new UserBills()
                .setUserId(userId)
                .setTitle(title)
                .setType(incomeOrOut)
                .setOrdersId(orderId)
                .setCurrencyType(currencyType)
                .setAmount(amount)
                .setVoucherId(voucherId);
        int flag = userBillsMapper.insert(bills);
        if (flag <= 0) {// 失败
            return false;
        }
        // 成功
        redisUtil.hPut(USER_BILLS_MAPS + userId, bills.getId(), bills);
        return true;
    }

    public List<UserBills> getBillsOutByOrder(String userId, String orderId) {
        return this.getBillsByOrder(userId,orderId,0);
    }
    public List<UserBills> getBillsInByOrder(String userId, String orderId) {
        return this.getBillsByOrder(userId,orderId,1);
    }



    public List<UserBills> getBillsByOrder(String userId, String orderId,Integer type) {
        return userBillsMapper.selectList(new LambdaQueryWrapper<UserBills>().eq(UserBills::getOrdersId, orderId).eq(UserBills::getUserId,userId));
    }
}
