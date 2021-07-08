package com.icbc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.icbc.common.PageResult;
import com.icbc.mapper.DeptMapper;
import com.icbc.pojo.Dept;
import com.icbc.service.DeptService;
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
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptMapper deptMapper;

	/** 添加方法 */
	public void save(Dept dept){
		try {
			deptMapper.insertSelective(dept);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 修改方法 */
	public void update(Dept dept){
		try {
			deptMapper.updateByPrimaryKeySelective(dept);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			deptMapper.deleteByPrimaryKey(id);
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
			deptMapper.deleteByExample(example);

		}catch (Exception ex){
            ex.printStackTrace();
		}
	}

	/** 根据主键id查询 */
	public Dept findOne(Serializable id){
		try {
			//通用mapper提供crud方法，只要在依赖的pojo中配置jpa注解
			return deptMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}


	/** 多条件分页查询 */
	public PageResult findByPage(Dept dept, int page, int rows){
		PageInfo<Dept> pageInfo = PageHelper.
				startPage(page, rows).doSelectPageInfo(()->{
			deptMapper.findAll(dept);
		});

		return new PageResult(pageInfo.getPages(),pageInfo.getList());
	}

	@Override
	public List<Dept> findAll() {
		try {
			List<Dept> all = deptMapper.findAll(new Dept());
			return all;
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}


}