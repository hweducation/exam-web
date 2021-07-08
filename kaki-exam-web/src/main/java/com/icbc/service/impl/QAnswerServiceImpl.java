package com.icbc.service.impl;

import com.icbc.mapper.QAnswerMapper;
import com.icbc.mapper.WebPaperMapper;
import com.icbc.pojo.QAnswer;
import com.icbc.service.QAnswerService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class QAnswerServiceImpl implements QAnswerService {
    @Autowired
    private QAnswerMapper qAnswerMapper;

    /** 添加方法 */
    public void save(QAnswer qAnswer){
        try {
            qAnswerMapper.save(qAnswer);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
