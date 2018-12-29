package com.crazy.gy.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpreferences_Utils {
    private SharedPreferences mPreferences;
    private final static String PREFERENCE_NAME="userInfo";//偏好设置存储文件名
    private static Sharedpreferences_Utils mUtils;

    public Sharedpreferences_Utils(Context context) {
        mPreferences=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
    }

    public static Sharedpreferences_Utils getInstance(Context context){
        if(mUtils==null){
            synchronized (Sharedpreferences_Utils.class){
                if(mUtils==null)
                    mUtils=new Sharedpreferences_Utils(context);
            }
        }
        return mUtils;
    }

    /**
     * 将数据存入偏好设置String
     */
    public void setString(String key, String values){
        SharedPreferences.Editor editor=mPreferences.edit();
        editor.putString(key, values);
        editor.commit();
    }
    /**
     * 将数据存入偏好设置Int
     */
    public void setInt(String key, int values){
        SharedPreferences.Editor editor=mPreferences.edit();
        editor.putInt(key,values);
        editor.commit();
    }
    /**
     * 将数据存入偏好设置Boolean
     */
    public void setBoolean(String key, boolean values){
        SharedPreferences.Editor editor=mPreferences.edit();
        editor.putBoolean(key, values);
        editor.commit();
    }

    /**
     * 将数据从偏好设置中取出String
     */
    public String getString(String key){
        String result=mPreferences.getString(key,"");//默认返回空
        return result;
    }
    /**
     * 将数据从偏好设置中取出int
     */
    public int getInt(String key){
        int result=mPreferences.getInt(key,0);//默认返回0
        return result;
    }
    /**
     * 将数据从偏好设置中取出boolean
     */
    public boolean getBoolean(String key){
        boolean result=mPreferences.getBoolean(key,false);//默认返回false
        return result;
    }

    /**
     * 清除信息
     */
    public void clear(){
        mPreferences.edit().clear().commit();
    }
}
