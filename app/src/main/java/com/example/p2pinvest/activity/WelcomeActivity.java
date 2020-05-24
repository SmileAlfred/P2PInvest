package com.example.p2pinvest.activity;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.p2pinvest.R;
import com.example.p2pinvest.common.ActivityManager;
import com.example.p2pinvest.util.UIUtils;

/**
 * 欢迎页面，
 * 1. 实现两秒延迟后登入主界面；
 * 2. 实现全屏显示；
 * @author 我是刘优秀
 */
public class WelcomeActivity extends Activity {

    private RelativeLayout rlWelcome;
    private ImageView ivWelcomeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ActivityManager.getInstance().add(this);
        findViews();
        initData();
    }

    private void findViews() {
        ivWelcomeIcon = findViewById(R.id.iv_welcome_icon);
        rlWelcome = findViewById(R.id.rl_welcome);
    }

    protected void initData() {
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                //finish();
                ActivityManager.getInstance().remove(WelcomeActivity.this);
            }
        }, 2000);
        showAnimation();
    }

    /**
     * 显示动画:    透明度: 0--1 持续2s
     */
    private void showAnimation() {
        //透明度
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        //启动动画
        rlWelcome.startAnimation(alphaAnimation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UIUtils.getHandler().removeCallbacksAndMessages(null);
    }
}
