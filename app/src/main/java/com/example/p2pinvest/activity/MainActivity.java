package com.example.p2pinvest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.p2pinvest.R;
import com.example.p2pinvest.common.ActivityManager;
import com.example.p2pinvest.fragment.HomeFragment;
import com.example.p2pinvest.fragment.InvestFragment;
import com.example.p2pinvest.fragment.MeFragment;
import com.example.p2pinvest.fragment.MoreFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private FragmentTransaction transaction;
    private FrameLayout flMain;
    private ImageView ivMainHome;
    private TextView tvMainHome;
    private LinearLayout llMainHome;
    private ImageView ivMainInvest;
    private TextView tvMainInvest;
    private LinearLayout llMainInvest;
    private ImageView ivMainMe;
    private TextView tvMainMe;
    private LinearLayout llMainMe;
    private ImageView ivMainMore;
    private TextView tvMainMore;
    private LinearLayout llMainMore;

    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;
    public final String TAG = MainActivity.this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //将当前的activity添加到ActivityManager中
        ActivityManager.getInstance().add(this);
        findViews();
        //默认显示首页
        setSelect(0);
    }

    private void findViews() {
        flMain = findViewById(R.id.fl_main);
        ivMainHome = findViewById(R.id.iv_main_home);
        tvMainHome = findViewById(R.id.tv_main_home);
        llMainHome = findViewById(R.id.ll_main_home);
        ivMainInvest = findViewById(R.id.iv_main_invest);
        tvMainInvest = findViewById(R.id.tv_main_invest);
        llMainInvest = findViewById(R.id.ll_main_invest);
        ivMainMe = findViewById(R.id.iv_main_me);
        tvMainMe = findViewById(R.id.tv_main_me);
        llMainMe = findViewById(R.id.ll_main_me);
        ivMainMore = findViewById(R.id.iv_main_more);
        tvMainMore = findViewById(R.id.tv_main_more);
        llMainMore = findViewById(R.id.ll_main_more);

        llMainHome.setOnClickListener(this);
        llMainInvest.setOnClickListener(this);
        llMainMe.setOnClickListener(this);
        llMainMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_main_invest:
                setSelect(1);
                break;
            case R.id.ll_main_me:
                setSelect(2);
                break;
            case R.id.ll_main_more:
                setSelect(3);
                break;
            case R.id.ll_main_home:
            default:
                setSelect(0);
                break;
        }
    }

    //提供相应的fragment的显示
    private void setSelect(int i) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        //隐藏所有Fragment的显示
        hideFragments();
        //重置ImageView和TextView的显示状态
        resetTab();

        switch (i) {
            case 0:
                if (homeFragment == null) {
                    //创建对象以后，并不会马上调用生命周期方法。而是在commit()之后，方才调用
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_main, homeFragment);
                }
                //显示当前的fragment
                transaction.show(homeFragment);
                //调用如下方法，来给对应页面进行联网请求。但是 调用位置错误;因为：创建对象以后，并不会马上调用生命周期方法。而是在commit()之后，方才调用
                //homeFragment.show();

                //改变选中项的图片和文本颜色的变化
                ivMainHome.setImageResource(R.drawable.bottom02);
                //错误的写法;此处设置的颜色应该是颜色色值；而传进去的是颜色id；
                //tvMainHome.setTextColor(R.color.text_progress);
                tvMainHome.setTextColor(getResources().getColor(R.color.home_back_selected));
                break;
            case 1:
                if (investFragment == null) {
                    investFragment = new InvestFragment();
                    transaction.add(R.id.fl_main, investFragment);
                }
                transaction.show(investFragment);

                //改变选中项的图片和文本颜色的变化
                ivMainInvest.setImageResource(R.drawable.bottom04);
                tvMainInvest.setTextColor(getResources().getColor(R.color.home_back_selected));

                break;
            case 2:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    transaction.add(R.id.fl_main, meFragment);
                }
                transaction.show(meFragment);

                //改变选中项的图片和文本颜色的变化
                ivMainMe.setImageResource(R.drawable.bottom06);
                tvMainMe.setTextColor(getResources().getColor(R.color.home_back_selected01));

                break;
            case 3:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    transaction.add(R.id.fl_main, moreFragment);
                }
                transaction.show(moreFragment);

                //改变选中项的图片和文本颜色的变化
                ivMainMore.setImageResource(R.drawable.bottom08);
                tvMainMore.setTextColor(getResources().getColor(R.color.home_back_selected));

                break;
            default:
                break;
        }
        transaction.commit();//提交事务
    }

    private void resetTab() {
        ivMainHome.setImageResource(R.drawable.bottom01);
        ivMainInvest.setImageResource(R.drawable.bottom03);
        ivMainMe.setImageResource(R.drawable.bottom05);
        ivMainMore.setImageResource(R.drawable.bottom07);

        tvMainHome.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvMainInvest.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvMainMe.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvMainMore.setTextColor(getResources().getColor(R.color.home_back_unselected));
        //这种方式也可以
        //tvMainMore.setTextColor(ContextCompat.getColor(this, R.color.home_back_unselected));
    }

    private void hideFragments() {

        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (investFragment != null) {
            transaction.hide(investFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }

    }

    //重写onKeyUp()，实现连续两次点击方可退出当前应用

    private boolean flag = true;
    private static final int WHAT_RESET_BACK = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("TAG", "handleMessage");
            switch (msg.what) {
                case WHAT_RESET_BACK:
                    flag = true;//复原
                    break;
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && flag) {
            Toast.makeText(MainActivity.this, "再点击一次，退出当前应用", Toast.LENGTH_SHORT).show();
            flag = false;
            //发送延迟消息
            handler.sendEmptyMessageDelayed(WHAT_RESET_BACK, 2000);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    //为了避免出现内存的泄漏，需要在 onDestroy() 中，移除所有未被执行的消息
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //方式一：移除指定id的所有的消息
        //handler.removeMessages(WHAT_RESET_BACK);
        //方式二：移除所有的未被执行的消息
        handler.removeCallbacksAndMessages(null);
    }
}