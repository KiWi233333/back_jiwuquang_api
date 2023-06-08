package com.example.back_jiwuquang_api.impl.comm;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.back_jiwuquang_api.dto.comm.SelectCommPostDTO;
import com.example.back_jiwuquang_api.pojo.comm.CommPost;
import com.example.back_jiwuquang_api.repository.comm.CommPostMapper;
import com.example.back_jiwuquang_api.service.comm.CommPostService;
import com.example.back_jiwuquang_api.util.RedisUtil;
import com.example.back_jiwuquang_api.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社区帖子业务实现层
 *
 * @className: GoodsService
 * @author: Kiwi23333
 * @description: 社区帖子业务层
 * @date: 2023/5/1 16:06
 */
@Service
@Slf4j
public class CommPostServiceImpl extends CommPostService {

    @Autowired
    RedisUtil redisUtil;


    /**
     * 获取帖子（分页）
     *
     * @param selectCommPostDTO 筛选
     * @param cid              分类id
     * @param page              页
     * @param size              数
     * @return Result
     */
    public Result getCommPostByPage(SelectCommPostDTO selectCommPostDTO,String cid, int page, int size) {
        IPage<CommPost> pagesList = this.getCommPostByPageSev(selectCommPostDTO, cid,page, size, false);
        if (pagesList != null) {
            return Result.ok(pagesList);
        }
        return Result.fail("获取失败！");
    }
}
