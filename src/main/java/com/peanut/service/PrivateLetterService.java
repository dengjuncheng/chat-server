package com.peanut.service;

import com.peanut.pojo.vo.PrivateLetterEntity;
import com.peanut.pojo.vo.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PrivateLetterService {

    public List<PrivateLetterEntity> getPrivateLetterByPageAndUserId(Integer userId, Integer pageNum, Integer pageSize);

    public Boolean deleteById(Integer id);

    public Boolean insert(Integer fromId, String toId, String content);

    ResponseData senMessage(HttpServletRequest request);
}
