package com.example.back_jiwuquang_api.repository.orders;

import com.example.back_jiwuquang_api.pojo.orders.OrdersComment;
import com.example.back_jiwuquang_api.repository.SpiceBaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersCommentMapper extends SpiceBaseMapper<OrdersComment>, MPJBaseMapper<OrdersComment> {

}
