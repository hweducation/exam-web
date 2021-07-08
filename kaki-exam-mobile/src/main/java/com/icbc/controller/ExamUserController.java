package com.icbc.controller;

import com.icbc.pojo.ExamUser;
import com.icbc.service.ExamUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author kaki
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/examuser")
public class ExamUserController {


    @Autowired
    private ExamUserService examUserService;

    /** 用户注册 */
    @PostMapping("/save")
    public Map<String,Object> save(@RequestBody ExamUser examUser){
        Map<String, Object> map = new HashMap<>(0);
        try {
            map.put("flag", true);

            //先判断用户名是否存在
            ExamUser user = examUserService.findOne(examUser.getUsername());
            if(user!=null){
                map.put("flag", false);
                map.put("msg", "该用户名已被注册");
                return map;
            }

            /**
             * 加密、加盐、加迭代次数
             * String algorithmName: 加密算法
             * Object source: 明文
             * Object salt：盐
             * int hashIterations: 迭代次数
             */
            String password = new SimpleHash("md5", examUser.getPassword(), examUser.getUsername(), 5).toHex();
            examUser.setPassword(password);
            examUserService.save(examUser);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("flag", false);
            map.put("msg", e.getMessage());
            return map;
        }
    }

    /** 用户注册 */
    @PostMapping("/update")
    public Map<String,Object> update(@RequestBody ExamUser examUser){
        Map<String, Object> map = new HashMap<>(0);
        try {
            examUserService.update(examUser);
            map.put("flag", true);
            map.put("msg", "保存成功");
            map.put("code", "0");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("flag", false);
            map.put("msg", e.getMessage());
            map.put("code", "-1");
            return map;
        }
    }

    /*密码修改*/
    @PostMapping("/sevePassword")
    public Map<String,Object> sevePassword(@RequestBody Map<String,Object> entity){

        Map<String,Object> map = examUserService.sevePassword(entity);

        return map;
    }
}
