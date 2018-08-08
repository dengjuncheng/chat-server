package com.peanut.controller;

import com.github.pagehelper.Page;
import com.peanut.constant.Constants;
import com.peanut.pojo.User;
import com.peanut.pojo.vo.PrivateLetterEntity;
import com.peanut.pojo.vo.ResponseData;
import com.peanut.service.PrivateLetterService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/letter")
public class PrivateLetterController {
    @Resource
    private PrivateLetterService privateLetterService;
    private static final Integer PAGE_SIZE = 6;
    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value="myLetter")
    public void getMyLetter(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        String pageNum = request.getParameter("currentPage");
        //System.out.println(pageNum);
        if(user == null){
            return;
        }
        Page<PrivateLetterEntity> myLetters = (Page<PrivateLetterEntity>) privateLetterService.getPrivateLetterByPageAndUserId(user.getId(),Integer.parseInt(pageNum),PAGE_SIZE );
       // System.out.println(myLetters.getPages());
        PrintWriter pw = null;
        try {
            response.setCharacterEncoding("UTF-8");  //编码格式必须设置在getWritter之前，否则无法生效
            response.setHeader("Charset","UTF-8");
            pw = response.getWriter();
            pw.write(objectMapper.writeValueAsString(myLetters));
            //System.out.println(objectMapper.writeValueAsString(myLetters));
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            pw.close();
        }
    }

    @RequestMapping(value = "delete/{id}")
    public String deleteLetter(@PathVariable("id") Integer id, RedirectAttributes model){
        if(id==null){
            return "redirect:/user/user";
        }

        if(privateLetterService.deleteById(id)){
            model.addFlashAttribute("deleteResult","success");

        }else{
            model.addFlashAttribute("deleteResult","fail");
        }

        return "redirect:/user/user";
    }

    @RequestMapping(value = "reply")
    public void reply(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return;
        }
        String toId = request.getParameter("toId");
        String content = request.getParameter("msg");
        if(StringUtils.isEmpty(toId) || StringUtils.isEmpty(content)){
            return;
        }
        Boolean result = privateLetterService.insert(user.getId(), toId, content);
        PrintWriter pw = null;
        try {
            response.setCharacterEncoding("UTF-8");  //编码格式必须设置在getWritter之前，否则无法生效
            response.setHeader("Charset","UTF-8");
            pw = response.getWriter();
            if(result) {
                pw.write("{\"result\":\"success\"}");
            }else {
                pw.write("{\"result\":\"fail\"}");
            }
            //System.out.println(objectMapper.writeValueAsString(myLetters));
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            pw.close();
        }
    }

    @RequestMapping(value = "msg")
    public void sendMsg(HttpServletRequest request, HttpServletResponse response)throws IOException{
        ResponseData data = privateLetterService.senMessage(request);
        PrintWriter pw = null;
        response.setCharacterEncoding("UTF-8");  //编码格式必须设置在getWritter之前，否则无法生效
        response.setHeader("Charset","UTF-8");
        pw = response.getWriter();
        pw.write(objectMapper.writeValueAsString(data));
        pw.flush();
        pw.close();
    }
}
