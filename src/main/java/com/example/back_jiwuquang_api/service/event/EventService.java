package com.example.back_jiwuquang_api.service.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.back_jiwuquang_api.pojo.event.Event;
import com.example.back_jiwuquang_api.repository.event.EventMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import com.example.back_jiwuquang_api.vo.EventGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.back_jiwuquang_api.domain.constant.EventConstant.EVENT_GOODS_MAPS_KEY;
import static com.example.back_jiwuquang_api.domain.constant.EventConstant.EVENT_LIST_KEY;

@Service
public class EventService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    EventMapper eventMapper;


    /**
     * 获取活动的商品列表
     *
     * @param id 活动id
     * @return Result
     */
    public Result getEventGoodsList(String id) {
        // 1、缓存获取
        List<EventGoodsVO> list = new ArrayList<>();
        Map<String, Object> map = redisUtil.hGetAll(EVENT_GOODS_MAPS_KEY + id);
        for (String key : map.keySet()) {
            list.add((EventGoodsVO) map.get(key));
        }
        // 2、sql
        if (map.isEmpty() || list.isEmpty()) {
            list = eventMapper.selectEventGoodsList(id);
            // 3、缓存
            for (EventGoodsVO vo : list) {
                map.put(vo.getGoodsId(), vo);
            }
            redisUtil.hPutAll(EVENT_GOODS_MAPS_KEY + id, map);
        }
        return Result.ok("获取成功！", list);
    }

    /**
     * 获取所有活动列表
     * userType 用户类型：0用户 1：admin
     *
     * @return Result
     */
    public Result getAllEventList(int userType) {
        List<Event> list;
        // 1、缓存获取
        list = (List<Event>) redisUtil.get(EVENT_LIST_KEY);
        // 2、sql获取
        if (list == null) {
            list = eventMapper.selectList(new LambdaQueryWrapper<Event>().orderByDesc(Event::getCreatedTime));
            redisUtil.set(EVENT_LIST_KEY, list);
        }

        if (list == null || list.isEmpty()) {
            return Result.ok("暂无活动！", null);
        }
        return Result.ok("获取成功！", list);
    }


}
