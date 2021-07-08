package com.icbc.service;



import com.icbc.common.PageResult;
import com.icbc.pojo.Dept;
import com.icbc.pojo.History;

import java.io.Serializable;
import java.util.List;

/**
 * BrandService 服务接口
 * @date 2019-07-28 17:18:26
 * @version 1.0
 */
public interface HistoryService {

	/** 添加方法 */
	void save(History history);

	/** 修改方法 */
	void update(History history);

	/** 根据主键id删除 */
	void delete(Serializable id);

	/** 批量删除 */
	void deleteAll(Serializable[] ids);

	/** 根据主键id查询 */
	History  findOne(Serializable id);


	/** 多条件分页查询 */
	PageResult findByPage(History history, int page, int rows);


    List<History> findAll();

    //查询历史卷纸排行榜
	PageResult findByPaperId(History history, Integer curPage, Integer rows);
}