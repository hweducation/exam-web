package com.icbc.mapper;

import com.icbc.pojo.Paper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BrandMapper 数据访问接口
 * @date 2019-07-28 17:13:36
 * @version 1.0
 */

public interface PaperMapper extends Mapper<Paper>{

    //条件查找
    List<Paper> findAll(Paper paper);

}