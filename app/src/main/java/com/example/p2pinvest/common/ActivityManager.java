package com.example.p2pinvest.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by shkstart on 2016/12/2 0002.
 *
 * 统一应用程序中所有的Activity的栈管理（单例）
 * 涉及到activity的添加、删除指定、删除当前、删除所有、返回栈大小的方法
 */
public class ActivityManager {

    //单例模式：饿汉式：私有化构造器，先new；然后再get return；
    private ActivityManager(){

    }
    private static ActivityManager activityManager = new ActivityManager();

    public static ActivityManager getInstance(){
        return activityManager;
    }

    //提供栈的对象
    private Stack<Activity> activityStack = new Stack<>();

    //activity的添加
    public void add(Activity activity){
        if(activity != null){
            activityStack.add(activity);
        }
    }

    //插曲：面试题，[12,3,44,6,332,65,-56,1]数组中每个数初一第一个数形成新数组。（同样是倒序）
    //删除指定的activity
    public void remove(Activity activity){
        if(activity != null){
//            for(int i = 0; i < activityStack.size(); i++) {
//                Activity currentActivity = activityStack.get(i);
//                if(currentActivity.getClass().equals(activity.getClass())){
//                    currentActivity.finish();//销毁当前的activity
//                    activityStack.remove(i);//从栈空间移除
//                }
//            }
			//这里倒着遍历不仅仅是因为，栈先进后出；remove可以移除任何位置的activity；
			//倒序遍历是因为：remove后，activity在栈中对应id发生改变，正序遍历一定会出错；
            for(int i = activityStack.size() - 1;i >= 0;i--){
                Activity currentActivity = activityStack.get(i);
                if(currentActivity.getClass().equals(activity.getClass())){
                    currentActivity.finish();//销毁当前的activity
                    activityStack.remove(i);//从栈空间移除
                }
            }
        }
    }

    //删除当前的activity；当前可不就是栈顶嘛
    public void removeCurrent(){
        //方式一：
//        Activity activity = activityStack.get(activityStack.size() - 1);
//        activity.finish();
//        activityStack.remove(activityStack.size() - 1);

        //方式二：
        Activity activity = activityStack.lastElement();
        activity.finish();
        activityStack.remove(activity);
    }

    //删除所有的activity
    public void removeAll(){
        for (int i = activityStack.size() - 1;i >= 0;i--){
            Activity activity = activityStack.get(i);
            activity.finish();
            activityStack.remove(activity);
        }
    }

    //返回栈大小
    public int size(){
        return activityStack.size();
    }

}
