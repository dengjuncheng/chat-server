package com.peanut.utils;

public class NumberVerifier {
    private static final String NUMBER_STRING = "0123456789";
    public static boolean checkNumber(String str){
        int length = str.length();
        for(int i = 0; i < length; i++){
            if(NUMBER_STRING.indexOf(str.charAt(i)) == -1){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        System.out.println(checkNumber("1323232000"));
    }
}
