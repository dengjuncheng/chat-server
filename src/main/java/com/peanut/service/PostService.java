package com.peanut.service;


import com.peanut.pojo.User;
import com.peanut.pojo.vo.PostSimpleEntity;
import com.peanut.pojo.vo.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface PostService {
    //获取文章列表
    List<PostSimpleEntity> getPostSimpleList(int pageCount, int pageSize);
    //获取置顶列表
    List<PostSimpleEntity> getTopRankList(Integer pageSize);
    //获取浏览数排行列表
    List<PostSimpleEntity> getBrowseRankList(Integer mainPageSize);
    //获取喜欢排行榜列表
    List<PostSimpleEntity> getLikeRankList(Integer mainPageSize);
    //获取最新文章列表
    List<PostSimpleEntity> getRecentRankList(Integer mainPageSize);
    //获取评论排行列表
    List<PostSimpleEntity> getCommentRankList(Integer mainPageSize);
    //获取置顶列表
    List<PostSimpleEntity> getTopList(int pageCount, int pageSize);
    //获取用户已发布文章列表
    List<PostSimpleEntity> getMyPostByUid(Integer useId,int pageNum, int pageSize);
    //新建文章
    Integer insert(User u ,String content, String title);
    //通过id获取文章详情
    PostSimpleEntity getPostById(Integer id);
    //增加文章查看数
    void increaseViewNum(Integer id);
    //增加文章喜欢数
    ResponseData addLike(HttpServletRequest request);
}
