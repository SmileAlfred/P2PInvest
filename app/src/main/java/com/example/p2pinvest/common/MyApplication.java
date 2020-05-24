package com.example.p2pinvest.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by shkstart on 2016/12/2 0002.
 * 1. ==**为什么提供MyApplication类？**==
 *
 * 	Application类里获取的Context,Handler对象可以被当前Module里的任何类库引用到，
 * 	而不必在需要用到的时候去new对象或者是通过构造传入。
 *
 * 	 Application类里获取的主线程和主线程 id 对象可以在当前 Module 任意的地方，
 * 	 判断当前代码是否运行在主线程当中，或者将一段代码指定在主线程中运行。
 *
 * 	1.Application对象的创建要早于所有的Activity对象的创建
 *  2.Context :getApplicationContext() 和 Activity:getApplication():返回的都是Application对象
 *  3.Application的实例，随着应用加载到内存中，此对象创建，方法调用。只要当前的进程存在，此对象就存在。（单例）
 *   如果进程销毁，此对象销毁。。
 *  4.Application实例的生命周期要早于所有的其他组件。
 *  5.application可以看成：应用全局数据内存级共享容器 <生命周期长，并且各个组件都可以调用，故可以进行事件传递>
 */
public class MyApplication extends Application {

    //在整个应用执行过程中，需要提供的变量
    //需要使用的上下文对象、需要使用的handler、提供主线程对象、提供主线程对象的id
    public static Context context;
    public static Handler handler;
    public static Handler mainHandler;
    public static Thread mainThread;
    public static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this.getApplicationContext();
        handler = new Handler();
        mainHandler  = new Handler(Looper.getMainLooper());
        mainThread = Thread.currentThread();//实例化当前Application的线程即为主线程
        mainThreadId = android.os.Process.myTid();//获取当前线程的id

        //设置未捕获异常的处理器;测试过程中需要让其报错，上线后再打开，给用户一个有好的提示；
        //CrashHandler.getInstance().init();
    }
}
