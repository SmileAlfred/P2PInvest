package com.example.p2pinvest.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.p2pinvest.R;
import com.example.p2pinvest.util.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author shkstart
 * @date 2016/12/3 0003
 * 一个 BUG 改一天的第一天：handler.postDelayed() 失效，解决办法：handler 整成 主线程的；
 * 在加载布局<或者应用主题>出现 失效情况时，考虑上下文 context 是不是传进去的是 application；
 */
public abstract class LoadingPage extends FrameLayout {

    //1.定义4种不同的显示状态
    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_EMPTY = 3;
    private static final int STATE_SUCCESS = 4;
    //默认情况下，当前状态为正在加载
    private int state_current = STATE_LOADING;
    /**
     * 传入一个 activity 上下文；因为 UiUtils 中提供的是 application；在加载布局时会造成冲突
     */
    private Context mContext;

    //2.提供4种不同的界面
    private View view_loading;
    private View view_error;
    private View view_empty;
    private View view_success;
    private LayoutParams params;
    public static final String TAG = LoadingPage.class.getSimpleName();

    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    //初始化方法
    private void init() {
        //实例化view
        //1.提供布局显示的参数
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (view_loading == null) {
            //2.加载布局
            view_loading = UIUtils.getView(R.layout.page_loading);
            //3.添加到当前的frameLayout中
            addView(view_loading, params);
        }

        if (view_empty == null) {
            //2.加载布局
            view_empty = UIUtils.getView(R.layout.page_empty);
            //3.添加到当前的frameLayout中
            addView(view_empty, params);
        }

        if (view_error == null) {
            //2.加载布局
            view_error = UIUtils.getView(R.layout.page_error);
            //3.添加到当前的frameLayout中
            addView(view_error, params);
        }
        showSafePage();
    }

    //保证如下的操作在主线程中执行的：更新界面
    private void showSafePage() {
        UIUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //保证run()中的操作在主线程中执行
                showPage();
            }
        });
    }

    private void showPage() {
        //根据当前state_current的值，决定显示哪个view
        view_loading.setVisibility(state_current == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        view_error.setVisibility(state_current == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        view_empty.setVisibility(state_current == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);

        if (view_success == null) {
            //加载布局使用的是 Application;这就造成布局中自定义的 主题失效
            //view_success = UIUtils.getView(layoutId());
            view_success = View.inflate(mContext, layoutId(), null);
            addView(view_success, params);
        }
        view_success.setVisibility(state_current == STATE_SUCCESS ? View.VISIBLE : View.INVISIBLE);
    }

    public abstract int layoutId();

    private ResultState resultState;

    /**
     * 在show()中实现联网加载数据;如果页面本来就不需要联网，那么直接让其显示，不必延迟不必联网
     */
    public void show() {
        String url = url();
        if (TextUtils.isEmpty(url)) {
            resultState = ResultState.SUCCESS;
            resultState.setContent("");
            //修改state_current，并且决定加载哪个页面：showSafePage()
            loadImage();
            return;
        }

        UIUtils.getMainHandler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "show: url:" + " + run 执行了" + resultState);
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get(url(), params(), new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(String content) {
                                if (TextUtils.isEmpty(content)) {// "" or null
                                    //state_current = STATE_EMPTY;
                                    Log.i("LP", "我进来联网了" + "show()+" + "STATE_EMPTY+");
                                    resultState = ResultState.EMPTY;
                                    resultState.setContent("");
                                } else {
                                    //state_current = STATE_SUCCESS;
                                    resultState = ResultState.SUCCESS;
                                    resultState.setContent(content);
                                    Log.i("LP", "我进来联网了" + "show()+" + "STATE_SUCCESS+");
                                }
                                //showSafePage();
                                loadImage();
                                Log.i(TAG, "show: url:" + " + run 执行了 onSuccess + resultState=" + resultState);
                            }

                            @Override
                            public void onFailure(Throwable error, String content) {
                                //state_current = STATE_ERROR;
                                resultState = ResultState.ERROR;
                                resultState.setContent("");
                                //showSafePage();
                                loadImage();
                                Log.i(TAG, "show: url:" + " + run 执行了onFailure + resultState=" + resultState);
                            }
                        });
                    }
                }, 1000);
        //}.run();//可行
        //, 1000);
        Log.i(TAG, "show: url:" + url + " + loadingPage 的 show() 执行完了 ,resultState=" + resultState);
    }

    private void loadImage() {
        switch (resultState) {
            case ERROR:
                state_current = STATE_ERROR;
                break;
            case EMPTY:
                state_current = STATE_EMPTY;
                break;
            case SUCCESS:
                state_current = STATE_SUCCESS;
                break;
            default:
                break;
        }
        //根据修改以后的state_current，更新视图的显示。
        showSafePage();

        if (state_current == STATE_SUCCESS) {
            onSuccess(resultState, view_success);
        }
        Log.i(TAG, "loadImage:联网： " + "LP中执行了 loadImage，并且 state_current = " + state_current);
    }

    public abstract void onSuccess(ResultState resultState, View view_success);

    /**
     * 提供联网的请求参数
     */
    protected abstract RequestParams params();

    /**
     * 提供联网的请求地址
     */
    public abstract String url();

    /**
     * 提供枚举类，封装联网以后的状态值和数据
     */
    public enum ResultState {

        ERROR(2), EMPTY(3), SUCCESS(4);

        int state;

        ResultState(int state) {
            this.state = state;
        }

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}