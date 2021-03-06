package com.example.p2pinvest.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.p2pinvest.R;
import com.example.p2pinvest.adapter.ProductAdapter2;
import com.example.p2pinvest.adapter.ProductAdapter4;
import com.example.p2pinvest.bean.Product;
import com.example.p2pinvest.common.AppNetConfig;
import com.example.p2pinvest.common.BaseFragment;
import com.loopj.android.http.RequestParams;

import java.util.List;

/**
 * @author shkstart
 * @date 2016/12/5 0005
 * ListView的使用：①ListView ②BaseAdapter ③Item Layout ④集合数据 (联网获取数据）
 */
public class ProductListFragment extends BaseFragment {
    private List<Product> productList;
    private TextView tvProductTitle;
    private ListView lvProductList;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.PRODUCT;
    }

    @Override
    protected void initData(String content) {
        //实现文本跑马灯效果 方式一：使得当前的textView获取焦点
        //tvProductTitle.setFocusable(true);
        //tvProductTitle.setFocusableInTouchMode(true);
        //tvProductTitle.requestFocus();
        //实现文本跑马灯效果 方式二：提供TextView的子类，重写isFocus(),返回true即可。

        JSONObject jsonObject = JSON.parseObject(content);
        boolean success = jsonObject.getBoolean("success");
        if (success) {
            String data = jsonObject.getString("data");
            //获取集合数据
            productList = JSON.parseArray(data, Product.class);
            Log.i("TAG", ProductListFragment.class.getSimpleName() + " + initData: " + productList);
            //方式一：没有抽取
            //ProductAdapter productAdapter = new ProductAdapter(productList);
            //lvProductList.setAdapter(productAdapter);//显示列表

            //方式二：抽取了getView()，但是抽取力度小 （可以作为选择）
            //ProductAdapter1 productAdapter1 = new ProductAdapter1(productList);
            //lvProductList.setAdapter(productAdapter1);//显示列表

            //方式三：抽取了，但是没有复用ViewHolder，getView()优化的不够
            //ProductAdapter2 productAdapter2 = new ProductAdapter2(productList);
            //lvProductList.setAdapter(productAdapter2);//显示列表

            //方式四：抽取了，最好的方式.（可以作为选择）
            //通用：拿去BaseHolder4.java，MyBaseAdapter4.java，重写MyHolder4.java，ProductAdapter4java
            ProductAdapter4 productAdapter4 = new ProductAdapter4(productList);
            lvProductList.setAdapter(productAdapter4);//显示列表
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_productlist;
    }

    @Override
    protected void findViews(View view) {
        tvProductTitle = view.findViewById(R.id.tv_product_title);
        lvProductList = view.findViewById(R.id.lv_product_list);
    }
}
