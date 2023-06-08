package com.example.back_jiwuquang_api.service.comm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.dto.comm.SelectCommPostDTO;
import com.example.back_jiwuquang_api.pojo.comm.CommPost;
import com.example.back_jiwuquang_api.repository.comm.CommPostMapper;
import com.example.back_jiwuquang_api.util.JacksonUtil;
import com.example.back_jiwuquang_api.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static com.example.back_jiwuquang_api.domain.constant.CommConstant.COMM_POST_PAGE_MAPS;

/**
 * 社区帖子业务层
 *
 * @className: GoodsService
 * @author: Kiwi23333
 * @description: 社区帖子业务层
 * @date: 2023/5/1 16:06
 */
@Service
@Slf4j
public class CommPostService {

    @Autowired
    CommPostMapper commPostMapper;
    @Autowired
    RedisUtil redisUtil;


    /**
     * 获取帖子（分页）
     *
     * @param dto          筛选dto
     * @param cid          分类id
     * @param page         页
     * @param size         数
     * @param isPermission 是否有查看全部权限
     * @return Result
     */
    public IPage<CommPost> getCommPostByPageSev(SelectCommPostDTO dto, String cid, int page, int size, boolean isPermission) {
        // 1、缓存查询缓存
        IPage<CommPost> result = (IPage<CommPost>) redisUtil.hGet(COMM_POST_PAGE_MAPS + ":" + page + ":" + size + ":", JacksonUtil.toJSON(dto));
        if (result != null && result.getTotal() > 0) {
            return result;
        }
        Page<CommPost> pages = new Page<>(page, size); // 创建分页对象，指定当前页码和每页记录数
        LambdaQueryWrapper<CommPost> qw = new LambdaQueryWrapper<>(); // 创建查询条件
        // 筛选分类
        if (StringUtils.isNotBlank(cid)) qw.eq(CommPost::getCategoryId, cid);
        // 筛选发布的帖子(用户)
        if (!isPermission) qw.eq(CommPost::getStatus, 1);
        // 按名称查找
        if (dto.getName() != null) {
            qw.and(q -> q.like(CommPost::getTitle, dto.getName()).or().like(CommPost::getContent, dto.getName()).or().like(CommPost::getTags, dto.getName()));
        }
        // 是否精华排序
        if (dto.getIsEssence() != null) {
            qw.eq(CommPost::getIsEssence, dto.getIsEssence() == 0 ? 0 : 1);
        }
        // 销量排序
        if (dto.getIsNew() != null) {
            qw.orderBy(true, dto.getIsNew() == 0, CommPost::getCreateTime);
        }
        // 浏览量排序
        if (dto.getViewsSort() != null) {
            qw.orderBy(true, dto.getViewsSort() == 0, CommPost::getViews);
        }
        // 2、查询
        result = commPostMapper.selectPage(pages, qw); // 调用Mapper接口方法进行分页查询
        // 3、缓存
        if (result != null && result.getTotal() != 0) {
            redisUtil.hPut(COMM_POST_PAGE_MAPS + ":" + page + ":" + size + ":", JacksonUtil.toJSON(dto), result);
        }
        return result;
    }


}
