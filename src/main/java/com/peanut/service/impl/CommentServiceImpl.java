package com.peanut.service.impl;

import com.github.pagehelper.PageHelper;
import com.peanut.constant.Constants;
import com.peanut.dao.mapper.CommentMapper;
import com.peanut.pojo.Comment;
import com.peanut.pojo.User;
import com.peanut.pojo.vo.CheckResult;
import com.peanut.pojo.vo.CommentSimpleEntity;
import com.peanut.pojo.vo.PostCommentRel;
import com.peanut.pojo.vo.ResponseData;
import com.peanut.service.CommentService;
import com.peanut.utils.SensitiveWordUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<PostCommentRel> getTopComment(Integer size) {
        return commentMapper.getCommentPostRel(size);
    }

    @Override
    public List<CommentSimpleEntity> getCommentByPostId(Integer id, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        return commentMapper.getSimpleCommentsByPostId(id);
    }

    @Override
    public CommentSimpleEntity getCommentById(Integer id) {
        return commentMapper.getSimpleCommentById(id);
    }

    @Override
    public List<CommentSimpleEntity> getCommentWithReply(Integer id, Integer pageSize, Integer pageNum) {
        List<CommentSimpleEntity> commentSimpleEntities = getCommentByPostId(id, pageSize, pageNum);
        //commentSimpleEntities.forEach(System.out::println);
        commentSimpleEntities.forEach(entity -> {
            if(entity.getIsReply() == 1) {
                CommentSimpleEntity reply = getCommentById(entity.getReplyId());
                if (reply.getIsSensitive() == 1) {
                    reply.setContent(Constants.COMMENT_SENSITIVE);
                }
                entity.setReplyComment(reply);
            }
            if(entity.getIsSensitive() == 1){
                entity.setContent(Constants.COMMENT_SENSITIVE);
            }
        });
        return commentSimpleEntities;
    }

    @Override
    public ResponseData dealWithReply(HttpServletRequest request) {
        ResponseData data = new ResponseData();
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            data.setCode(2); //2表示用户未登录
            data.setMsg(Constants.USER_NOT_LOGGED_IN);
            return data;
        }
        Integer toCommentId = Integer.parseInt(request.getParameter("toId"));
        String content = request.getParameter("content");
        Integer postId = Integer.valueOf(request.getParameter("postId"));
        Integer id= user.getId();
        Integer toUserId = commentMapper.getUserIdByCommentId(toCommentId);
        if(toUserId.equals(id)){
            data.setCode(3);  //3表示用户id与回复的id一样。
            data.setMsg(Constants.ID_SAME_ERROR);
            return data;
        }
        if(StringUtils.isEmpty(content)){
            data.setCode(4);  //4表示回复内容为空
            data.setMsg(Constants.REPLY_CONTENT_EMPTY);
            return data;
        }
        CheckResult cr = SensitiveWordUtil.checkSensitive(content);
        byte isSensitive = (byte) (cr.getCode() == 0 ? 0 : 1);
        if(cr.getCode() != 0){
            data.setCode(5);
            data.setMsg(cr.getReason());
            return data;
        }

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setIsReply((byte) 1);
        comment.setIsSensitive(isSensitive);
        comment.setPublishDate(new Date());
        comment.setReplyId(toCommentId);
        comment.setUserId(id);
        comment.setPostId(postId);
        int result = commentMapper.insert(comment);
        if(result != 1){
            data.setCode(1);
            data.setMsg(Constants.COMMENT_ERROR);
            return data;
        }
        data.setCode(0);
        data.setMsg(Constants.COMMENT_SUCCESS);
        return data;
    }

    //TODO 继续添加敏感词检测

    @Override
    public ResponseData addComment(HttpServletRequest request) {
        User u = (User) request.getSession().getAttribute("user");
        ResponseData data = new ResponseData();
        Integer postId = Integer.valueOf(request.getParameter("postId"));
        if(u == null){
            data.setCode(1);  //1  用户未登录
            data.setMsg(Constants.USER_NOT_LOGGED_IN);
            data.setSrc(String.valueOf(postId));
            return data;
        }
        Integer userId = u.getId();
        String content = request.getParameter("comment");

        if(content == null || content.length() < 5 || content.length()>300){
            data.setCode(2);
            data.setMsg(Constants.COMMENT_LENGTH_ERROR);  //2 .评论长度不够
            data.setSrc(String.valueOf(postId));
            return data;
        }
        CheckResult cr = SensitiveWordUtil.checkSensitive(content);
        byte isSensitive = (byte) (cr.getCode() == 0 ? 0 : 1);
        if(cr.getCode() != 0){
            data.setCode(4);
            data.setMsg(cr.getReason());
            return data;
        }
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setPublishDate(new Date());
        comment.setIsSensitive(isSensitive);
        comment.setContent(content);
        comment.setIsReply((byte) 0);

        int result = commentMapper.insert(comment);

        if(result != 1){
            data.setCode(3);
            data.setMsg(Constants.COMMENT_ERROR);
            data.setSrc(String.valueOf(postId));
            return data;
        }

        data.setCode(0);
        data.setMsg(Constants.COMMENT_SUCCESS);
        data.setSrc(String.valueOf(postId));

        return data;
    }
}
