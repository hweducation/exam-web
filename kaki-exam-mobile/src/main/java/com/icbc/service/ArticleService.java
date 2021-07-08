package com.icbc.service;
import com.icbc.common.PageResult;
import com.icbc.pojo.Article;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @date 2020-04-12 13:53:36
 * @author koji kigawa
 * @version 2.0
 */
public interface ArticleService {

	/** 添加方法 */
	void save(Article article);

	/** 修改方法 */
	void update(Article article);

	/** 根据主键id删除 */
	void delete(Serializable id);

	/** 批量删除 */
	void deleteAll(Serializable[] ids);

	/** 根据主键id查询 */
	Article  findOne(Serializable id);


	/** 多条件分页查询 */
	PageResult findByPage(Article article, int page, int rows);


    List<Article> findAll();

    //查询历史卷纸排行榜
	PageResult findByPaperId(Article article, Integer curPage, Integer rows);

	//根据id查询某个文章
	Article findById(Article article);
}