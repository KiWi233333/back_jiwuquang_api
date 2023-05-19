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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.back_jiwuquang_api.domain.constant.EventConstant.EVENT_GOODS_MAPS_KEY;
import static com.example.back_jiwuquang_api.domain.constant.EventConstant.EVENT_LIST_KEY;

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


    /** ********************* 活动商品 **********************/
    /**
     * 添加活动的商品（单个）
     *
     * @param eventGoodsDTO 参数
     * @return Result
     */
    public Result addEventGoodsById(EventGoodsDTO eventGoodsDTO) {
        // 1、sql query
        EventGoods eventGoods = EventGoodsDTO.toEventGoods(eventGoodsDTO);
        if (eventMapper.selectOne(new LambdaQueryWrapper<Event>().eq(Event::getId, eventGoodsDTO.getEventId())) == null ||
                goodsMapper.selectOne(new LambdaQueryWrapper<Goods>().eq(Goods::getId, eventGoodsDTO.getGoodsId())) == null) {
            return Result.fail(Result.LINK_NULL_ERR, "对应活动或商品不存在！");
        }
        // 2、insert into
        if (eventGoodsMapper.insert(eventGoods) <= 0) {
            return Result.fail(Result.INSERT_ERR, "添加失败");
        }
        redisUtil.delete(EVENT_GOODS_MAPS_KEY + eventGoodsDTO.getEventId());
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
        String eventId = list.get(0).getEventId();
        List<EventGoods> eventGoodsList = new ArrayList<>();
        List<String> gidList = new ArrayList<>();
        for (EventGoodsDTO eg : list) {
            if (!eventId.equals(eg.getEventId())) {
                return Result.fail(Result.INSERT_ERR, "添加失败，父活动不一致！");
            } else {
                gidList.add(eg.getGoodsId());
                eventGoodsList.add(EventGoodsDTO.toEventGoods(eg));
            }
        }
        // 2、sql query
        if (eventMapper.selectOne(new LambdaQueryWrapper<Event>().eq(Event::getId, eventId)) == null) {
            return Result.fail(Result.LINK_NULL_ERR, "对应活动不存在！");
        }
        // 查询是否存在商品（批量查询）
        if (goodsMapper.selectCount(new LambdaQueryWrapper<Goods>().in(Goods::getId, gidList)) != gidList.size()) {
            return Result.fail(Result.LINK_NULL_ERR, "对应商品部分不存在！");
        }
        // 3、批量插入
        int count = eventGoodsMapper.insertBatchSomeColumn(eventGoodsList);
        redisUtil.delete(EVENT_GOODS_MAPS_KEY + eventId);
        return Result.ok("添加成功！", count);
    }

    /**
     * 修改活动商品
     *
     * @param eventGoodsDTO 参数
     * @return Result
     */
    public Result updateEventGoodsById(EventGoodsDTO eventGoodsDTO) {
        // 1、查询
        if (goodsMapper.selectById(eventGoodsDTO.getGoodsId()) == null) {
            return Result.fail(Result.LINK_NULL_ERR, "对应商品不存在！");
        }
        EventGoods eventGoods = EventGoodsDTO.toEventGoods(eventGoodsDTO).setId(eventGoodsDTO.getId());
        // 2、sql
        int flag = eventGoodsMapper.updateById(eventGoods);
        if (flag <= 0) {
            return Result.fail(Result.UPDATE_ERR, "修改失败，对应关联id错误！");
        }
        // 3、删除缓存
        redisUtil.delete(EVENT_GOODS_MAPS_KEY + eventGoodsDTO.getEventId());
        return Result.ok("修改成功！", null);
    }

    /**
     * 删除活动商品
     *
     * @param eid 活动id
     * @param id 关联id
     * @return Result
     */
    public Result deleteEventGoodsById(String eid, String id) {
        // 1、sql 删除
        if (eventGoodsMapper.deleteById(id) <= 0) {
            return Result.fail(Result.DELETE_ERR, "删除失败！");
        }
        // 2、删除缓存
        redisUtil.hDelete(EVENT_GOODS_MAPS_KEY + eid, id);
        return Result.ok("删除成功！", null);
    }

    /**
     * 删除活动商品（批量）
     *
     * @param eid 活动id
     * @param ids 活动商品ids
     * @return Result
     */
    public Result deleteEventGoodsByIds(String eid, List<String> ids) {
        // 1、delete sql
        int count = eventGoodsMapper.delete(new LambdaQueryWrapper<EventGoods>().eq(EventGoods::getEventId, eid).in(EventGoods::getId, ids));
        if (count <= 0) {
            return Result.fail(Result.LINK_NULL_ERR, "删除失败，资源不存在！");
        }
        // 2、删除
        redisUtil.delete(EVENT_GOODS_MAPS_KEY + eid);
        return Result.ok("删除成功！", count);
    }
}
