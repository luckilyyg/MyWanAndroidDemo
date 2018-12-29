package com.crazy.gy;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.widget.ImageView;


/**
 * 作者：Administrator
 * 时间：2018/4/16
 * 功能：
 */
public class App extends Application {
    private static App instance;//全局实例
    public static Context mContext;

    //服务器地址
    public static String path = "http://www.wanandroid.com/";
    //imooc的api
    public static String imoocPath = "http://www.imooc.com/";



    public static App getInstance() {
        return instance;
    }

    public static Resources getAppResources() {
        return instance.getResources();
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
    }




    //返回
    public static Context getContextObject(){
        return mContext;
    }


}
