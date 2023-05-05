package com.example.back_jiwuquang_api.service.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.dto.goods.GoodsPageDTO;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.repository.goods.GoodsMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @className: GoodsService
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/1 16:06
 */
@Service

public class GoodsService {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 分页查询商品（）
     * @param goodsPageDTO 查询参数
     * @param page 当前页码
     * @param size 每页数量
     * @return Page
     */
    public Result getGoodsListByPageSize(GoodsPageDTO goodsPageDTO, Integer page, Integer size) {

        Page<Goods> pages = new Page<>(page, size); // 创建分页对象，指定当前页码和每页记录数
        QueryWrapper<Goods> qw = new QueryWrapper<>(); // 创建查询条件
        // 按名称查找
        if (goodsPageDTO.getName() != null) {
            qw.like("name",goodsPageDTO.getName()).or().like("description",goodsPageDTO.getName());
        }
        // 按类型排序
        if (goodsPageDTO.getCid() != null) {
            qw.eq("category_id",goodsPageDTO.getCid());
        }
        // 是否为新品排序
        if (goodsPageDTO.getIsNew() != null) {
            qw.eq("is_new",goodsPageDTO.getIsNew()==0?0:1);
        }
        // 销量排序
        if (goodsPageDTO.getSaleSort() != null) {
            qw.orderBy(true, goodsPageDTO.getSaleSort() == 0, "sales");
        }
        // 价格排序
        if (goodsPageDTO.getPriceSort() != null) {
            qw.orderBy(true, goodsPageDTO.getPriceSort() == 0, "price");
        }
        // 浏览量排序
        if (goodsPageDTO.getViewsSort() != null) {
            qw.orderBy(true, goodsPageDTO.getViewsSort() == 0, "views");
        }
        IPage<Goods> userPage = goodsMapper.selectPage(pages, qw); // 调用Mapper接口方法进行分页查询

        return Result.ok(userPage);
    }

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    public Result getGoodsInfoById(String id) {
        if (StringUtil.isNullOrEmpty(id)) return Result.fail("商品id不能为空！");
        Goods goods = goodsMapper.selectById(id);
        if (goods!=null) return Result.ok(goods);
        return Result.fail("查询失败！");
    }
}
