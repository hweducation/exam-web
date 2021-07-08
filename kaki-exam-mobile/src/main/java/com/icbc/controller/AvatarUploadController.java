package com.icbc.controller;

import com.icbc.utils.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kaki Nakajima
 * @desc 头像上传控制器
 */
@RestController
public class AvatarUploadController {

    @Value("${fileUplaodAddress}")
    private String fileUplaodAddress;

    @Value("${finedir}")
    private String finedir;

    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file")MultipartFile multipartFile){
        FileOutputStream out = null;
        InputStream in = null;

        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", 500);

        try {
            //获取去文件字节
            byte[] bytes = multipartFile.getBytes();

            //获取源文件名
            String originalFilename = multipartFile.getOriginalFilename();

            //获取后缀
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

            //组装新文件名
            String newFileName = new IdWorker().nextId() +"" +suffix;

            if (!((".jpg".equalsIgnoreCase(suffix)) || (".png".equalsIgnoreCase(suffix)))){
                throw new RuntimeException("头像格式只能是jpg或者png");
            }

            if (multipartFile.getSize()>2097152){
                throw new RuntimeException("头像大小限制在2M");
            }

            //组合文件地址
            String fileUrl = fileUplaodAddress+newFileName;

            //文件实际存储地址
            String localFile = finedir + newFileName;


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

}
