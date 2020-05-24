package com.example.p2pinvest.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.p2pinvest.R;
import com.example.p2pinvest.common.BaseFragment;
import com.loopj.android.http.RequestParams;


/**
 * Created by shkstart on 2016/11/30 0030.
 */
public class MeFragment extends BaseFragment {

    private ImageView ivTitleBack;
    private TextView tvTitle;
    private ImageView ivTitleSetting;

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void findViews(View view) {
        ivTitleBack = view.findViewById(R.id.iv_title_back);
        tvTitle = view.findViewById(R.id.tv_title);
        ivTitleSetting = view.findViewById(R.id.iv_title_setting);
    }

    @Override
    protected void initData(String content) {

    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("我的资产");
        ivTitleSetting.setVisibility(View.GONE);
    }
}
