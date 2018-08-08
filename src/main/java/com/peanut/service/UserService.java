package com.peanut.service;

import com.peanut.pojo.User;
import com.peanut.pojo.vo.ResponseData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService {
    public Boolean validateUser(String stuId, String password, HttpSession session);

    public ResponseData register(HttpServletRequest request);

    User getUserById(Integer id);

    ResponseData updateUser(HttpServletRequest request);
}
