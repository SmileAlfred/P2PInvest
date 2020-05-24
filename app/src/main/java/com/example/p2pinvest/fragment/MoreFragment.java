package com.example.p2pinvest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p2pinvest.R;
import com.example.p2pinvest.common.BaseFragment;
import com.loopj.android.http.RequestParams;

/**
 * @author shkstart
 * @date 2016/11/30 0030
 */
public class MoreFragment extends BaseFragment {

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
        return R.layout.fragment_more;
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
        tvTitle.setText("更多");
        ivTitleSetting.setVisibility(View.GONE);
    }


}
