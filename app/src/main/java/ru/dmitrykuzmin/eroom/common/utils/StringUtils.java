package ru.dmitrykuzmin.eroom.common.utils;

/**
 * Created by DmitryComp on 20.09.2014.
 */
public class StringUtils {
    public static boolean isNullOrEmpty(String str){
        if(str==null || str.equals(""))
            return true;
        return false;
    }
}
