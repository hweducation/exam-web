package com.icbc.controller;


import com.icbc.common.PageResult;
import com.icbc.pojo.ExamUser;
import com.icbc.pojo.History;
import com.icbc.service.HistoryService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 历史考试controller
 */
@RestController
@RequestMapping("/history")
public class HistoryController {


    @Autowired
    private HistoryService historyService;


    @GetMapping("/findByPage")
    public PageResult findByPage(History history, Integer curPage, @RequestParam(defaultValue = "10") Integer rows){
        //如果时get请求就不用@RequestBody
        try {
            PageResult byPage = historyService.findByPage(history, curPage, rows);
            return byPage;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    //历史卷纸排行榜
    @GetMapping("/findByPaperId")
    public PageResult findByPaperId(History history, Integer curPage, @RequestParam(defaultValue = "10") Integer rows){
        //如果时get请求就不用@RequestBody
        try {
            PageResult byPage = historyService.findByPaperId(history, curPage, rows);

            return byPage;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }




    @PostMapping("/seve")
    public Boolean seve(@RequestBody History history){
        try{

            // 获取用户名
            ExamUser examUser = (ExamUser) (SecurityUtils.getSubject().getPrincipal());
            if(examUser!=null && examUser.getUsername()!=null){
                history.setUserId(examUser.getUsername());
            }

            history.setCreateDate(new Date());

            historyService.save(history);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @PostMapping("/update")
    public Boolean update(@RequestBody History history){
        try {
            historyService.update(history);
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
                historyService.deleteAll(ids);
                return true;
            }
            return false;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @GetMapping("/findAll")
    public List<History> findAll(){
        try {
            List<History> all = historyService.findAll();
            return all;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


}
