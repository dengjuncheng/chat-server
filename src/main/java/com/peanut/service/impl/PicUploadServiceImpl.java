package com.peanut.service.impl;

import com.peanut.constant.Constants;
import com.peanut.pojo.vo.ResponseData;
import com.peanut.service.PicUploadService;
import com.peanut.utils.UploadPathUtil;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("picUploadService")
public class PicUploadServiceImpl implements PicUploadService{
    @Override
    public ResponseData getUploadHeadPicResponse(MultipartFile file) {
        ResponseData responseData = new ResponseData();
        long currentTimeStamp = System.currentTimeMillis();
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        if(file.getSize() > Constants.HEAD_MAX_LENGTH){
            responseData.setCode(1);
            responseData.setMsg(Constants.MAXIMUN_LIMIT_ERROR);
            return responseData;
        }

        if(!Constants.PNG.equals(extension) && !Constants.JPG.equals(extension) && !Constants.JPGE.equals(extension)) {
            responseData.setCode(2);
            responseData.setMsg(Constants.FILE_TYPE_ERROR);
            return responseData;
        }

        String currentFileName = UUID.randomUUID().toString() + originalFilename;
        try {
            file.transferTo(new File(UploadPathUtil.getHeadUploadPath(file, currentTimeStamp,true) + currentFileName));
        } catch (Exception e){
            e.printStackTrace();
            return responseData;
        }

        responseData.setCode(0);
        responseData.setMsg(Constants.HEAD_PIC_UPLOAD_SUCCESS);
        responseData.setSrc(UploadPathUtil.getHeadUploadPath(file, currentTimeStamp, false) + currentFileName);
        return responseData;
    }

    @Override
    public Map<String, Object> uploadFile(HttpServletRequest request) {
        Map<String, Object> m = new HashMap<>();
        String rootDir = Constants.PIC_DIR;
        String tmpDir = UploadPathUtil.getUploadTempPath();
        File tmpDirPath = new File(tmpDir);

        System.out.println(tmpDir);

        if(tmpDirPath.exists()){
            System.out.println("tmp文件夹存在");
        }

        if(ServletFileUpload.isMultipartContent(request)){
            try {
                request.setCharacterEncoding("UTF-8");

                DiskFileItemFactory dff = new DiskFileItemFactory();
                dff.setRepository(tmpDirPath);
                dff.setSizeThreshold(Constants.UPLOAD_BUFFER_SIZE);
                ServletFileUpload sfu = new ServletFileUpload(dff);
                sfu.setFileSizeMax(Constants.FILE_MAX_SIZE);
                sfu.setSizeMax(Constants.FILE_MAX_SIZE);
                FileItemIterator fii = sfu.getItemIterator(request);
                List<?> items = sfu.parseRequest(request);
                System.out.println(request.getContentLengthLong());
                while(fii.hasNext()){
                    System.out.println("fii hasNext");
                    FileItemStream fis = fii.next();
                    if (!fis.isFormField() && fis.getName().length() > 0) {
                        System.out.println("判断成功");
                        BufferedInputStream in = new BufferedInputStream(fis.openStream());// 获得文件输入流
                        BufferedOutputStream out =
                                new BufferedOutputStream(new FileOutputStream(new File(tmpDir + "123.png")));//
                        Streams.copy(in, out, true);
                    }
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return m;
            } catch (FileUploadException e) {
                e.printStackTrace();
                return m;
            } catch (IOException e) {
                e.printStackTrace();
                return m;
            }
        }
        m.put("path", "/" +"testpath"+ "/");
        m.put("filename", "filename");
        m.put("original", "original");
        m.put("name", "newFileName");
        m.put("url", "http://www.taopic.com/uploads/allimg/130331/240460-13033106243430.jpg");
        m.put("state", "SUCCESS");
        m.put("type", ".jpg");
        m.put("size", "99697");

        return m;
    }
}
