package com.example.back_jiwuquang_api.repository.comm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.back_jiwuquang_api.pojo.comm.CommPost;
import com.example.back_jiwuquang_api.pojo.comm.CommPostComment;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface CommPostCommentMapper extends BaseMapper<CommPostComment>, MPJBaseMapper<CommPostComment> {

}