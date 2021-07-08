package com.icbc.mapper;

import com.icbc.pojo.Article;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * ArticleMapper 数据访问接口
 * @date 2020-04-12 13:53:36
 * @author koji kigawa
 * @version 2.0
 */

public interface ArticleMapper extends Mapper<Article>{

    //条件查找
    List<Article> findAll(Article article);

    //插表
    void articleInsert(Article article);

    List<Article> findByPaperId(Article article);
}