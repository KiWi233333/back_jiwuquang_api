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
        if (eventMapper.selectOne(new LambdaQueryWrapper<Event>().eq(Event::getId, eventGoodsDTO.getId())) == null || goodsMapper.selectOne(new LambdaQueryWrapper<Goods>().eq(Goods::getId, eventGoodsDTO.getGoodsId())) == null) {
            return Result.fail(Result.LINK_NULL_ERR, "对应活动或商品不存在！");
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
            return Result.fail(Result.LINK_NULL_ERR, "对应活动不存在！");
        }
        // 3、批量插入
        int count = eventGoodsMapper.insertBatchSomeColumn(eventGoodsList);
        redisUtil.delete(EVENT_GOODS_MAPS_KEY + flag);
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
        if (eventMapper.selectOne(new LambdaQueryWrapper<Event>().eq(Event::getId, eventGoodsDTO.getId())) == null) {
            return Result.fail(Result.LINK_NULL_ERR, "修改失败！");
        }
        EventGoods eventGoods = EventGoodsDTO.toEventGoods(eventGoodsDTO);
        // 2、sql
        int flag = eventGoodsMapper.updateById(eventGoods);
        if (flag <= 0) {
            return Result.fail(Result.UPDATE_ERR, "修改失败！");
        }
        // 3、删除缓存
        redisUtil.delete(EVENT_GOODS_MAPS_KEY + eventGoodsDTO.getId());
        return Result.ok("修改成功！", null);
    }

    /**
     * 删除活动商品
     *
     * @param eid 活动id
     * @param gid 活动商品id
     * @return Result
     */
    public Result deleteEventGoodsById(String eid, String gid) {
        // 1、sql 删除
        if (eventGoodsMapper.deleteById(gid) <= 0) {
            return Result.fail(Result.DELETE_ERR, "删除失败！");
        }
        // 2、删除缓存
        redisUtil.hDelete(EVENT_GOODS_MAPS_KEY + eid, gid);
        return Result.ok("删除成功！");
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
            return Result.fail(Result.DELETE_ERR, "删除失败！");
        }
        // 2、删除
        redisUtil.hDelete(EVENT_GOODS_MAPS_KEY + eid);
        return Result.ok("删除成功！", count);
    }
}
