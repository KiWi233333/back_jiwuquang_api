package com.example.back_jiwuquang_api.repository.event;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.pojo.event.Event;
import com.example.back_jiwuquang_api.pojo.event.EventGoods;
import com.example.back_jiwuquang_api.repository.SpiceBaseMapper;
import com.example.back_jiwuquang_api.vo.EventGoodsVO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EventMapper extends SpiceBaseMapper<Event> {


//    IPage<EventGoodsVO> selectEventGoodsPageById(int page, int size,String eventId) {
//        MPJLambdaWrapper<Event> qw = new MPJLambdaWrapper<> ();
//        qw.leftJoin(EventGoods.class,);
//        Page<EventGoodsVO> pages = new Page<>(page, size);
//        return this.selectPage(pages, qw);
//    }

    @Select("SELECT e.id event_id,eg.id event_goods_id, g.id goods_id,g.*,eg.event_price FROM event e " +
            "LEFT JOIN event_goods eg ON eg.event_id = e.id " +
            "LEFT JOIN goods g ON eg.goods_id = g.id " +
            "WHERE e.id = #{eventId}")
    List<EventGoodsVO> selectEventGoodsList(@Param("eventId") String eventId);


}
