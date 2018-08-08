package com.peanut.service.impl;

import com.github.pagehelper.PageHelper;
import com.peanut.constant.Constants;
import com.peanut.controller.PostController;
import com.peanut.dao.mapper.PostLikeMapper;
import com.peanut.dao.mapper.PostMapper;
import com.peanut.pojo.Post;
import com.peanut.pojo.PostLike;
import com.peanut.pojo.User;
import com.peanut.pojo.vo.PostMain;
import com.peanut.pojo.vo.PostSimpleEntity;
import com.peanut.pojo.vo.ResponseData;
import com.peanut.service.PostService;
import com.peanut.utils.TextUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service("postService")
public class PostServiceImpl implements PostService{
    @Resource
    private PostMapper postMapper;
    @Resource
    private PostLikeMapper postLikeMapper;

    public List<PostSimpleEntity> getPostSimpleList(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<PostSimpleEntity> postSimpleList = postMapper.getPostSimpleList();
        postSimpleList.forEach(item -> item.setShortDesc(TextUtils.delHTMLTag(item.getContent())));

        return postSimpleList;
    }

    @Override
    public List<PostSimpleEntity> getTopRankList(Integer pageSize) {
        return postMapper.getTopList(pageSize);
    }

    @Override
    public List<PostSimpleEntity> getBrowseRankList(Integer mainPageSize) {

        return postMapper.getBrowserList(mainPageSize);
    }

    @Override
    public List<PostSimpleEntity> getLikeRankList(Integer mainPageSize) {
        return postMapper.getLikeList(mainPageSize);
    }

    @Override
    public List<PostSimpleEntity> getRecentRankList(Integer mainPageSize) {
        return postMapper.getRecentList(mainPageSize);
    }

    @Override
    public List<PostSimpleEntity> getCommentRankList(Integer mainPageSize) {
        return postMapper.getCommentList(mainPageSize);
    }

    @Override
    public List<PostSimpleEntity> getTopList(int pageCount, int pageSize) {
        PageHelper.startPage(pageCount, pageSize);
        List<PostSimpleEntity> topAll = postMapper.getTopAll();
        topAll.forEach(item -> item.setShortDesc(TextUtils.delHTMLTag(item.getContent())));
        return topAll;
    }

    @Override
    public List<PostSimpleEntity> getMyPostByUid(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return postMapper.getMyPostById(userId);
    }

    @Override
    public Integer insert(User u, String content, String title) {
        if(u ==null){
            return -1;
        }

        if(StringUtils.isEmpty(content.trim())){
            return -2;
        }

        if(StringUtils.isEmpty(title.trim()) || title.trim().length() < 5 || title.length() > 40){
            return -3;
        }

        Post post = new Post();
        post.setBrowserNum(0);
        post.setCommentNum(0);
        post.setContent(replaceVideoTag(content));
        post.setIsTop((byte) 0);
        post.setLikeNum(0);
        post.setPublishDate(new Date());
        post.setState((byte) 1);
        post.setStuId(u.getId());
        post.setTitle(title);
        postMapper.insertSelective(post);
        if(post.getId() == 0){
            return 0;
        }
        return post.getId();
    }

    @Override
    public PostSimpleEntity getPostById(Integer id) {
        return postMapper.getPostSimpleById(id);
    }

    @Override
    public void increaseViewNum(Integer id) {
        postMapper.increaseViewNum(id);
    }

    @Override
    public ResponseData addLike(HttpServletRequest request) {
        User u = (User) request.getSession().getAttribute("user");
        ResponseData data = new ResponseData();
        if(u == null){
            data.setCode(1);
            data.setMsg(Constants.USER_NOT_LOGGED);
            return data;
        }

        Integer postId = Integer.valueOf(request.getParameter("postId"));
        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(u.getId());
        if(postLikeMapper.verifyLike(postId, u.getId()) != 0){  //检查是否已经点赞
            data.setCode(2);
            data.setMsg(Constants.LIKE_ALREADY);
            return data;
        }

        postLikeMapper.insert(postLike);
        if(postLike.getId() == null || postLike.getId() == 0){
            data.setCode(3);
            data.setMsg(Constants.LIKE_ERROR);
            return data;
        }

        postMapper.increaseLikeNum(postId);
        data.setCode(0);
        data.setMsg(Constants.PLUS);

        return data;
    }

    private String replaceVideoTag(String value){
        return value.replace("<embed", "<video controls=\"controls\"");
    }
}
