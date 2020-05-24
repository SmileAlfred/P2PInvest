package com.example.p2pinvest.adapter;

import android.view.View;

/**
 *通用：拿去BaseHolder.java，MyBaseAdapter3.java，重写MyHolder.java，.ProductAdapter3java
 * @author shkstart
 * @date 2016/12/5 0005
 */
public abstract class BaseHolder<T> {
    private View rootView;

    private T data;

    public BaseHolder(){
        rootView = initView();
        rootView.setTag(this);
        findViews(initView());
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
