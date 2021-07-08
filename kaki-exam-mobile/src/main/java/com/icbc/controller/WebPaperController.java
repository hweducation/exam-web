package com.icbc.controller;



import com.icbc.common.PageResult;
import com.icbc.pojo.ExamUser;
import com.icbc.pojo.Paper;
import com.icbc.pojo.Question;
import com.icbc.service.WebPaperService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 前台试卷控制器
 */
@RestController
@RequestMapping("/webpaper")
public class WebPaperController {


    @Autowired
    private WebPaperService webPaperService;

    @GetMapping("/findByPage")
    @ResponseBody
    public PageResult findByPage(Paper paper, Integer curPage, @RequestParam(defaultValue = "10") Integer rows){
        //如果时get请求就不用@RequestBody
        try {
            ExamUser user = (ExamUser)(SecurityUtils.getSubject().getPrincipal());
            paper.setUsername(user.getUsername());
            PageResult byPage = webPaperService.findByPage(paper, curPage, rows);
            return byPage;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    //根据试卷id查询所有试卷题目
    @GetMapping("/findByPaperId")
    @ResponseBody
    public List<Question> findByPaperId(Integer paperId){
        //如果时get请求就不用@RequestBody
        try {
            List<Question> list = webPaperService.findByPaperId(paperId);
            return list;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    @PostMapping("/seve")
    public Boolean seve(@RequestBody Paper paper){
        try{
            webPaperService.save(paper);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @PostMapping("/update")
    public Boolean update(@RequestBody Paper paper){
        try {
            webPaperService.update(paper);
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
                webPaperService.deleteAll(ids);
                return true;
            }
            return false;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

}
