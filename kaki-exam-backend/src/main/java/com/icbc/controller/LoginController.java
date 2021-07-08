package com.icbc.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

//用户登录controller
@Controller
@RequestMapping("/user")
public class LoginController {

    @PostMapping("/login")
    public String login(String username,String password){
        try{
          //获得认证主体
            Subject subject = SecurityUtils.getSubject();

            //创建令牌对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);

            //进行认证
            subject.login(token);

            //判断是否通过
            if (subject.isAuthenticated()){
                return "redirect:/admin/index.html";
            }
        }catch (Exception ex){
            ex.printStackTrace();

        }

        return "redirect:/login.html";
    }

    //显示用户名
    @GetMapping("/findLongName")
    @ResponseBody
    public Map<String,Object> findLongName(){
        //获得登入的用户名
        String loginName = SecurityUtils.getSubject().getPrincipal().toString();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("loginName", loginName);

        return hashMap;
    }
}
