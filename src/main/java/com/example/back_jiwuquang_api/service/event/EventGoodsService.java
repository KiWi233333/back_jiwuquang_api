package com.example.back_jiwuquang_api.service.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.back_jiwuquang_api.dto.event.EventGoodsDTO;
import com.example.back_jiwuquang_api.pojo.event.Event;
import com.example.back_jiwuquang_api.pojo.event.EventGoods;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.repository.event.EventGoodsMapper;
import com.example.back_jiwuquang_api.repository.event.EventMapper;
import com.example.back_jiwuquang_api.repository.goods.GoodsMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.back_jiwuquang_api.domain.constant.EventConstant.EVENT_GOODS_MAPS_KEY;

@Service
public class EventGoodsService {

    @Autowired
    EventGoodsMapper eventGoodsMapper;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    EventMapper eventMapper;
    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 添加活动的商品（单个）
     *
     * @param eventGoodsDTO 参数
     * @return Result
     */
    public Result addEventGoodsById(EventGoodsDTO eventGoodsDTO) {
        // 1、sql query
        EventGoods eventGoods = EventGoodsDTO.toEventGoods(eventGoodsDTO);
        if (eventMapper.selectOne(new LambdaQueryWrapper<Event>().eq(Event::getId, eventGoodsDTO.getId())) == null || goodsMapper.selectOne(new LambdaQueryWrapper<Goods>().eq(Goods::getId, eventGoodsDTO.getGoodsId())) == null) {
            return Result.fail(Result.INSERT_NULL_ERR, "对应活动或商品不存在！");
        }
        // 2、insert into
        if (eventGoodsMapper.insert(eventGoods) <= 0) {
            return Result.fail(Result.INSERT_ERR, "添加失败");
        }
        redisUtil.delete(EVENT_GOODS_MAPS_KEY + eventGoodsDTO.getId());
        return Result.ok("添加成功！", null);
    }


    /**
     * 添加活动商品（批量）
     *
     * @param list 参数list
     * @return Result
     */
    public Result addEventGoodsByBatch(List<EventGoodsDTO> list) {
        // 1、转化 校验
        String flag = list.get(0).getId();
        List<EventGoods> eventGoodsList = new ArrayList<>();
        for (EventGoodsDTO eg : list) {
            if (!flag.equals(eg.getId())) {
                return Result.fail(Result.INSERT_ERR, "添加失败，父活动不一致！");
            }
        }
        // 2、sql query
        if (eventMapper.selectOne(new LambdaQueryWrapper<Event>().eq(Event::getId, flag)) == null) {
            return Result.fail(Result.INSERT_NULL_ERR, "对应活动不存在！");
        }
        // 3、批量插入
        int count = eventGoodsMapper.insertBatchSomeColumn(eventGoodsList);
        redisUtil.delete(EVENT_GOODS_MAPS_KEY + flag);
        return Result.ok("添加成功！", count);
    }
}
