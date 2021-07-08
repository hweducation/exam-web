package com.icbc.service;

import com.icbc.pojo.ExamUser;

import java.io.Serializable;
import java.util.Map;

/**
 * 用户服务接口
 * @author kaki
 */
public interface ExamUserService {


    /**
     * 保存用户
     * @param examUser
     */
    void save(ExamUser examUser);

    /**
     * 修改用户
     * @param examUser
     */
    void update(ExamUser examUser);

    /**
     * 根据用户id查询其基本信息
     * @param id
     * @return
     */
    ExamUser findOne(Serializable id);

    /**
     * 密码修改
     * @param entity
     * @return
     */
    Map<String, Object> sevePassword(Map<String, Object> entity);
}
