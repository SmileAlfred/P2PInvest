package com.example.p2pinvest.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.p2pinvest.R;
import com.example.p2pinvest.common.BaseActivity;
import com.example.p2pinvest.util.UIUtils;


public class TiXianActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivTitleBack;
    private TextView tvTitle;
    private ImageView ivTitleSetting;
    private TextView accountZhifubao;
    private RelativeLayout selectBank;
    private TextView chongzhiText;
    private View view;
    private EditText etInputMoney;
    private TextView chongzhiText2;
    private TextView textView5;
    private Button btnTixian;

    @Override
    protected void findViews() {
        ivTitleBack = findViewById(R.id.iv_title_back);
        tvTitle = findViewById(R.id.tv_title);
        ivTitleSetting = findViewById(R.id.iv_title_setting);
        accountZhifubao = findViewById(R.id.account_zhifubao);
        selectBank = findViewById(R.id.select_bank);
        chongzhiText = findViewById(R.id.chongzhi_text);
        view = findViewById(R.id.view);
        etInputMoney = findViewById(R.id.et_input_money);
        chongzhiText2 = findViewById(R.id.chongzhi_text2);
        textView5 = findViewById(R.id.textView5);
        btnTixian = findViewById(R.id.btn_tixian);

        ivTitleBack.setOnClickListener(this);
        btnTixian.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //设置当前的体现的button是不可操作的
        btnTixian.setClickable(false);
        etInputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String money = etInputMoney.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    //设置button不可操作的
                    btnTixian.setClickable(false);
                    //修改背景颜色
                    btnTixian.setBackgroundResource(R.drawable.btn_02);
                } else {
                    //设置button可操作的
                    btnTixian.setClickable(true);
                    //修改背景颜色
                    btnTixian.setBackgroundResource(R.drawable.btn_01);
                }
            }
        });
    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("提现");
        ivTitleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ti_xian;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                removeCurrentActivity(TiXianActivity.this);
                break;
            case R.id.btn_tixian:
                //将要提现的数据数额发送给后台，由后台连接第三方支付平台，完成金额的提现操作。（略）
                //提示用户信息：
                UIUtils.toast("您的提现申请已被成功受理。审核通过后，24小时内，你的钱自然会到账", false);

                UIUtils.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        removeCurrentActivity(TiXianActivity.this);
                    }
                }, 2000);
                break;
            default:
                break;
        }
    }
}
