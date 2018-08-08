package com.peanut.controller;

import com.peanut.constant.Constants;
import com.peanut.pojo.User;
import com.peanut.pojo.vo.CommentSimpleEntity;
import com.peanut.pojo.vo.ResponseData;
import com.peanut.service.CommentService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    private static final Integer PAGE_SIZE = 5;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "page")
    public void getComment(HttpServletRequest request, HttpServletResponse response){
        Integer pageNum = Integer.parseInt(request.getParameter("currentPage"));
        Integer postId = Integer.parseInt(request.getParameter("postId"));

        List<CommentSimpleEntity> comments = commentService.getCommentWithReply(postId, PAGE_SIZE, pageNum);
        //comments.forEach(System.out::println);
        PrintWriter pw = null;
        try {
            response.setCharacterEncoding("UTF-8");  //编码格式必须设置在getWritter之前，否则无法生效
            response.setHeader("Charset","UTF-8");
            pw = response.getWriter();
            pw.write(objectMapper.writeValueAsString(comments));
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            pw.close();
        }
    }

    @RequestMapping(value = "reply")
    public void reply(HttpServletResponse response, HttpServletRequest request) {
        ResponseData data= commentService.dealWithReply(request);
        PrintWriter pw = null;
        try{
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Charset","UTF-8");
            pw = response.getWriter();
            pw.write(objectMapper.writeValueAsString(data));
            pw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "new_comment")
    public String newComment(HttpServletRequest request, RedirectAttributes model){

        ResponseData data = commentService.addComment(request);
        model.addFlashAttribute("commentResult", data);
        return "redirect:/post/post/" + data.getSrc();
    }
}
