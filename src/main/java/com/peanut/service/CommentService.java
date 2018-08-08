package com.peanut.service;

import com.peanut.pojo.vo.CommentSimpleEntity;
import com.peanut.pojo.vo.PostCommentRel;
import com.peanut.pojo.vo.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService {
    List<PostCommentRel> getTopComment(Integer size);
    List<CommentSimpleEntity> getCommentByPostId(Integer id, Integer pageSize, Integer pageNum);
    CommentSimpleEntity getCommentById(Integer id);
    List<CommentSimpleEntity> getCommentWithReply(Integer id, Integer pageSize, Integer pageNum);

    ResponseData dealWithReply(HttpServletRequest request);

    ResponseData addComment(HttpServletRequest request);
}
