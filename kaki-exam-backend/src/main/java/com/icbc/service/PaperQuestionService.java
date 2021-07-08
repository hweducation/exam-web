package com.icbc.service;


import com.icbc.entity.PageResult;
import com.icbc.pojo.Paper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * BrandService 服务接口
 * @date 2019-07-28 17:18:26
 * @version 1.0
 */
public interface PaperQuestionService {

	/** 添加方法 */
	void save(Map<String,Object> map);

	/** 修改方法 */
	void update(Paper paper);

	/** 根据主键id删除 */
	void delete(Serializable id);

	/** 批量删除 */
	void deleteAll(Serializable[] ids);

	/** 根据主键id查询 */
	Paper findOne(Serializable id);

	/** 查询全部 */
	List<Paper> findAll();

	/** 多条件分页查询 */
	PageResult findByPage(Paper paper, int page, int rows);

	//跟据卷纸id查询题目id（一对多）
	List<Integer> findByPaperId(Integer id);
}