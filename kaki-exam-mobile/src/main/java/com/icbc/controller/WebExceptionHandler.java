package com.icbc.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kaki Nakajima
 * @desc 统一异常处理类
 */
@ControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String,Object> error(Exception e){
        e.printStackTrace();
        HashMap<String, Object> resMap = new HashMap<>(0);
        resMap.put("flag", false);
        resMap.put("msg", e.getMessage());
        resMap.put("code","-1");
        return resMap;
    }
}
