package com.icbc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icbc.entity.PageResult;
import com.icbc.mapper.PaperMapper;
import com.icbc.pojo.Paper;
import com.icbc.pojo.Question;
import com.icbc.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * QuestionServiceImpl 服务接口实现类
 * @date 2019-07-28 17:18:26
 * @version 1.0
 */
@Service
@Transactional
public class PaperServiceImpl implements PaperService {

	@Autowired
	private PaperMapper paperMapper;

	/** 添加方法 */
	public void save(Paper paper){
		try {
			paperMapper.insertSelective(paper);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 修改方法 */
	public void update(Paper paper){
		try {
			paperMapper.updateByPrimaryKeySelective(paper);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			paperMapper.deleteByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 批量删除 */
	public void deleteAll(Serializable[] ids){
		try{
		    //创建例子对象，目的就是为了in语句，给他表例子
			Example example = new Example(Paper.class);
			//创建条件对象
			Example.Criteria criteria = example.createCriteria();
			//封装条件
			criteria.andIn("id", Arrays.asList(ids));

			//将例子对象放到条件删除方法里
			paperMapper.deleteByExample(example);

		}catch (Exception ex){
            ex.printStackTrace();
		}
	}

	/** 根据主键id查询 */
	public Paper findOne(Serializable id){
		try {
			//通用mapper提供crud方法，只要在依赖的pojo中配置jpa注解
			return paperMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 查询全部 */
	public List<Paper> findAll(){
		try {
			return paperMapper.selectAll();
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 多条件分页查询 */
	public PageResult findByPage(Paper paper, int page, int rows){
		PageInfo<Question> pageInfo = PageHelper.
				startPage(page, rows).doSelectPageInfo(()->{
			paperMapper.findAll(paper);
		});

		return new PageResult(pageInfo.getPages(),pageInfo.getList());
	}




}