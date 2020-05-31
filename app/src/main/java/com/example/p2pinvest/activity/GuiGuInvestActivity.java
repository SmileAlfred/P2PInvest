package com.example.p2pinvest.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.p2pinvest.R;
import com.example.p2pinvest.common.BaseActivity;
import com.example.p2pinvest.common.BaseActivity;


public class GuiGuInvestActivity extends BaseActivity {

    private ImageView ivTitleBack;
    private TextView tvTitle;
    private ImageView ivTitleSetting;

    @Override
    protected void findViews() {
        ivTitleBack = findViewById(R.id.iv_title_back);
        tvTitle = findViewById(R.id.tv_title);
        ivTitleSetting = findViewById(R.id.iv_title_setting);

        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCurrentActivity();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("关于硅谷理财");
        ivTitleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gui_gu_invest;
    }
}
