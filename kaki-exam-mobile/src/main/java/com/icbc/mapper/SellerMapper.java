package com.icbc.mapper;

import com.icbc.pojo.Seller;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * SellerMapper 数据访问接口
 * @date 2019-07-28 09:14:10
 * @version 1.0
 */
public interface SellerMapper extends Mapper<Seller>{

    /** 多条件分页查询商家 */
    List<Seller> findAll(Seller seller);
}