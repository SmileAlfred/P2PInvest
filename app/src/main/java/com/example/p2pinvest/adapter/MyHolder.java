package com.example.p2pinvest.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.p2pinvest.R;
import com.example.p2pinvest.bean.Product;
import com.example.p2pinvest.ui.RoundProgress;
import com.example.p2pinvest.util.UIUtils;

/**
 * Created by shkstart on 2016/12/5 0005.
 * 通用：拿去BaseHolder.java，MyBaseAdapter3.java，重写MyHolder.java，.ProductAdapter3java
 */
public class MyHolder extends BaseHolder<Product> {
    private TextView pName;
    private TextView pMoney;
    private TextView pYearlv;
    private TextView pSuodingdays;
    private TextView pMinzouzi;
    private TextView pMinnum;
    private RoundProgress pProgresss;


    @Override
    protected void findViews(View view) {
        pName = view.findViewById(R.id.p_name);
        pMoney = view.findViewById(R.id.p_money);
        pYearlv = view.findViewById(R.id.p_yearlv);
        pSuodingdays = view.findViewById(R.id.p_suodingdays);
        pMinzouzi = view.findViewById(R.id.p_minzouzi);
        pMinnum = view.findViewById(R.id.p_minnum);
        pProgresss = view.findViewById(R.id.p_progresss);
    }

    @Override
    protected View initView() {
        return View.inflate(UIUtils.getContext(), R.layout.item_product_list, null);
    }

    @Override
    protected void refreshData() {
        Product data = this.getData();

        //装数据
        pMinnum.setText(data.memberNum);
        pMinzouzi.setText(data.minTouMoney);
        pMoney.setText(data.money);
        pName.setText(data.name);
        pProgresss.setProgress(Integer.parseInt(data.progress));
        pSuodingdays.setText(data.suodingDays);
        pYearlv.setText(data.yearRate);
    }
}
