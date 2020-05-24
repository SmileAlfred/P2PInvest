package com.example.p2pinvest.common;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.p2pinvest.R;
import com.example.p2pinvest.ui.LoadingPage;
import com.example.p2pinvest.util.UIUtils;
import com.loopj.android.http.RequestParams;


/**
 * @author shkstart
 * @date 2016/12/3 0003
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPage loadingPage =  null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("BaseFragment", "initData: 我进来联网了" + "BaseFragment 中 onCreateView生命周期");
        loadingPage = new LoadingPage(container.getContext()) {
            @Override
            public int layoutId() {
                return getLayoutId();
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            public String url() {
                return getUrl();
            }

            /**
             * 其实 view_success 的数据，在 LP 中的方法：“ view_success = UIUtils.getView(layoutId());
             * addView(view_success, params);”中已经指定了；所以这里不需要再次 inflate
             * @param resultState 联网解析的状态：成功(数据有无)，失败，加载中
             * @param view_success 加载的布局
             */
            @Override
            public void onSuccess(LoadingPage.ResultState resultState, View view_success) {
                //view_success = UIUtils.getView(getLayoutId());多此一举，因为这行代码我的app数据被写死了…
                //addView(view_success);多此一举，因为这行代码，我的APP看起来像散光一样，重影了……
                findViews(view_success);
                initTitle();
                initData(resultState.getContent());
            }
        };
        return loadingPage;
    }

    /**
     * 为保证 loadingPage 不为null
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        loadingPage.show();
        Log.i("BaseFragment", "initData: 我进来联网了" + "BaseFragment 中生命周期 onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    protected abstract String getUrl();

    protected abstract RequestParams getParams();

    //提供布局
    public abstract int getLayoutId();

    protected abstract void findViews(View view);

    //初始化界面的数据
    protected abstract void initData(String content);

    //初始化title
    protected abstract void initTitle();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
