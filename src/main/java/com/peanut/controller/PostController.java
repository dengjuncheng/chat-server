package com.peanut.controller;

import com.github.pagehelper.Page;
import com.peanut.constant.Constants;
import com.peanut.pojo.User;
import com.peanut.pojo.vo.CommentSimpleEntity;
import com.peanut.pojo.vo.PostCommentRel;
import com.peanut.pojo.vo.PostSimpleEntity;
import com.peanut.pojo.vo.ResponseData;
import com.peanut.service.CommentService;
import com.peanut.service.PostService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/post")
public class PostController {
    @Resource
    private PostService postService;

    @Resource
    private CommentService commentService;

    private static final Integer PAGE_SIZE = 5;
    private static final Integer MAIN_PAGE_SIZE=6;
    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("test")
    public void test(){
        //postService.getPostSimpleList(1,2);
        System.out.println("_______________test");
        //page继承ArrayList,实际getPostSimpleList返回的是一个Page对象，只是用父类引用指向子类对象。Page重写toString方法，并且添加了其他一些字段
        Page<PostSimpleEntity> list= (Page<PostSimpleEntity>) postService.getPostSimpleList(1,2);
        System.out.println(list.getPages());
        //System.out.println(list);
//        list.forEach(entity -> System.out.println(entity));
    }
    //文章列表接口
    @RequestMapping(value = "post_list/{pageCount}")
    public String getPostSimpleList(@PathVariable("pageCount") Integer pageCount, Model model){
        Page<PostSimpleEntity> postList= (Page<PostSimpleEntity>)postService.getPostSimpleList(pageCount, PAGE_SIZE);
        List<PostSimpleEntity> topList = postService.getTopRankList(PAGE_SIZE);
        List<PostCommentRel> postCommentRels = commentService.getTopComment(PAGE_SIZE);//最近评论
        model.addAttribute("pageNum", postList.getPageNum());
        model.addAttribute("pageTotal", postList.getPages());
        model.addAttribute("postList", postList);
        model.addAttribute("topList", topList);
        model.addAttribute("commentList", postCommentRels);
        return "articles-list";
    }

    //定义无参请求Controller方法，防止出错
    @RequestMapping(value = "post_list")
    public String getPostSimpleList(RedirectAttributes attr){
        attr.addFlashAttribute(1);
        return "redirect:post_list/1";  //重定向到参数为1的方法。
    }
    //主页接口
    @RequestMapping(value = "main")
    public String getMainPage(Model model){
        List<PostSimpleEntity> topList = postService.getTopRankList(MAIN_PAGE_SIZE);
        List<PostSimpleEntity> recentList = postService.getRecentRankList(MAIN_PAGE_SIZE);

        model.addAttribute("topList",topList);
        model.addAttribute("recentList",recentList);
        return "main";
    }
    //排行榜接口
    @RequestMapping(value = "rank")
    public String getRankPage(Model model){
        List<PostSimpleEntity> topList = postService.getTopRankList(MAIN_PAGE_SIZE);
        List<PostSimpleEntity> browserList = postService.getBrowseRankList(MAIN_PAGE_SIZE);
        List<PostSimpleEntity> likeList = postService.getLikeRankList(MAIN_PAGE_SIZE);
        List<PostSimpleEntity> commentList = postService.getCommentRankList(MAIN_PAGE_SIZE);

        model.addAttribute("topList",topList);
        model.addAttribute("browserList",browserList);
        model.addAttribute("likeList",likeList);
        model.addAttribute("commentList",commentList);
        return "rank";
    }
    //置顶文章列表接口
    @RequestMapping(value = "top/{pageCount}")
    public String getTopPage(Model model, @PathVariable(value ="pageCount") Integer pageCount){
        Page<PostSimpleEntity> topList= (Page<PostSimpleEntity>)postService.getTopList(pageCount, PAGE_SIZE);
        List<PostSimpleEntity> recentList = postService.getPostSimpleList(1,MAIN_PAGE_SIZE);
        List<PostCommentRel> commentList = commentService.getTopComment(MAIN_PAGE_SIZE);

        model.addAttribute("topList", topList);
        model.addAttribute("pageNum", topList.getPageNum());
        model.addAttribute("pageTotal", topList.getPages());
        model.addAttribute("recentList", recentList);
        model.addAttribute("commentList", commentList);
        return "top";
    }
    //重定向置顶文章接口，防止出错
    @RequestMapping(value = "top")
    public String getTopPage(){
        return "redirect:top/1";  //重定向到参数为1的方法。
    }
    //文章详情接口
    @RequestMapping(value="post/{id}")
    public String getSinglePage(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        postService.increaseViewNum(id);
        PostSimpleEntity post = postService.getPostById(id);
        List<PostSimpleEntity> recentList = postService.getPostSimpleList(1,MAIN_PAGE_SIZE);
        List<PostCommentRel> commentList = commentService.getTopComment(MAIN_PAGE_SIZE);
        Page<CommentSimpleEntity> commentSimpleEntities = (Page<CommentSimpleEntity>) commentService.getCommentByPostId(id,PAGE_SIZE,1);
        long commentPageCount = commentSimpleEntities.getTotal();
        model.addAttribute("commentPageCount", commentPageCount);
        model.addAttribute("commentList", commentList);
        model.addAttribute("recentList", recentList);
        model.addAttribute("post", post);

        Map<String, ?> resultMap = RequestContextUtils.getInputFlashMap(request);
        if(resultMap != null){
            model.addAttribute("commentResult", (ResponseData)resultMap.get("commentResult"));
        }
        return "single";
    }
    //创建文章接口
    @RequestMapping(value = "new_post")
    String showNewPost(){
        return "new-post";
    }
    //获取用户已发布文章
    @RequestMapping(value = "myposts",produces = "text/json;charset=UTF-8")
    public void getMyPosts(HttpServletResponse response, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        String pageNum = request.getParameter("currentPage");
        if(user == null){
            return;
        }
        Page<PostSimpleEntity> myPosts = (Page<PostSimpleEntity>) postService.getMyPostByUid(user.getId(),Integer.parseInt(pageNum),MAIN_PAGE_SIZE);
        PrintWriter pw = null;
        try {
            response.setCharacterEncoding("UTF-8");  //编码格式必须设置在getWritter之前，否则无法生效
            response.setHeader("Charset","UTF-8");
            pw = response.getWriter();
            //response.setContentType("text/json;charset=UTF-8");
            pw.write(objectMapper.writeValueAsString(myPosts));
            //System.out.println(objectMapper.writeValueAsString(myPosts));
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            pw.close();
        }
    }
    //发布文章接口
    @RequestMapping(value = "publish")
    public void publishPost(HttpServletRequest request, HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        String content = request.getParameter("content");
        String title = request.getParameter("title");

        //System.out.println(content);
        Integer postId = postService.insert(user,content,title);

        ResponseData data = new ResponseData();
        if(postId == 0){
            data.setCode(1);
            data.setMsg(Constants.PUBLISH_POST_ERROR);
        }else if(postId == -1){
            data.setCode(-1);
            data.setMsg(Constants.USER_NOT_LOGGED_IN_ON_PUBLISH);
        }else if(postId == -2){
            data.setCode(-2);
            data.setMsg(Constants.CONTENT_EMPTY_ERROR);
        }else if(postId == -3){
            data.setCode(-3);
            data.setMsg(Constants.TITLE_ERROR);
        }else{
            data.setCode(0);
            data.setMsg(Constants.PUBLISH_POST_SUCCESS);
            data.setSrc(postId.toString());
        }
        //编码格式必须设置在getWritter之前，否则无法生效
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Charset","UTF-8");
        try (PrintWriter pw = response.getWriter()) {
            pw.write(objectMapper.writeValueAsString(data));
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //文章点赞接口
    @RequestMapping(value = "like")
    public void addLike(HttpServletResponse response, HttpServletRequest request)throws IOException{
        ResponseData data = postService.addLike(request);

        System.out.println(data);

        PrintWriter pw = null;
        response.setCharacterEncoding("UTF-8");  //编码格式必须设置在getWritter之前，否则无法生效
        response.setHeader("Charset","UTF-8");
        pw = response.getWriter();
        pw.write(objectMapper.writeValueAsString(data));
        //System.out.println(objectMapper.writeValueAsString(myPosts));
        pw.flush();
        pw.close();
    }
}
