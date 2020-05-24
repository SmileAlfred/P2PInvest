package com.atguigu.activitydemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.e("TAG", "SecondActivity onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG", "SecondActivity onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "SecondActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "SecondActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "SecondActivity onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("TAG", "SecondActivity onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "SecondActivity onDestroy()");
    }

    /**
     * 页面二 BUTTON 的点击
     */
    public void back(View v) {
        setResult(RESULT_OK);
        finish();
    }
}
