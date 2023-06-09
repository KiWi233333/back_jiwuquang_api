package com.example.back_jiwuquang_api.impl.comm;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.back_jiwuquang_api.dto.comm.SelectCommPostCommentDTO;
import com.example.back_jiwuquang_api.dto.comm.SelectCommPostDTO;
import com.example.back_jiwuquang_api.pojo.comm.CommPost;
import com.example.back_jiwuquang_api.pojo.comm.CommPostComment;
import com.example.back_jiwuquang_api.service.comm.CommPostCommentService;
import com.example.back_jiwuquang_api.service.comm.CommPostService;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 社区帖子评论业务实现层
 *
 * @className: GoodsService
 * @author: Kiwi23333
 * @description: 社区帖子评论业务层
 * @date: 2023/5/1 16:06
 */
@Service
@Slf4j
public class CommPostCommentServiceImpl extends CommPostCommentService {

    @Autowired
    RedisUtil redisUtil;


    /**
     * 获取帖子（分页）
     *
     * @param dto  筛选
     * @param cid  分类id
     * @param page 页
     * @param size 数
     * @return Result
     */
    public Result getCommPostCommentList(SelectCommPostCommentDTO dto, String cid, int page, int size) {
        IPage<CommPostComment> pagesList = this.getCommPostCommentListByPage(dto, cid, page, size);
        if (pagesList != null) {
            return Result.ok(pagesList);
        }
        return Result.fail(Result.SELECT_ERR,"获取失败！");
    }
}
