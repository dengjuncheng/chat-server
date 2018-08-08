package com.peanut.utils;

public class ForbiddenCharacterUtil {

    public static Boolean checkString(String value){
        String fc = "~!@#$%^&*()_+=-\\][}{|\"'?/>.<,~！@#￥%……&*（）——+}{、】【'？、》《。，";
        char[] fList = fc.toCharArray();
        for(char b : fList){
            if(value.indexOf(b) > -1){
                return true;
            }
        }
        return false;
    }
}
