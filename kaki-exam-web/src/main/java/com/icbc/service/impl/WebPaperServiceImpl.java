package com.icbc.service.impl;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.icbc.common.PageResult;
import com.icbc.mapper.WebPaperMapper;
import com.icbc.pojo.Paper;

import com.icbc.pojo.Question;
import com.icbc.service.WebPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * QuestionServiceImpl 服务接口实现类
 * @date 2019-07-28 17:18:26
 * @version 1.0
 */
@Service
@Transactional
public class WebPaperServiceImpl implements WebPaperService {

	@Autowired
	private WebPaperMapper webPaperMapper;

	/** 添加方法 */
	public void save(Paper paper){
		try {
			webPaperMapper.insertSelective(paper);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 修改方法 */
	public void update(Paper paper){
		try {
			webPaperMapper.updateByPrimaryKeySelective(paper);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			webPaperMapper.deleteByPrimaryKey(id);
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
			webPaperMapper.deleteByExample(example);

		}catch (Exception ex){
            ex.printStackTrace();
		}
	}

	/** 根据主键id查询 */
	public Paper findOne(Serializable id){
		try {
			//通用mapper提供crud方法，只要在依赖的pojo中配置jpa注解
			return webPaperMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 查询全部 */
	public List<Paper> findAll(){
		try {
			return webPaperMapper.selectAll();
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 多条件分页查询 */
	public PageResult findByPage(Paper paper, int page, int rows){
		PageInfo<Question> pageInfo = PageHelper.
				startPage(page, rows).doSelectPageInfo(()->{
			webPaperMapper.findAll(paper);
		});

		return new PageResult(pageInfo.getPages(),pageInfo.getList());
	}


    /**
     * 根据试卷id查询所有试卷题目
     * @param findByPaperId
     * @return
     */
    @Override
    public List<Question> findByPaperId(Integer findByPaperId){
        try {
            List<Question> list=  webPaperMapper.findByPaperId(findByPaperId);
			for (Question question1 : list) {
				//将字符串格式的map，转成map格式
				HashMap hashMap = JSON.parseObject(question1.getOptionMapString(), HashMap.class);
				question1.setOptionMap(hashMap);
			}
           return list;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}