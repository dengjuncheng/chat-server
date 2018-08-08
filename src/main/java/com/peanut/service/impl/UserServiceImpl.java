package com.peanut.service.impl;

import com.peanut.constant.Constants;
import com.peanut.dao.mapper.UserMapper;
import com.peanut.pojo.User;
import com.peanut.pojo.vo.ResponseData;
import com.peanut.service.UserService;
import com.peanut.utils.ForbiddenCharacterUtil;
import com.peanut.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service(value = "userService")
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;

    @Override
    public Boolean validateUser(String stuId, String password, HttpSession session) {
        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(stuId)){
            return false;
        }

        User user = userMapper.selectByStuId(stuId);
        if(user != null){
            if(MD5Util.getMD5(password).equals(user.getPassword())){
                session.setAttribute("user", user);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    @Override
    public ResponseData register(HttpServletRequest request) {
        ResponseData rd = new ResponseData();

        String uploadPic = request. getParameter("head_pic");
        String username = request.getParameter("username");
        String studentId = request.getParameter("student_id");
        String password = request.getParameter("password");
        String rePassowrd = request.getParameter("repassword");
        String sex = request.getParameter("sex");
        String birthday = request.getParameter("birthday");

        if(StringUtils.isEmpty(uploadPic)){
            uploadPic = Constants.DEFAULT_HEAD_PIC_URL;
        }

        if(StringUtils.isEmpty(username) || username.length() > 14){
            rd.setCode(1);
            rd.setMsg(Constants.USER_NAME_ERROR);
            return rd;
        }

        if(userMapper.selectByNickName(username) != null){
            rd.setCode(1);
            rd.setMsg(Constants.USER_NAME_EXIT_ERROR);
            return rd;
        }

        if(StringUtils.isEmpty(studentId) || studentId.trim().length() != 11){
            rd.setCode(1);
            rd.setMsg(Constants.STU_ID_ERROR);
            return rd;
        }

        if(userMapper.selectByStuId(studentId) != null){
            rd.setCode(1);
            rd.setMsg(Constants.STU_ID_EXIT_ERROR);
            return rd;
        }

        if(StringUtils.isEmpty(password) ||
                password.trim().length() < 6 || password.trim().length() > 15){
            rd.setCode(1);
            rd.setMsg(Constants.PASSWORD_LENGTH_ERROR);
            return rd;
        }

        if(!password.equals(rePassowrd)){
            rd.setCode(1);
            rd.setMsg(Constants.PASSWORD_DIFFER_ERROR);
            return rd;
        }

        if(StringUtils.isEmpty(birthday)){
            rd.setCode(1);
            rd.setMsg(Constants.BIRTHDAY_ERROR);
            return rd;
        }

        if(StringUtils.isEmpty(sex)){
            sex = "男";
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Date date = null;
        try {
            date=sdf.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
            rd.setCode(1);
            rd.setMsg(Constants.BIRTHDAY_ERROR);
            return rd;
        }

        if(ForbiddenCharacterUtil.checkString(username)){
            rd.setCode(1);
            rd.setMsg(Constants.REGISTER_NAME_ERROR);
            return rd;
        }

        User u =new User();
        u.setNickname(username);
        u.setHeadPic(uploadPic);
        u.setRegistDate(new Date());
        u.setStuId(studentId);
        u.setPassword(MD5Util.getMD5(password));
        u.setSex(sex);
        u.setBirthday(date);
        if(userMapper.insertSelective(u) < 1){
            rd.setCode(1);
            rd.setMsg(Constants.SERVER_ERROR);
            System.out.println("插入数据失败");
            return rd;
        }

        rd.setCode(0);
        rd.setMsg(Constants.REGISTER_SUCCESS);
        return rd;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public ResponseData updateUser(HttpServletRequest request) {
        ResponseData data = new ResponseData();
        User u = (User) request.getSession().getAttribute("user");
        if(u == null){
            data.setCode(1);
            data.setMsg(Constants.USER_NOT_LOGGED_IN_ON_MODIFY);
            return data;
        }

        String declaration = request.getParameter("declaration");
        String personalIn = request.getParameter("personalIn");
        User user = new User();
        user.setId(u.getId());
        user.setDeclaration(declaration);
        user.setPersonalIntroduction(personalIn);
        if(userMapper.updateByPrimaryKeySelective(user) != 1){
            data.setCode(2);
            data.setMsg(Constants.USER_UPDATE_ERROR);
            return data;
        }

        data.setCode(0);
        data.setMsg(Constants.USER_MODIFY_SUCCESS);
        u.setDeclaration(declaration);
        u.setPersonalIntroduction(personalIn);

        return data;
    }
}
