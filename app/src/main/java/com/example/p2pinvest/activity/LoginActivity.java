package com.example.p2pinvest.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.p2pinvest.R;
import com.example.p2pinvest.bean.User;
import com.example.p2pinvest.common.AppNetConfig;
import com.example.p2pinvest.common.BaseActivity;
import com.example.p2pinvest.util.MD5Utils;
import com.example.p2pinvest.util.UIUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivTitleBack;
    private TextView tvTitle;
    private ImageView ivTitleSetting;
    private TextView tvLoginNumber;
    private EditText etLoginNumber;
    private RelativeLayout rlLogin;
    private TextView tvLoginPwd;
    private EditText etLoginPwd;
    private Button btnLogin;

    @Override
    protected void findViews() {
        ivTitleBack = findViewById(R.id.iv_title_back);
        tvTitle = findViewById(R.id.tv_title);
        ivTitleSetting = findViewById(R.id.iv_title_setting);
        tvLoginNumber = findViewById(R.id.tv_login_number);
        etLoginNumber = findViewById(R.id.et_login_number);
        rlLogin = findViewById(R.id.rl_login);
        tvLoginPwd = findViewById(R.id.tv_login_pwd);
        etLoginPwd = findViewById(R.id.et_login_pwd);
        btnLogin = findViewById(R.id.btn_login);

        ivTitleBack.setOnClickListener(this);
        ivTitleSetting.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("用户登录");
        ivTitleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                removeAll();
                goToActivity(MainActivity.class, null);
                break;
            case R.id.btn_login:
                //登录按钮的点击事件
                String number = etLoginNumber.getText().toString().trim();
                String pwd = etLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(pwd)) {
                    String url = AppNetConfig.LOGIN;
                    RequestParams params = new RequestParams();
                    Log.i("TAG", LoginActivity.class.getSimpleName() + "btn_login + onClick:" + "number+" + number + "pwd" + pwd + "+ url:" + url);
                    params.put("phone", number);
                    params.put("password", MD5Utils.MD5(pwd));
                    UIUtils.toast("登陆成功", false);
                    /**
                     * 没有做登陆的验证；因为我没有设置服务器；
                     */
                    /*client.post(url, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String content) {//200 404
                            //解析json
                                    JSONObject jsonObject = JSON.parseObject(content);
                            if (jsonObject != null) {
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    //解析json数据，生成User对象
                                    String data = jsonObject.getString("data");
                                    User user = JSON.parseObject(data, User.class);

                                    //保存用户信息
                                    saveUser(user);
                                    //重新加载界面
                                    removeAll();
                                    goToActivity(MainActivity.class, null);

                                } else {
                                    Toast.makeText(LoginActivity.this, "用户名不存在或密码不正确", Toast.LENGTH_SHORT).show();
                                }
                                Log.i("TAG", LoginActivity.class.getSimpleName() + "btn_login + onClick: 联网" + success);
                            } else {
                                Log.i("TAG", LoginActivity.class.getSimpleName() + "btn_login + onClick: + jsonObject = " + jsonObject);
                                finish();
                            }
                        }


                        @Override
                        public void onFailure(Throwable error, String content) {
                            UIUtils.toast("联网失败", false);
                        }
                    });*/
                } else {
                    UIUtils.toast("用户名或密码不能为空", false);
                }
                break;
            default:
                break;
        }
    }
}
