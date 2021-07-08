package com.icbc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icbc.common.PageResult;
import com.icbc.mapper.ArticleMapper;
import com.icbc.mapper.ExamUserMapper;
import com.icbc.pojo.Article;
import com.icbc.pojo.Dept;
import com.icbc.pojo.ExamUser;
import com.icbc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @date 2020-04-12 13:53:36
 * @author koji kigawa
 * @version 2.0
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private ExamUserMapper examUserMapper;

	/** 添加方法 */
	public void save(Article ariticle){
		try {

			ExamUser examUser = examUserMapper.selectByPrimaryKey(ariticle.getUserId());

			if(examUser==null){
				throw new RuntimeException("考试记录表插入数据异常");
			}
			Integer deptFlag = examUser.getDeptFlag();

			//ariticle.setDeptFlag(deptFlag);


			articleMapper.articleInsert(ariticle);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 修改方法 */
	public void update(Article ariticle){
		try {
			articleMapper.updateByPrimaryKeySelective(ariticle);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			articleMapper.deleteByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 批量删除 */
	public void deleteAll(Serializable[] ids){
		try{
		    //创建例子对象，目的就是为了in语句，给他表例子
			Example example = new Example(Dept.class);
			//创建条件对象
			Example.Criteria criteria = example.createCriteria();
			//封装条件
			criteria.andIn("id", Arrays.asList(ids));

			//将例子对象放到条件删除方法里
			articleMapper.deleteByExample(example);

		}catch (Exception ex){
            ex.printStackTrace();
		}
	}

	/** 根据主键id查询 */
	public Article findOne(Serializable id){
		try {
			//通用mapper提供crud方法，只要在依赖的pojo中配置jpa注解
			return articleMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}


	/** 多条件分页查询 */
	public PageResult findByPage(Article ariticle, int page, int rows){
		PageInfo<Article> pageInfo = PageHelper.
				startPage(page, rows).doSelectPageInfo(()->{
			articleMapper.findAll(ariticle);
		});

		return new PageResult(pageInfo.getPages(),pageInfo.getList());
	}

	@Override
	public List<Article> findAll() {
		try {
			List<Article> all = articleMapper.findAll(new Article());
			return all;
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public PageResult findByPaperId(Article ariticle, Integer curPage, Integer rows) {
		PageInfo<Article> pageInfo = PageHelper.
				startPage(curPage, rows).doSelectPageInfo(()->{
			articleMapper.findByPaperId(ariticle);
		});

		return new PageResult(pageInfo.getPages(),pageInfo.getList());
	}

	@Override
	public Article findById(Article article) {
		Article article1 = articleMapper.selectByPrimaryKey(article);
		return article1;
	}


}