package com.icbc.mapper;

import com.icbc.pojo.Paper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BrandMapper 数据访问接口
 * @date 2019-07-28 17:13:36
 * @version 1.0
 */

public interface PaperQuestionMapper extends Mapper<Paper>{

    //条件查找
    List<Paper> findAll(Paper paper);

    void insertMap(@Param("paperId") Integer paperId, @Param("questionidList")List<Integer> questionidList);

    //跟据卷纸id查询题目id（一对多）
    List<Integer> findByPaperId(Integer id);

    //根据卷纸id删除所有题目
    void deleteByPaperId(Integer paperId);
}