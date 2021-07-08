package com.icbc.mapper;

import com.icbc.pojo.Dept;
import com.icbc.pojo.History;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BrandMapper 数据访问接口x
 * @date 2019-07-28 17:13:36
 * @version 1.0
 */

public interface HistoryMapper extends Mapper<History>{

    //条件查找
    List<History> findAll(History history);

    //插表
    void historyInsert(History history);

    List<History> findByPaperId(History history);
}