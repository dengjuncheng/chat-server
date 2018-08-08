package com.peanut.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static final String keyword = "The Shawshank Redemption";
    public static String getMD5(String msg){
        String md5Str = "";
        String safeStr = msg + keyword;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] input = safeStr.getBytes();
            byte[] buff = md5.digest(input);
            md5Str = bytesToHex(buff);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Str;
    }

    private static String bytesToHex(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        int digital;
        for(int i= 0 ; i < bytes.length ; i++ ){
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(digital));
        }
        return sb.toString().toUpperCase();
    }

    public static void main(String[] args){
        System.out.println(MD5Util.getMD5("邓君城"));
    }
}
