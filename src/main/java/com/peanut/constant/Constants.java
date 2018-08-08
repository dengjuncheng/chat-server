package com.peanut.constant;

public class Constants {
    public static final String SERVER_ERROR = "服务器错误";

    // 正则匹配替换评论内容中的特殊符号、标点符号等
    public final static String PATTERN_COMMENT_CONTENT="[^0-9a-zA-Z\u4e00-\u9fa5]";

    public static final String PIC_DIR = "/Users/dengjuncheng/myProjects/SSm_SSm/target/SSm/uploaded/";
    public static final String DEFAULT_HEAD_PIC_URL = "http://localhost:8080/images/default_head_pic.jpg";
    public static final int HEAD_MAX_LENGTH = 2*2024*1024; //头像最大为2M
    public static final String MAXIMUN_LIMIT_ERROR = "上传失败，头像大小不能超过2M";
    public static final String FILE_TYPE_ERROR = "上传失败，文件类型不正确";
    public static final String HEAD_PIC_UPLOAD_SUCCESS = "头像上传成功";
    public static final String JPG = "JPG";
    public static final String PNG = "png";
    public static final String JPGE = "jpge";

    public static final String UPLOAD_RELATIVE_PATH = "/uploaded/";
    public static final int UPLOAD_DIR_NUM = 32;


    //注册相关常量
    public static final String STU_ID_ERROR = "注册失败,学号格式错误";
    public static final String USER_NAME_EXIT_ERROR ="注册失败，昵称已存在";
    public static final String STU_ID_EXIT_ERROR ="注册失败，学号已存在";
    public static final String USER_NAME_ERROR = "注册失败，昵称格式错误";
    public static final String PASSWORD_LENGTH_ERROR = "注册失败，密码格式错误";
    public static final String PASSWORD_DIFFER_ERROR = "注册失败，两次输入的密码不一致";
    public static final String BIRTHDAY_ERROR ="注册失败，出生日期格式错误";
    public static final String REGISTER_SUCCESS = "注册成功";

    //上传文件时常量
    public static final int UPLOAD_BUFFER_SIZE = 2 * 1024 * 1024;
    public static final long FILE_MAX_SIZE = 1000000000;

    //发布帖子相关常量
    public static final String PUBLISH_POST_ERROR = "发表帖子失败";
    public static final String PUBLISH_POST_SUCCESS = "发表帖子成功";
    public static final String COMMENT_SENSITIVE = "用户评论敏感";
    public static final String USER_NOT_LOGGED_IN = "请登录后再回复";
    public static final String ID_SAME_ERROR = "不能回复自己的评论";

    public static final String REPLY_CONTENT_EMPTY = "回复失败，内容不能为空";
    public static final String COMMENT_ERROR = "回复出现错误，请稍后再试";
    public static final String COMMENT_SUCCESS = "回复成功";
    public static final String USER_NOT_LOGGED_IN_ON_PUBLISH = "请登录以后再发布";
    public static final String COMMENT_LENGTH_ERROR = "评论长度不在5-300之间";
    public static final String USER_NOT_LOGGED_IN_ON_MODIFY = "登录失效，请登录后再修改";
    public static final String USER_UPDATE_ERROR = "用户信息修改错误，请稍后重试";
    public static final String USER_MODIFY_SUCCESS = "用户个人信息修改成功";
    public static final String USER_NOT_LOGGED_IN_ON_MSG = "请登录后再发送私信";
    public static final String RECEIVE_USER_NOT_FOUND = "目标用户不存在";
    public static final String LETTER_NOT_STANDARD = "私信内容不规范";
    public static final String LETTER_ERROR = "发送私信出现错误，请稍后再试";
    public static final String LETTER_SUCCESS = "私信发送成功";
    public static final String USER_NOT_LOGGED = "请登录后操作";
    public static final String LIKE_ERROR = "操作失败，请稍后再试";
    public static final String PLUS = "+1";
    public static final String LIKE_ALREADY = "你已经点过赞";
    public static final String CONTENT_EMPTY_ERROR = "内容为空，发表失败";
    public static final String TITLE_ERROR = "标题长度不符合，发表失败";
    public static final String REGISTER_NAME_ERROR = "注册失败，用户名包含非法字符";
}
