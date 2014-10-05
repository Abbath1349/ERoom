package ru.dmitrykuzmin.eroom.common.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by DmitryComp on 09.09.2014.
 */
public class ResUtils {
    public static String getStringByName(Context context,String name){
        Resources res=context.getResources();
        int id= res.getIdentifier(name,"string",context.getPackageName());
        return context.getString(id);
    }
}
