package com.example.back_jiwuquang_api.repository.comm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.back_jiwuquang_api.pojo.comm.CommCategory;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommCategoryMapper extends BaseMapper<CommCategory>, MPJBaseMapper<CommCategory> {

    default List<CommCategory> selectALlCategoryTree() {
        LambdaQueryWrapper<CommCategory> qw = new LambdaQueryWrapper<CommCategory>()
                .orderByDesc(CommCategory::getSortOrder);
        List<CommCategory> list = this.selectList(qw);
        if (list.size() == 0) return null;
        return CommCategory.buildTree(list,null);
    }

}