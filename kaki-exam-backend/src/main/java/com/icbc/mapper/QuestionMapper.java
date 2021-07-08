package com.icbc.mapper;

import com.icbc.pojo.Question;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * BrandMapper 数据访问接口
 * @date 2019-07-28 17:13:36
 * @version 1.0
 */

public interface QuestionMapper extends Mapper<Question>{

    //条件查找
    List<Question> findAll(Question question);

    //插入数据
    void insertPojo(Question question);

}