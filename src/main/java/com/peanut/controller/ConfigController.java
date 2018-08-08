package com.peanut.controller;


import com.peanut.service.ConfigService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class ConfigController {

    @Resource
    ConfigService configService;

    private String fileDir;
    // 文件后缀名称
    private String fileExt;
    // 当前站点上下文
    private String pageCtx;
    // 站点真实路径
    private String relPath;
    // 上传文件保存路径
    private String savePath;
    // 允许上传文件后缀MAP数组
    private static final HashMap<String, String> extMap = new HashMap<String, String>();
    // 允许上传文件大小MAP数组
    private static final HashMap<String,Long> sizeMap = new HashMap<String, Long>();
    // 上传文件存放根目录
    private String filePath = "/attached/";

    static {
        // 初始后缀名称MAP数组
        extMap.put("image", "gif,jpg,jpeg,png,bmp,GIF,JPG,JPEG,PNG,BMP");
        extMap.put("flash", "swf,flv,mp4");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,mp4");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,txt,zip,rar");
        // 初始文件大小MAP数组
        sizeMap.put("image", 10 *1024* 1024L);
        sizeMap.put("flash", 30 *1024* 1024 * 1024L);
        sizeMap.put("media", 30 *1024* 1024 * 1024L);
        sizeMap.put("file", 10 *1024* 1024 * 1024L);
    }
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(MultipartFile imgFile, HttpServletRequest request,
                                      String dir) throws IOException {
        // 初始相关变量

        //System.out.println("测试");
        Map<String, Object> execute = new HashMap<String, Object>();
        ServletContext context=request.getSession().getServletContext();
        pageCtx = context.getContextPath().concat(filePath);
        relPath = context.getRealPath(filePath);
        fileDir = dir;
        if (null == dir || dir.isEmpty()) {
            fileDir = "file";
        }
        // 检查是否有上传文件
        if (null == imgFile) {
            execute.put("error", 1);
            execute.put("message", "请选择上传文件.");
            return execute;
        }
        // 检查上传文件保存目录是否存在或可写
        if (!isExistOrRwFolder()) {
            execute.put("error", 1);
            execute.put("message", "上传文件保存目录不存在或\n是没有写入权限,请检查.");
            return execute;
        }
        // 检查目录名称是否正确
        if (!extMap.containsKey(fileDir)) {
            execute.put("error", 1);
            execute.put("message", "目录名不正确,请检查.");
            return execute;
        }
        // 计算出文件后缀名
        // String tempFileName = FilenameUtils.getName(imgFile
        //      .getOriginalFilename());
        fileExt = FilenameUtils
                .getExtension(imgFile.getOriginalFilename());
        // 检查上传文件类型
        if(!Arrays.<String>asList(extMap.get(fileDir).split(",")).contains(fileExt)){
            execute.put("error", 1);
            execute.put("message", "上传文件的格式被拒绝,\n只允许" + extMap.get(fileDir) + "格式的文件.");
            return execute;
        }
        // 检查上传文件的大小
        long maxSize = sizeMap.get(fileDir);
        if (imgFile.getSize() > maxSize) {
            execute.put("error", 1);
            String size = null;
            if(maxSize < 1024) {
                size = maxSize + "B";
            }
            if(maxSize > 1024 && maxSize < 1024 * 1024){
                size = maxSize/1024 + "KB";
            }
            if(maxSize > 1024 * 1024){
                size = maxSize/(1024 * 1024) + "MB";
            }
            execute.put("message", "上传文件大小超过限制,只允\n许上传小于 " + size + " 的文件.");
            return execute;
        }
        // 生成新的文件名,并按日期分类
        newSavePath();
        // 拷贝上传文件至指定存放目录
        copy(imgFile.getInputStream(), savePath);
        // 计算出文件输出路径
        int point = savePath.lastIndexOf("/") - 8;
        StringBuilder url = new StringBuilder(pageCtx);
        url.append(fileDir).append("/");
        url.append(savePath.substring(point));
        // 返回上传文件的输出路径至前端
        execute.put("error", 0);
        execute.put("url", url.toString());
        System.out.println(url);

        return execute;
    }

    public boolean isExistOrRwFolder(){
        if(null == relPath || relPath.isEmpty()) {
            return false;
        }
        File folder = new File(relPath);
        //文件路径不存在则创建目录
        if(!folder.exists()) {
            folder.mkdirs();
        }
        if(!folder.isDirectory())
            return false;
        if(!folder.canWrite())
            return false;
        return true;
    }

    /**
     * @category 生成新的文件名,且按日期分类管理
     */
    public void newSavePath() {
        StringBuilder tempPath = new StringBuilder(relPath);
        tempPath.append("/").append(fileDir).append("/");
        SimpleDateFormat folderNameFormat = new SimpleDateFormat("yyyyMMdd");
        tempPath.append(folderNameFormat.format(new Date()));
        File temp = new File(tempPath.toString());
        if(!temp.exists())
            temp.mkdirs();
        SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMddkkmmss_S");
        tempPath.append("/").append(fileNameFormat.format(new Date()));
        tempPath.append(".").append(fileExt);
        savePath = tempPath.toString().replaceAll("\\\\", "/");
    }


    public void copy(InputStream src, String tar) {
        // 判断源文件或目标路径是否为空
        if (null == src
                || null == tar
                || tar.isEmpty()) {
            return;
        }
        //InputStream srcIs = null;
        OutputStream tarOs = null;
        try {
            //srcIs = new FileInputStream(src);
            File tarFile = new File(tar);
            tarOs = new FileOutputStream(tarFile);
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = src.read(buffer))) {
                tarOs.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != src) {
                    src.close();
                }
                if (null != tarOs) {
                    tarOs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
