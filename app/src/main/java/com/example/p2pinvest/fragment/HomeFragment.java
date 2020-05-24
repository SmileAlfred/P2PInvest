package com.example.p2pinvest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.p2pinvest.R;
import com.example.p2pinvest.bean.Image;
import com.example.p2pinvest.bean.Index;
import com.example.p2pinvest.bean.Product;
import com.example.p2pinvest.common.AppNetConfig;
import com.example.p2pinvest.common.BaseFragment;
import com.example.p2pinvest.ui.RoundProgress;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shkstart
 * @date 2016/11/30 0030
 */
public class HomeFragment extends BaseFragment {

    private ImageView ivTitleBack;
    private TextView tvTitle;
    private ImageView ivTitleSetting;

    private TextView tvHomeProduct;
    private TextView tvHomeYearrate;
    private Banner banner;

    private RoundProgress roundProHome;
    private static final String TAG = HomeFragment.class.getSimpleName();

    private Index index;
    private int currentProress;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            roundProHome.setMax(100);
            for (int i = 0; i < currentProress; i++) {
                roundProHome.setProgress(i + 1);

                SystemClock.sleep(20);
                //强制重绘
                //roundProHome.invalidate();//只有主线程才可以如此调用
                roundProHome.postInvalidate();//主线程、分线程都可以如此调用
            }
        }
    };

    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        //Log.i(TAG, "initData: 我进来联网了"  + AppNetConfig.INDEX );
        return AppNetConfig.INDEX;
    }

    @Override
    protected void findViews(View view) {
        ivTitleBack = view.findViewById(R.id.iv_title_back);
        tvTitle = view.findViewById(R.id.tv_title);
        ivTitleSetting = view.findViewById(R.id.iv_title_setting);

        tvHomeProduct = view.findViewById(R.id.tv_home_product);
        tvHomeYearrate = view.findViewById(R.id.tv_home_yearrate);
        banner = view.findViewById(R.id.banner);
        roundProHome = view.findViewById(R.id.roundPro_home);
    }

    @Override
    protected void initData(String content) {
        //即便是传入的地址 错误也应该显示 默认的界面
        if (!TextUtils.isEmpty(content)) {
            Log.i(TAG, "initData: 我进来联网了");
            index = new Index();
            JSONObject jsonObject = JSON.parseObject(content);
            //解析json对象数据
            String proInfo = jsonObject.getString("proInfo");
            Product product = JSON.parseObject(proInfo, Product.class);
            //解析json数组数据
            String imageArr = jsonObject.getString("imageArr");
            List<Image> images = JSON.parseArray(imageArr, Image.class);
            index.product = product;
            index.images = images;

            //更新页面数据
            tvHomeProduct.setText(product.name);//"硅谷彩虹新手计划"
            Log.i(TAG, "initData: 我进来联网了" + product.name);
            tvHomeYearrate.setText(product.yearRate + "%");//8.00

            //获取数据中的进度值
            currentProress = Integer.parseInt(index.product.progress);
            //在分线程中，实现进度的动态变化
            new Thread(runnable).start();

            //设置banner样式
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片地址构成的集合
            ArrayList<String> imagesUrl = new ArrayList<String>(index.images.size());
            //for(int i = 0; i < imagesUrl.size(); i++) {
            // imagesUrl.size():0;虽然建的list.size =4，但是还没有添加，此时的size = 0；
            for (int i = 0; i < index.images.size(); i++) {//index.images.size():4
                imagesUrl.add(AppNetConfig.BASE_URL + index.images.get(i).IMAURL);
                Log.i(TAG, "onSuccess: " + index.images.get(i).IMAURL);
            }
            banner.setImages(imagesUrl);
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.DepthPage);
            //设置标题集合（当banner样式有显示title时）
            String[] titles = new String[]{"分享砍学费", "人脉总动员", "想不到你是这样的app", "购物节，爱不单行"};
            banner.setBannerTitles(Arrays.asList(titles));
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(1500);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.CENTER);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }
    }

    @Override
    public void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("首页");
        ivTitleSetting.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             常用的图片加载库：
             Universal Image Loader：一个强大的图片加载库，包含各种各样的配置，最老牌，使用也最广泛。
             Picasso: Square出品，必属精品。和OkHttp搭配起来更配呦！
             Volley ImageLoader：Google官方出品，可惜不能加载本地图片~
             Fresco：Facebook出的，天生骄傲！不是一般的强大。
             Glide：Google推荐的图片加载库，专注于流畅的滚动。
             */

            //Picasso 加载图片简单用法
            Picasso.get().load((String) path).into(imageView);
        }
    }
}
