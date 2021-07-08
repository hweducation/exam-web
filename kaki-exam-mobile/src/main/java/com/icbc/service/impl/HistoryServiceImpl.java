package com.icbc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icbc.common.PageResult;
import com.icbc.mapper.DeptMapper;
import com.icbc.mapper.ExamUserMapper;
import com.icbc.mapper.HistoryMapper;
import com.icbc.pojo.Dept;
import com.icbc.pojo.ExamUser;
import com.icbc.pojo.History;
import com.icbc.service.DeptService;
import com.icbc.service.HistoryService;
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
public class HistoryServiceImpl implements HistoryService {

	@Autowired
	private HistoryMapper historyMapper;

	@Autowired
	private ExamUserMapper examUserMapper;

	/** 添加方法 */
	public void save(History history){
		try {

			ExamUser examUser = examUserMapper.selectByPrimaryKey(history.getUserId());

			if(examUser==null){
				throw new RuntimeException("考试记录表插入数据异常");
			}
			Integer deptFlag = examUser.getDeptFlag();

			history.setDeptFlag(deptFlag);


			historyMapper.historyInsert(history);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 修改方法 */
	public void update(History history){
		try {
			historyMapper.updateByPrimaryKeySelective(history);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			historyMapper.deleteByPrimaryKey(id);
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
			historyMapper.deleteByExample(example);

		}catch (Exception ex){
            ex.printStackTrace();
		}
	}

	/** 根据主键id查询 */
	public History findOne(Serializable id){
		try {
			//通用mapper提供crud方法，只要在依赖的pojo中配置jpa注解
			return historyMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}


	/** 多条件分页查询 */
	public PageResult findByPage(History history, int page, int rows){
		PageInfo<History> pageInfo = PageHelper.
				startPage(page, rows).doSelectPageInfo(()->{
			historyMapper.findAll(history);
		});

		return new PageResult(pageInfo.getPages(),pageInfo.getList());
	}

	@Override
	public List<History> findAll() {
		try {
			List<History> all = historyMapper.findAll(new History());
			return all;
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public PageResult findByPaperId(History history, Integer curPage, Integer rows) {
		PageInfo<History> pageInfo = PageHelper.
				startPage(curPage, rows).doSelectPageInfo(()->{
			historyMapper.findByPaperId(history);
		});

		return new PageResult(pageInfo.getPages(),pageInfo.getList());
	}


}