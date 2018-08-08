package com.peanut.service;

import com.peanut.pojo.vo.ResponseData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PicUploadService {
    public ResponseData getUploadHeadPicResponse(MultipartFile file);

    public Map<String, Object> uploadFile(HttpServletRequest request);
}
