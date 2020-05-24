package com.example.p2pinvest.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.p2pinvest.common.MyApplication;

/**
 * 专门提供为处理一些UI相关的问题而创建的工具类，
 * 提供资源获取的通用方法，避免每次都写重复的代码获取结果。
 */
public class UIUtils {

    public static Context getContext() {
        return MyApplication.context;
    }

    public static Handler getHandler() {
        return MyApplication.handler;
    }

    public static Handler getMainHandler() {
        return MyApplication.mainHandler;
    }

    //加载指定viewId的视图对象，并返回；加载布局使用的是 Application
    public static View getView(int viewId) {
        View view = View.inflate(getContext(), viewId, null);
        return view;
    }

    //返回指定colorId对应的颜色值
    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    public static String[] getStringArr(int strArrId) {
        String[] stringArray = getContext().getResources().getStringArray(strArrId);
        return stringArray;
    }

    //将dp转化为px
    public static int dp2px(int dp) {
        //获取手机密度
        float density = getContext().getResources().getDisplayMetrics().density;
        //实现四舍五入
        return (int) (dp * density + 0.5);
    }

    public static int px2dp(int px) {
        //获取手机密度
        float density = getContext().getResources().getDisplayMetrics().density;
        //实现四舍五入
        return (int) (px / density + 0.5);
    }

    //保证runnable中的操作在主线程中执行
    public static void runOnUiThread(Runnable runnable) {
        if (isInMainThread()) {
            runnable.run();
        } else {
            UIUtils.getHandler().post(runnable);
        }
    }

    //判断当前线程是否是主线程
    private static boolean isInMainThread() {
        int currentThreadId = android.os.Process.myTid();
        return MyApplication.mainThreadId == currentThreadId;
    }
    public static void toast(String message,boolean isLengthLong){
        Toast.makeText(UIUtils.getContext(), message,isLengthLong? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}
