package com.example.back_jiwuquang_api.service.comm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back_jiwuquang_api.dto.comm.SelectCommPostCommentDTO;
import com.example.back_jiwuquang_api.pojo.comm.CommPost;
import com.example.back_jiwuquang_api.pojo.comm.CommPostComment;
import com.example.back_jiwuquang_api.repository.comm.CommPostCommentMapper;
import com.example.back_jiwuquang_api.util.JacksonUtil;
import com.example.back_jiwuquang_api.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.back_jiwuquang_api.domain.constant.CommConstant.COMM_POST_PAGE_MAPS;


/**
 * 社区帖子评论业务层
 *
 * @className: GoodsService
 * @author: Kiwi23333
 * @description: 社区帖子评论业务层
 * @date: 2023/5/1 16:06
 */
@Service
@Slf4j
public class CommPostCommentService {

    @Autowired
    CommPostCommentMapper commPostCommentMapper;
    @Autowired
    RedisUtil redisUtil;

    public IPage<CommPostComment> getCommPostCommentListByPage(SelectCommPostCommentDTO dto, String cid, int page, int size) {
        // 1、缓存查询缓存
        IPage<CommPostComment> result = (IPage<CommPostComment>) redisUtil.hGet(COMM_POST_PAGE_MAPS + ":" + page + ":" + size + ":", JacksonUtil.toJSON(dto));
        if (result != null && result.getTotal() > 0) {
            return result;
        }
        Page<CommPostComment> pages = new Page<>(page, size); // 创建分页对象，指定当前页码和每页记录数
        LambdaQueryWrapper<CommPostComment> qw = new LambdaQueryWrapper<>(); // 创建查询条件
        // 评论数排序
        if (dto.getIsNew() != null) {
            qw.orderBy(true, dto.getIsNew() == 0, CommPostComment::getCreateTime);
        }
        // 筛选结果
        // 2、查询
        result = commPostCommentMapper.selectPage(pages, qw); // 调用Mapper接口方法进行分页查询
        // 3、缓存
        if (result != null && result.getTotal() != 0) {
            redisUtil.hPut(COMM_POST_PAGE_MAPS + ":" + page + ":" + size + ":", JacksonUtil.toJSON(dto), result);
        }
        return result;
    }
}
