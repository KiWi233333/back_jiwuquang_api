package com.example.back_jiwuquang_api.service.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.back_jiwuquang_api.dto.event.EventDTO;
import com.example.back_jiwuquang_api.pojo.event.Event;
import com.example.back_jiwuquang_api.pojo.event.EventGoods;
import com.example.back_jiwuquang_api.repository.event.EventGoodsMapper;
import com.example.back_jiwuquang_api.repository.event.EventMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import com.example.back_jiwuquang_api.vo.EventGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
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
    /** ********************* 活   动 **********************/

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
        if (map.isEmpty()) {
            list = eventMapper.selectEventGoodsList(id);
            // 3、缓存
            if (!list.isEmpty()) {
                for (EventGoodsVO goodsVO : list) {
                    map.put(goodsVO.getId(), goodsVO);
                }
                redisUtil.hPutAll(EVENT_GOODS_MAPS_KEY + id, map);
            }
            // 缓存时间当天最后一时刻
            Date target = toDateWithZone();
            redisUtil.expireAt(EVENT_GOODS_MAPS_KEY + id, target);
        }
        return Result.ok("获取成功！", list);
    }

    private static Date toDateWithZone() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfDay = now.withHour(23).withMinute(59).withSecond(59).withNano(0);
        ZoneOffset offset = ZoneOffset.of("+0800");
        return Date.from(endOfDay.toInstant(offset));
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
            list = eventMapper.selectList(new LambdaQueryWrapper<Event>().orderByDesc(Event::getCreateTime));
            redisUtil.set(EVENT_LIST_KEY, list);
            // 缓存时间当天最后一时刻
            Date target = toDateWithZone();
            redisUtil.expireAt(EVENT_LIST_KEY, target);
        }

        if (list == null || list.isEmpty()) {
            return Result.ok("暂无活动！", null);
        }
        return Result.ok("获取成功！", list);
    }


    /**
     * 添加活动
     *
     * @param eventDTO 参数
     * @return Result
     */
    public Result addEventByEventDTO(EventDTO eventDTO) {
        // 1、sql
        Event event = EventDTO.toEventGoods(eventDTO);
        // 校验
        if (EventDTO.startEndTimeValid(eventDTO)) {
            return Result.fail(Result.INSERT_ERR, "时间间隔不少于30秒！");
        }
        if (eventMapper.insert(event) <= 0) {
            return Result.fail(Result.INSERT_ERR, "添加失败！");
        }
        // 2、删除缓存
        redisUtil.delete(EVENT_LIST_KEY);
        return Result.ok("添加成功！", null);
    }

    /**
     * 修改活动
     *
     * @param id       活动id
     * @param eventDTO 参数
     * @return Result
     */
    public Result updateEventById(String id, EventDTO eventDTO) {
        // 1、sql query
        // 校验
        if (EventDTO.startEndTimeValid(eventDTO)) {
            return Result.fail(Result.INSERT_ERR, "时间间隔不少于30秒！");
        }
        if (eventMapper.updateById(EventDTO.toEventGoods(eventDTO).setId(id)) <= 0) {
            return Result.fail(Result.UPDATE_ERR, "修改失败！");
        }
        redisUtil.delete(EVENT_LIST_KEY);
        return Result.ok("修改成功！", null);
    }


    @Autowired
    EventGoodsMapper eventGoodsMapper;

    /**
     * 删除活动
     *
     * @param id 活动id
     * @return Result
     */
    public Result deleteEventById(String id) {
        // 1、sql 删除
        if (eventMapper.deleteById(id) <= 0) {
            return Result.fail(Result.DELETE_ERR, "删除失败！");
        }
        // 2、删除活动附属商品
        int count = eventGoodsMapper.delete(new LambdaQueryWrapper<EventGoods>().in(EventGoods::getEventId, id));
        // 3、删除缓存
        redisUtil.delete(EVENT_LIST_KEY);
        return Result.ok("删除成功！", "删除1个活动" + count + "个活动商品");
    }
}
