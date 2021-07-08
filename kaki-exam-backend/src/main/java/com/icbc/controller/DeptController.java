package com.icbc.controller;


import com.icbc.entity.PageResult;
import com.icbc.pojo.Dept;
import com.icbc.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dept")
public class DeptController {


    @Autowired
    private DeptService deptService;


    @GetMapping("/findByPage")
    public PageResult findByPage(String deptName, Integer curPage, @RequestParam(defaultValue = "10") Integer rows){
        //如果时get请求就不用@RequestBody
        try {
            Dept dept = new Dept();
            dept.setDeptName(deptName);
            PageResult byPage = deptService.findByPage(dept, curPage, rows);
            return byPage;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @PostMapping("/seve")
    public Boolean seve(@RequestBody Dept dept){
        try{
            deptService.save(dept);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @PostMapping("/update")
    public Boolean update(@RequestBody Dept dept){
        try {
            deptService.update(dept);
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
                deptService.deleteAll(ids);
                return true;
            }
            return false;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @GetMapping("/findAll")
    public List<Dept> findAll(){
        try {
            List<Dept> all = deptService.findAll();
            return all;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


}
