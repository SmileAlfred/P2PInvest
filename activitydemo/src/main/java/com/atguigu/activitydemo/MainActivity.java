package com.atguigu.activitydemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("TAG", "MainActivity onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG", "MainActivity onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "MainActivity onResume()");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("TAG", "MainActivity onSaveInstanceState()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "MainActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "MainActivity onStop()");
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("TAG", "MainActivity onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "MainActivity onDestroy()");
    }

    public void startSecond1(View v) {
//        finish();
        startActivity(new Intent(this, SecondActivity.class));
    }
    public void startSecond2(View v) {
        startActivityForResult(new Intent(this, SecondActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "MainActivity onActivityResult()");
    }

    public void startDialogActivity(View v) {
        startActivity(new Intent(this, DialogActivity.class));
    }

    public void startDialog(View v) {
        new AlertDialog.Builder(this)
                    .setTitle("测试")
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", null)
                    .show();   
        
//        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
    }
    

}
