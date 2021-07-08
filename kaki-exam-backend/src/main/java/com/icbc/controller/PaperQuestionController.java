package com.icbc.controller;


import com.icbc.entity.PageResult;
import com.icbc.pojo.Paper;
import com.icbc.service.PaperQuestionService;
import com.icbc.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 卷纸与题目的中间表
 */
@RestController
@RequestMapping("/paperQuestion")
public class PaperQuestionController {


    @Autowired
    private PaperQuestionService paperQuestionService;

    //分页查询所有品牌
    @GetMapping("/findByPage")
    @ResponseBody
    public PageResult findByPage(Paper paper, Integer curPage, @RequestParam(defaultValue = "10") Integer rows){
        //如果时get请求就不用@RequestBody
        try {
            PageResult byPage = paperQuestionService.findByPage(paper, curPage, rows);
            return byPage;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    //跟据卷纸id查询题目id（一对多）
    @GetMapping("/findByPaperId")
    @ResponseBody
    public List<Integer> findByPaperId(Integer id){

        try {
            List<Integer> list= paperQuestionService.findByPaperId(id);
            return list;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    @PostMapping("/seve")
    public Boolean seve(@RequestBody Map<String,Object> map){
        try{
            paperQuestionService.save(map);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    //修改品牌数据
    @PostMapping("/update")
    public Boolean update(@RequestBody Paper paper){
        try {
            paperQuestionService.update(paper);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    //删除页面商品数据
    @PostMapping("/delete")
    public Boolean delete(@RequestBody Map<String,Long[]> map){
        try{
            if(map !=null && map.size()>0){
                Long[] ids = map.get("ids");
                paperQuestionService.deleteAll(ids);
                return true;
            }
            return false;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

}
