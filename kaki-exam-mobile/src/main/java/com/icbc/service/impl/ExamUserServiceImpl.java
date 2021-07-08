package com.icbc.service.impl;


import com.icbc.mapper.ExamUserMapper;
import com.icbc.pojo.Dept;
import com.icbc.pojo.ExamUser;
import com.icbc.service.ExamUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * QuestionServiceImpl 服务接口实现类
 * @date 2019-07-28 17:18:26
 * @version 1.0
 */
@Service
@Transactional
public class ExamUserServiceImpl implements ExamUserService {

	@Autowired
	private ExamUserMapper examUserMapper;

	/** 添加方法 */
	public void save(ExamUser examUser){
		try {
			examUserMapper.insertSelective(examUser);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 修改方法 */
	public void update(ExamUser examUser){
		try {
			examUserMapper.updateByPrimaryKeySelective(examUser);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			examUserMapper.deleteByPrimaryKey(id);
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
			examUserMapper.deleteByExample(example);

		}catch (Exception ex){
            ex.printStackTrace();
		}
	}

	/** 根据主键id查询 */
	public ExamUser findOne(Serializable id){
		try {
			//通用mapper提供crud方法，只要在依赖的pojo中配置jpa注解
			return examUserMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 密码修改
	 * @param entity
	 * @return
	 */
	@Override
	public Map<String, Object> sevePassword(Map<String, Object> entity) {
		String oldPassword = (String)entity.get("oldPassword");
		String newPassword = (String)entity.get("newPassword");
		String username = (String)entity.get("username");
		HashMap<String, Object> map = new HashMap<>();

		if(authorizing(username, oldPassword)){
			String password = new SimpleHash("md5", newPassword, username, 5).toHex();
			ExamUser examUser = new ExamUser();
			examUser.setUsername(username);
			examUser.setPassword(password);

			try {
				//examUserMapper.updateByPrimaryKey(examUser);
				examUserMapper.updateByPrimaryKeySelective(examUser);
				map.put("flag", true);
				map.put("msg", "修改成功");
				map.put("code", "0");
				return map;
			}catch (Exception e){
				e.printStackTrace();
				map.put("flag", false);
				map.put("msg", e.getMessage());
				map.put("code", "-1");
				return map;
			}

		}else{
			map.put("flag", false);
			map.put("msg", "密码错误");
			map.put("code", "-1");
			return map;
		}
	}


	//密码认证
	private boolean authorizing(String username,String password){
		// 获取认证主体对象
		Subject subject = SecurityUtils.getSubject();

		// 创建用户名密码令牌对象
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		// 身份认证
		subject.login(token);

		return subject.isAuthenticated();
	}


	/** 多条件分页查询
	public PageResult findByPage(Dept dept, int page, int rows){
		PageInfo<Dept> pageInfo = PageHelper.
				startPage(page, rows).doSelectPageInfo(()->{
			examUserMapper.findAll(dept);
		});

		return new PageResult(pageInfo.getPages(),pageInfo.getList());
	}*/



}