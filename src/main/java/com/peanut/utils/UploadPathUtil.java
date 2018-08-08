package com.peanut.utils;

import com.peanut.constant.Constants;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class UploadPathUtil {
    public static String getHeadUploadPath(MultipartFile file, long currentTimeStamp, boolean isAbsolute){
        String returnPath;
        if(isAbsolute){
            returnPath = Constants.PIC_DIR;
        }else{
            returnPath = Constants.UPLOAD_RELATIVE_PATH;
        }
        returnPath += getSubPath(currentTimeStamp);

        if(isAbsolute){
            File filePath = new File(returnPath);
            if(!filePath.exists()){
                filePath.mkdir();
            }
        }
        return returnPath;
    }

    /**
     * 获得子路径
     * @return
     */
    private static String getSubPath(long timeStamp){
        return String.valueOf(timeStamp % Constants.UPLOAD_DIR_NUM)+"/";
    }

    public static String getUploadTempPath(){
        return Constants.PIC_DIR+"tmp";
    }

    public static void main(String[] args){
        getUploadTempPath();
    }

}
