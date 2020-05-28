package com.example.p2pinvest.fragment;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.p2pinvest.R;
import com.example.p2pinvest.common.BaseFragment;
import com.example.p2pinvest.ui.FlowLayout;
import com.example.p2pinvest.util.DrawUtils;
import com.example.p2pinvest.util.UIUtils;
import com.loopj.android.http.RequestParams;

import java.util.Random;

/**
 * @author shkstart
 * @date 2016/12/5 0005
 */
public class ProductHotFragment extends BaseFragment {
    private FlowLayout flowHot;
    /**
     * 提供数据，动态添加到布局中
     */
    private String[] datas = new String[]{
            "认罪认罚从宽",
            "法不能向不法让步",
            "AG赢了",
            "公益诉讼",
            "群众信访件件有回复",
            "乃万甩话筒",
            "CSDN粉丝12",
            "大学老师购买车辆",
            "天气热",
            "美人鱼影视拍摄投资",
            "Android培训老师自己周转",
            "开斋节",
            "北京代表团",
            "喻言变温柔了",
            "待你不薄"
    };

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initData(String content) {
        for (int i = 0; i < datas.length; i++) {
            final TextView tv = new TextView(getContext());

            //设置属性
            tv.setText(datas[i]);
            //设置margin边距
            ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);
            mp.leftMargin = UIUtils.dp2px(5);
            mp.rightMargin = UIUtils.dp2px(5);
            mp.topMargin = UIUtils.dp2px(5);
            mp.bottomMargin = UIUtils.dp2px(5);
            tv.setLayoutParams(mp);
            /**
             * 设置 padding 内边距
             */
            int padding = UIUtils.dp2px(2);
            tv.setPadding(padding, padding, padding, padding);
            tv.setTextSize(UIUtils.dp2px(8));

            Random random = new Random();
            int red = random.nextInt(211);
            int green = random.nextInt(211);
            int blue = random.nextInt(211);
            //设置单一背景
            //tv.setBackground(DrawUtils.getDrawable(Color.rgb(red,green,blue),UIUtils.dp2px(5)));
            //设置具有选择器功能的背景
            tv.setBackground(DrawUtils.getSelector(DrawUtils.getDrawable(Color.rgb(red, green, blue)
                    , UIUtils.dp2px(5)), DrawUtils.getDrawable(Color.WHITE, UIUtils.dp2px(5))));
            //设置textView是可点击的.如果设置了点击事件，则TextView就是可点击的。
            //tv.setClickable(true);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIUtils.toast(tv.getText().toString(), false);
                }
            });
            flowHot.addView(tv);
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_producthot;
    }

    @Override
    protected void findViews(View view) {
        flowHot = view.findViewById(R.id.flow_hot);
    }
}
