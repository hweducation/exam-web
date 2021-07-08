package com.icbc.service;


import com.icbc.entity.PageResult;
import com.icbc.pojo.Question;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * BrandService 服务接口
 * @date 2019-07-28 17:18:26
 * @version 1.0
 */
public interface QuestionService {

	/** 添加方法 */
	void save(Question question);

	/** 修改方法 */
	void update(Question question);

	/** 根据主键id删除 */
	void delete(Serializable id);

	/** 批量删除 */
	void deleteAll(Serializable[] ids);

	/** 根据主键id查询 */
	Question findOne(Serializable id);

	/** 查询全部 */
	List<Question> findAll();

	/** 多条件分页查询 */
	PageResult findByPage(Question question, int page, int rows);

}