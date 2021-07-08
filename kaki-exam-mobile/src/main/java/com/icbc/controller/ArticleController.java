package com.icbc.controller;


import com.icbc.common.PageResult;
import com.icbc.pojo.Article;
import com.icbc.pojo.ExamUser;
import com.icbc.service.ArticleService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学习资料controller
 * @date 2020-04-12 13:53:36
 * @author koji kigawa
 * @version 2.0
 */
@RestController
@RequestMapping("/article")
public class ArticleController {


    @Autowired
    private ArticleService articleService;


    @GetMapping("/findByPage")
    public PageResult findByPage(Article article, Integer curPage, @RequestParam(defaultValue = "10") Integer rows){
        //如果时get请求就不用@RequestBody
        try {
            PageResult byPage = articleService.findByPage(article, curPage, rows);
            return byPage;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @GetMapping("/findById")
    public Map<String,Object> findById( Article article){
        //如果时get请求就不用@RequestBody
        Map<String, Object> map = new HashMap<>();
        try {
            Article data = articleService.findById(article);
            map.put("data",data);
            map.put("code",0);
            map.put("msg","查询成功");
            return map;
        }catch (Exception ex){
            ex.printStackTrace();
            map.put("data",null);
            map.put("code",-1);
            map.put("msg","查询失败，msg:"+ex.getMessage());
            return map;
        }
    }

    @PostMapping("/seve")
    public Boolean seve(@RequestBody Article article){
        try{

            // 获取用户名
            ExamUser examUser = (ExamUser) (SecurityUtils.getSubject().getPrincipal());
            if(examUser!=null && examUser.getUsername()!=null){
                article.setUserId(examUser.getUsername());
            }

            //article.setCreateDate(new Date());

            articleService.save(article);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @PostMapping("/update")
    public Boolean update(@RequestBody Article article){
        try {
            articleService.update(article);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }


    @PostMapping("/delete")
    public Boolean delete(@RequestBody Map<String,Long[]> map){
        try{
            if(map !=null && map.size()>0){
                Long[] ids = map.get("ids");
                articleService.deleteAll(ids);
                return true;
            }
            return false;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @GetMapping("/findAll")
    public List<Article> findAll(){
        try {
            List<Article> all = articleService.findAll();
            return all;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


}
