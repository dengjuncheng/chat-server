package com.peanut.controller;

import com.peanut.pojo.vo.ResponseData;
import com.peanut.service.PicUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Controller
@RequestMapping("/pic")
public class UploadController {
    @Resource
    private PicUploadService picUploadService;

    @RequestMapping(value = "uploadPhoto" ,method = RequestMethod.POST)
    public void headPhote(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile pic){
        ObjectMapper mapper = new ObjectMapper();
        ResponseData responseData =picUploadService.getUploadHeadPicResponse(pic);
        //System.out.println("接受到文件");
        PrintWriter pw =null;
        try {
            String fileName =  mapper.writeValueAsString(responseData);
            response.setCharacterEncoding("UTF-8");
            pw = response.getWriter();
            pw.print(fileName);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            pw.close();
        }
    }

    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public void  uploadFiles(HttpServletResponse response, HttpServletRequest request) throws IOException {

    }

}
