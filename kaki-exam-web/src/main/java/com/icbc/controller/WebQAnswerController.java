package com.icbc.controller;
/*
import com.icbc.common.PageResult;
import com.icbc.pojo.ExamUser;
import com.icbc.pojo.Paper;
import com.icbc.pojo.Question;
import com.icbc.service.WebPaperService;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
*/
import com.icbc.pojo.ExamUser;
import com.icbc.service.QAnswerService;
import com.icbc.utils.IdWorker;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icbc.pojo.QAnswer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.io.*;

@RestController
//@RequestMapping("/api")
public class WebQAnswerController {
    //String file_save_path = "/audio/";
    @Autowired
    QAnswerService qAnswerService;
    @Value("${audioFileUplaodAddress}")
    private String audioFileUplaodAddress;

    @Value("${audioFinedir}")
    private String audioFinedir;

    @PostMapping("/webqanswer/upload")
    public Map<String,Object> upload(@RequestParam("audio_file") MultipartFile multipartFile,@RequestParam("filename") String filename,@RequestParam("id") String id){//  ("audio_file")

        System.out.println("upload函数执行！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
        FileOutputStream out = null;
        InputStream in = null;

        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", 500);

        try {
            ExamUser examUser = (ExamUser) (SecurityUtils.getSubject().getPrincipal());
            String username =examUser.getUsername();
            //获取去文件字节
            byte[] bytes = multipartFile.getBytes();
//            Date dNow = new Date();
//            SimpleDateFormat ft = new SimpleDateFormat ("yyyy:MM:dd:hh:mm:ss");
            String fileName= new IdWorker().nextId()+"-"+new Date().getTime()+ "-"+username + "-" + filename;
            //组合文件地址,存入数据库
            String fileUrl = audioFileUplaodAddress +fileName;//newFileName
            //随机id-ms-username-questionIndex-paperid-'recording'.wav
            System.out.println(fileUrl);
            //文件实际存储地址
            String localFile = audioFinedir + fileName;//newFileName
            System.out.println(localFile);

            //IO读写操作
            out = new FileOutputStream(localFile);

            byte[] buffer = new byte[1024];

            int len = -1;

            in = multipartFile.getInputStream();

            while ((len= in.read(buffer))>0){
                out.write(buffer,0,len);
            }

            in.close();
            out.close();

            //封装返回参数
            reqMap.put("status", 200);
            reqMap.put("url",fileUrl);
            reqMap.put("id",id);

        }catch (Exception e){
            e.printStackTrace();
            return reqMap;
        }finally {
            try {
                if(in!=null)in.close();
                if(out!=null)out.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return reqMap;
    }

    @RequestMapping("/webqanswer/save")
    public Boolean save(@RequestBody QAnswer qAnswer){
        System.out.println(qAnswer.toString());
        try{
            // 获取用户名
            ExamUser examUser = (ExamUser) (SecurityUtils.getSubject().getPrincipal());
            if(examUser!=null && examUser.getUsername()!=null){
                qAnswer.setUid(examUser.getUsername());
            }

//            qAnswer.setStart_time(new Date());

            qAnswerService.save(qAnswer);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

}
