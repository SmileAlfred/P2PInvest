package com.example.p2pinvest.adapter;

import android.view.View;

/**
 * @author LiuSaiSai
 * @description:
 * @date :2020/05/28 13:11
 */
public abstract class BaseHolder4<T> {
    private View rootView;

    private T data;

    public BaseHolder4(){
        rootView = initView();
        rootView.setTag(this);
        findViews(rootView);
    }

    protected abstract void findViews(View view);

    //提供item的布局
    protected abstract View initView();

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        refreshData();
    }
    //装配过程
    protected abstract void refreshData();

    public View getRootView() {
        return rootView;
    }
}
