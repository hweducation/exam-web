package com.icbc.controller;


import com.icbc.entity.PageResult;
import com.icbc.pojo.Paper;
import com.icbc.pojo.Question;
import com.icbc.service.PaperService;
import com.icbc.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/paper")
public class PaperController {


    @Autowired
    private PaperService paperService;

    //分页查询所有品牌
    @GetMapping("/findByPage")
    @ResponseBody
    public PageResult findByPage(Paper paper, Integer curPage, @RequestParam(defaultValue = "10") Integer rows){
        //如果时get请求就不用@RequestBody
        try {
            PageResult byPage = paperService.findByPage(paper, curPage, rows);
            return byPage;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    //添加品牌
    @PostMapping("/seve")
    public Boolean seve(@RequestBody Paper paper){
        try{
            paperService.save(paper);
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
            paperService.update(paper);
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
                paperService.deleteAll(ids);
                return true;
            }
            return false;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

}
