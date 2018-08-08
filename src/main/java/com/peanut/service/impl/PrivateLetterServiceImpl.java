package com.peanut.service.impl;

import com.github.pagehelper.PageHelper;
import com.peanut.constant.Constants;
import com.peanut.dao.mapper.PrivateLetterMapper;
import com.peanut.pojo.PrivateLetter;
import com.peanut.pojo.User;
import com.peanut.pojo.vo.PrivateLetterEntity;
import com.peanut.pojo.vo.ResponseData;
import com.peanut.service.PrivateLetterService;
import com.peanut.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service("privateLetterService")
public class PrivateLetterServiceImpl implements PrivateLetterService{
    @Resource
    private PrivateLetterMapper privateLetterMapper;

    @Resource
    private UserService userService;

    @Override
    public List<PrivateLetterEntity> getPrivateLetterByPageAndUserId(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return privateLetterMapper.getPrivateLetterByUserId(userId);
    }

    @Override
    public Boolean deleteById(Integer id) {
        Integer result = privateLetterMapper.deleteByPrimaryKey(id);

        if(result < 1)
            return false;

        return true;
    }

    @Override
    public Boolean insert(Integer fromId, String toId, String content) {
        Integer to_id = Integer.parseInt(toId);
        if(content.trim().isEmpty() || content.length() < 5){
            return false;
        }
        Date currentTime = new Date();

        PrivateLetter privateLetter = new PrivateLetter();

        privateLetter.setContent(content);
        privateLetter.setFromId(fromId);
        privateLetter.setIsRead((byte)0);
        privateLetter.setSendingTime(currentTime);
        privateLetter.setToId(to_id);

        if(privateLetterMapper.insert(privateLetter) < 1){
            return false;
        }
        return true;
    }

    @Override
    public ResponseData senMessage(HttpServletRequest request) {
        ResponseData data = new ResponseData();
        User u = (User) request.getSession().getAttribute("user");

        if(u == null){
            data.setCode(1);
            data.setMsg(Constants.USER_NOT_LOGGED_IN_ON_MSG);
            return data;
        }

        Integer toId = Integer.valueOf(request.getParameter("toId"));
        String content = request.getParameter("msg");
        if(userService.getUserById(toId) == null){
            data.setCode(2);
            data.setMsg(Constants.RECEIVE_USER_NOT_FOUND);
            return data;
        }

        if(StringUtils.isEmpty(content) || content.length() < 5){
            data.setCode(3);
            data.setMsg(Constants.LETTER_NOT_STANDARD);
            return  data;
        }

        PrivateLetter letter = new PrivateLetter();
        letter.setToId(toId);
        letter.setSendingTime(new Date());
        letter.setIsRead((byte) 0);
        letter.setFromId(u.getId());
        letter.setContent(content);
        int result = privateLetterMapper.insert(letter);

        if(result != 1){
            data.setCode(4);
            data.setMsg(Constants.LETTER_ERROR);
            return data;
        }
        data.setCode(0);
        data.setMsg(Constants.LETTER_SUCCESS);
        return data;
    }
}
