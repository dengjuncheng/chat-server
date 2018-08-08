package com.peanut.chat.utils;

import java.util.Arrays;
import java.util.Base64;

public class Base64Util {
    //解码
    public static String decode(byte[] values){
        return new String(Base64.getDecoder().decode(values));
    }
    //编码
    public static String encode(byte[] values){
        return new String(Base64.getEncoder().encode(values));
    }
}
