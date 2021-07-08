package com.icbc.service;

import com.icbc.common.PageResult;
import com.icbc.pojo.Seller;


import java.util.List;
import java.io.Serializable;
/**
 * SellerService 服务接口
 * @date 2019-07-28 09:17:10
 * @version 1.0
 */
public interface SellerService {

	/** 添加方法 */
	void save(Seller seller);

	/** 修改方法 */
	void update(Seller seller);

	/** 根据主键id删除 */
	void delete(Serializable id);

	/** 批量删除 */
	void deleteAll(Serializable[] ids);

	/** 根据主键id查询 */
	Seller findOne(Serializable id);

	/** 查询全部 */
	List<Seller> findAll();

	/** 多条件分页查询 */
	PageResult findByPage(Seller seller, int page, int rows);

	/** 修改商家的状态 */
	void updateStatus(String sellerId, String status);
}