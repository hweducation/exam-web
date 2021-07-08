package com.icbc.controller;

import com.icbc.pojo.ExamUser;
import com.icbc.service.ExamUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author kaki_nakajima
 * @version 1.0
 * <p>File Created at 2020-01-25<p>
 */
@Controller
@RequestMapping("/web")
public class WebLoginController {

    @Autowired
    private ExamUserService userService;


    /** 登录方法 */
    @PostMapping("/loginweb")
    public String login(String username, String password){
        try{
            // 获取认证主体对象
            Subject subject = SecurityUtils.getSubject();

            //String passwordMd5 = new SimpleHash("md5", password, password, 5).toHex();
            // 创建用户名密码令牌对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);

            // 身份认证
            subject.login(token);

            // 判断是否登录成功
            if (subject.isAuthenticated()){
                // 登录成功，重定向到后台主页面
                return "redirect:/admin/index.html";
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        // 登录失败，重定向到登录页面
        return "redirect:/login.html";
    }


    /** 获取登录用户名 */
    @GetMapping("/findLoginName")
    @ResponseBody
    public Map<String,Object> findLoginName(){
        Map<String,Object> data = new HashMap<>();
        // 获取用户名
        ExamUser examUser = (ExamUser) (SecurityUtils.getSubject().getPrincipal());

        if (StringUtils.isNotBlank(examUser.getUsername())){
            data.put("loginName", examUser.getUsername());
            //获取登录用户信息
            data.put("user",userService.findOne(examUser.getUsername()));
        }

        return data;
    }

}
