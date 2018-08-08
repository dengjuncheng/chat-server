package com.peanut.controller;

import com.github.pagehelper.Page;
import com.peanut.pojo.User;
import com.peanut.pojo.vo.PostSimpleEntity;
import com.peanut.pojo.vo.PrivateLetterEntity;
import com.peanut.pojo.vo.ResponseData;
import com.peanut.service.PostService;
import com.peanut.service.PrivateLetterService;
import com.peanut.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private PostService postService;

    @Resource
    private PrivateLetterService privateLetterService;

    @RequestMapping(value = "center")
    public String showLogin(){
        return "login";
    }
    @RequestMapping(value = "fast_login")
    public String showFastLogin(){
        return "fastlogin";
    }
    @RequestMapping(value = "regist")
    public String showRegist(){
        return "regist";
    }

    @RequestMapping(value = "login")
    public String login(HttpServletRequest request, Model model){
        String userId = request.getParameter("username");
        String password = request.getParameter("password");
        if(userService.validateUser(userId, password, request.getSession())){
            return "forward:/post/main";//重定向到主页
        }
        model.addAttribute("username", userId);
        model.addAttribute("loginFail", "用户名不存在或密码错误");
        //用户信息不正确，返回到登录页面
        return "login";
    }
    @RequestMapping(value = "register")
    public String register(HttpServletRequest request, Model model){
        ResponseData data = userService.register(request);
        model.addAttribute("registerResult", data);
        if(data.getCode() == 0){
            return "login";
        }
        return "regist";
    }

    @RequestMapping(value = "exit")
    public String exit(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "login";
    }

    @RequestMapping(value="user")
    public String showUser(HttpServletRequest request,Model model){
        User u = (User) request.getSession().getAttribute("user");
        if(u == null){
            return "redirect:center";
        }
        Page<PostSimpleEntity> myPosts = (Page<PostSimpleEntity>) postService.getMyPostByUid(u.getId(),1,6);
        long myPostPageCount = myPosts.getTotal();
        Page<PrivateLetterEntity> myPrivateLetter = (Page<PrivateLetterEntity>) privateLetterService.getPrivateLetterByPageAndUserId(u.getId(),1,6);
        long myLetterCount = myPrivateLetter.getTotal();
        model.addAttribute("myPostPageCount",myPostPageCount);
        model.addAttribute("myLetterCount", myLetterCount);

        Map<String, ?> deleteMap = RequestContextUtils.getInputFlashMap(request);
        if (deleteMap != null) {
            if (deleteMap.get("deleteResult") != null) {
                model.addAttribute("deleteResult", deleteMap.get("deleteResult").toString());
            }
        }

        Map<String, ?> modifyMap = RequestContextUtils.getInputFlashMap(request);
        if(modifyMap != null){
            if(modifyMap.get("modifyResult") != null){
                model.addAttribute("modifyResult", modifyMap.get("modifyResult"));
            }
        }

        return "personal";
    }

    @RequestMapping(value = "other/{id}")
    public String showOther(@PathVariable("id") Integer id, Model model,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user != null && user.getId().equals(id)){
            return "forward:/user/user";
        }

        User u = userService.getUserById(id);
        if(u != null){
            model.addAttribute("otherUser", u);
            return "others";
        }
        return "errorpage";
    }

    @RequestMapping(value = "modify")
    public String modifyUser(HttpServletRequest request,RedirectAttributes model){
        ResponseData data = userService.updateUser(request);
        model.addFlashAttribute("modifyResult", data);
        return "redirect:/user/user";
    }
}
