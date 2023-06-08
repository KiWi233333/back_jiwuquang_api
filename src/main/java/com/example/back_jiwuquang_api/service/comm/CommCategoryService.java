package com.example.back_jiwuquang_api.service.comm;

import com.example.back_jiwuquang_api.pojo.comm.CommCategory;
import com.example.back_jiwuquang_api.repository.comm.CommCategoryMapper;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.back_jiwuquang_api.domain.constant.GoodsConstant.GOODS_CATEGORY_LIST;

/**
 * 社区圈分类业务层
 *
 * @className: GoodsService
 * @author: Kiwi23333
 * @description: 社区圈分类业务层
 * @date: 2023/5/1 16:06
 */
@Service
@Slf4j
public class CommCategoryService {

    @Autowired
    CommCategoryMapper commCategoryMapper;
    @Autowired
    RedisUtil redisUtil;

    /**
     * 获取所有分类
     * @return Result
     */
    public Result getAllCategoryTree() {
        List<CommCategory> list = (List<CommCategory>) redisUtil.get(GOODS_CATEGORY_LIST);
        // 数据库取
        if (list == null || list.isEmpty()) {
            list = commCategoryMapper.selectALlCategoryTree();
        } else {
            return Result.ok("获取成功",list);
        }
        // 缓存
        if (!list.isEmpty()) {
            redisUtil.set(GOODS_CATEGORY_LIST, list);
            return Result.ok("获取成功",list);
        } else {
            return Result.fail("获取失败！");
        }
    }

//    /**
//     * 添加分类（批量）
//     *
//     * @param categoryVOList 分类参数
//     * @return Result
//     */
//    public Result addCategoryByList(List<CoommDTO> categoryVOList) {
//        // 1、生成分类列表
//        List<Coomm> categoryList = new ArrayList<>();
//        categoryVOList.forEach(p -> {
//            if (p != null) categoryList.add(Coomm.toCoomm(p));
//        });
//        // 2、插入列表
//        if (commCategoryMapper.insertBatchSomeColumn(categoryList) != categoryList.size()) {
//            return Result.fail("添加失败！");
//        }
//        // 3、清空缓存
//        redisUtil.delete(GOODS_CATEGORY_LIST);
//        return Result.ok("添加成功，"+categoryList.size()+"条数据", categoryList.size());
//    }
//
//    /**
//     * 删除分类（单一）
//     *
//     * @param id
//     * @return
//     */
//    public Result deleteOneByCategoryId(String id) {
//        // 删除其附属的分类
//        int flags = commCategoryMapper.delete(new QueryWrapper<Coomm>().lambda().eq(Coomm::getId, id).or().eq(Coomm::getParentId, id));
//        // 重新缓存
//        if (flags > 0) {// 重新缓存
//            redisUtil.delete(GOODS_CATEGORY_LIST);
//            return Result.ok("删除成功！", flags);
//        } else {
//            return Result.fail("删除失败！");
//        }
//    }
//
//
//    /**
//     * 删除分类（批量）可跨级
//     *
//     * @param ids id集合
//     * @return Result
//     */
//    public Result deleteByCategoryIds(List<String> ids) {
//        int count = 0;
//        // 1、数据库删除操作
//        count = commCategoryMapper.delete(
//                new QueryWrapper<Coomm>().lambda()
//                .in(Coomm::getId, ids)
//                .or()
//                .in(Coomm::getParentId, ids));
//        if (count == 0) {
//            // 2、redis 删除操作
//            redisUtil.delete(GOODS_CATEGORY_LIST);
//            return Result.fail("删除失败！");
//        } else {
//            return Result.ok("删除成功！", count);
//        }
//    }
}
