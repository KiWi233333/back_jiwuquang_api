package com.example.back_jiwuquang_api.service.goods;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.dto.goods.GoodsDTO;
import com.example.back_jiwuquang_api.dto.goods.GoodsPageDTO;
import com.example.back_jiwuquang_api.dto.goods.UpdateGoodsDTO;
import com.example.back_jiwuquang_api.pojo.goods.Goods;
import com.example.back_jiwuquang_api.pojo.goods.GoodsSku;
import com.example.back_jiwuquang_api.repository.goods.GoodsMapper;
import com.example.back_jiwuquang_api.repository.goods.GoodsSkuMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.back_jiwuquang_api.domain.constant.GoodsConstant.GOODS_INFO_MAPS; 

/**
 * 描述
 *
 * @className: GoodsService
 * @author: Kiwi23333
 * @description: TODO描述
 * @date: 2023/5/1 16:06
 */
@Service
@Slf4j
public class GoodsService {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsSkuMapper goodsSkuMapper;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 分页查询商品（）
     *
     * @param goodsPageDTO 查询参数
     * @param page         当前页码
     * @param size         每页数量
     * @param isPermission 是否上架 0 管理员 1 用户
     * @return Page
     */
    public Result getGoodsListByPageSize(GoodsPageDTO goodsPageDTO, Integer page, Integer size, Integer isPermission) {

        Page<Goods> pages = new Page<>(page, size); // 创建分页对象，指定当前页码和每页记录数
        LambdaQueryWrapper<Goods> qw = new LambdaQueryWrapper<>(); // 创建查询条件
        // 筛选上架商品(用户)
        if (isPermission == 0) qw.eq(Goods::getIsShow, 1);
        // 按名称查找
        if (goodsPageDTO.getName() != null) {
            qw.like(Goods::getName, goodsPageDTO.getName()).or().like(Goods::getDescription, goodsPageDTO.getName());
        }
        // 按类型排序
        if (goodsPageDTO.getCid() != null) {
            qw.eq(Goods::getCategoryId, goodsPageDTO.getCid());
        }
        // 是否为新品排序
        if (goodsPageDTO.getIsNew() != null) {
            qw.eq(Goods::getIsNew, goodsPageDTO.getIsNew() == 0 ? 0 : 1);
        }
        // 是否为免邮
        if (goodsPageDTO.getIsPostage() != null) {
            if (goodsPageDTO.getIsPostage().equals(1)) {
                qw.eq(Goods::getPostage, 0);
            } else {
                qw.lt(Goods::getPostage, 0);
            }
        }
        // 销量排序
        if (goodsPageDTO.getSaleSort() != null) {
            qw.orderBy(true, goodsPageDTO.getSaleSort() == 0, Goods::getSales);
        }
        // 价格排序
        if (goodsPageDTO.getPriceSort() != null) {
            qw.orderBy(true, goodsPageDTO.getPriceSort() == 0, Goods::getPrice);
        }
        // 浏览量排序
        if (goodsPageDTO.getViewsSort() != null) {
            qw.orderBy(true, goodsPageDTO.getViewsSort() == 0, Goods::getViews);
        }
        IPage<Goods> userPage = goodsMapper.selectPage(pages, qw); // 调用Mapper接口方法进行分页查询

        return Result.ok("获取成功！", userPage);
    }

    /** 采取 Hash缓存 GOODS_INFO_MAPS **/

    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     */
    public Result getGoodsInfoById(String id) {
        // 1、redis
        if (StringUtil.isNullOrEmpty(id)) return Result.fail("商品id不能为空！");
        Goods goods;
        goods = (Goods) redisUtil.hGet(GOODS_INFO_MAPS, id);
        if (goods != null) return Result.ok(goods);
        // 2、数据库
        goods = goodsMapper.selectById(id);
        if (goods != null) {
            redisUtil.hPut(GOODS_INFO_MAPS, id, goods);// 缓存
            return Result.ok("获取成功！", goods);
        }
        return Result.fail("查无该商品！");
    }

    /**
     * 添加商品
     *
     * @param goodsDTO 商品参数
     * @return Result
     */
    public Result addGoodsByGoodsDTO(GoodsDTO goodsDTO) {
        Goods goods = GoodsDTO.toGoods(goodsDTO);
        // 1、插入sql
        if (goodsMapper.insert(goods) <= 0) {
            log.info("Good add failed 添加商品失败 ！");
            return Result.fail("添加失败，参数错误！");
        }
        // 2、添加缓存
        redisUtil.hPut(GOODS_INFO_MAPS, goods.getId(), goods);
        return Result.ok("添加成功！", null);
    }

    /**
     * 修改商品失败
     *
     * @param upGoodsDTO UpdateGoodsDTO
     * @return Result
     */
    public Result updateGoods(UpdateGoodsDTO upGoodsDTO) {
        if (goodsMapper.updateById(UpdateGoodsDTO.toGoods(upGoodsDTO)) <= 0) {
            log.info("Good add failed 修改商品失败 ！");
            return Result.fail("修改失败！");
        }
        // 清除缓存
        redisUtil.hDelete(GOODS_INFO_MAPS, upGoodsDTO.getId());
        return Result.ok("修改成功！", null);
    }

    /**
     * 删除商品 byId
     *
     * @param id 商品id
     * @return Result
     */
    @Transactional(rollbackFor = Exception.class)
    public Result deleteGoodsById(String id) {
        // 删除商品 和 删除商品规格
        if (goodsMapper.deleteById(id) > 0 &&
                goodsSkuMapper.delete(new QueryWrapper<GoodsSku>().eq("goods_id", id)) >= 0) {
            // 删除 商品  缓存
            redisUtil.hDelete(GOODS_INFO_MAPS, id);
            return Result.ok("删除成功！", null);
        } else {
            return Result.fail("删除失败！");
        }
    }
}
