package com.icbc.mapper;

import com.icbc.pojo.Dept;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BrandMapper 数据访问接口
 * @date 2019-07-28 17:13:36
 * @version 1.0
 */

public interface DeptMapper extends Mapper<Dept>{

    //条件查找
    List<Dept> findAll(Dept dept);
}