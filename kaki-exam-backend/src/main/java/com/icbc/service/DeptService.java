package com.icbc.service;


import com.icbc.entity.PageResult;
import com.icbc.pojo.Dept;

import java.io.Serializable;
import java.util.List;

/**
 * BrandService 服务接口
 * @date 2019-07-28 17:18:26
 * @version 1.0
 */
public interface DeptService {

	/** 添加方法 */
	void save(Dept dept);

	/** 修改方法 */
	void update(Dept dept);

	/** 根据主键id删除 */
	void delete(Serializable id);

	/** 批量删除 */
	void deleteAll(Serializable[] ids);

	/** 根据主键id查询 */
	Dept  findOne(Serializable id);


	/** 多条件分页查询 */
	PageResult findByPage(Dept dept, int page, int rows);


    List<Dept> findAll();
}