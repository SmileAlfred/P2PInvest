package com.example.p2pinvest.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.p2pinvest.R;
import com.example.p2pinvest.bean.Product;
import com.example.p2pinvest.ui.RoundProgress;
import com.example.p2pinvest.util.UIUtils;

import java.util.List;

/**
 * Created by shkstart on 2016/12/5 0005.
 */
public class ProductAdapter extends BaseAdapter {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList == null ? 0 : productList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //将具体的集合数据装配到具体的一个item layout中。
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("TAG", "parent = " + parent.getClass().toString());
        Log.e("TAG", "parent.getContext() = " + parent.getContext());
        //加载不同效果示例：
        int itemViewType = getItemViewType(position);
        if (itemViewType == 0) {
            TextView tv = new TextView(parent.getContext());
            tv.setText("与子同行，奈何覆舟");
            tv.setTextColor(UIUtils.getColor(R.color.text_progress));
            tv.setTextSize(UIUtils.dp2px(20));
            return tv;
        }
        //将没有添加这个item时，后面的item正常显示，这里用的是 --
        if (position > 3) {
            position--;
        }

        ViewHolder holder;
        if (convertView == null) {
            //这里需要初始化视图时，需要上下文的activity，可以定义全局 context，然后在构造其中添加参数，由设置adapter的activity传进来；
            //也可以用如下方式 parent.getContext()；通过得到父布局 listview 获取activity；
            convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //装配数据
        Product product = productList.get(position);
        holder.pMinnum.setText(product.memberNum);
        holder.pMinzouzi.setText(product.minTouMoney);
        holder.pMoney.setText(product.money);
        holder.pName.setText(product.name);
        holder.pProgresss.setProgress(Integer.parseInt(product.progress));
        holder.pSuodingdays.setText(product.suodingDays);
        holder.pYearlv.setText(product.yearRate);

        return convertView;
    }

    //不同的position位置上，显示的具体的item的type值
    @Override
    public int getItemViewType(int position) {
        if (position == 3) {
            return 0;
        } else {
            return 1;
        }
    }

    //返回不同类型的item的个数
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    static class ViewHolder {
        TextView pName;
        TextView pMoney;
        TextView pYearlv;
        TextView pSuodingdays;
        TextView pMinzouzi;
        TextView pMinnum;
        RoundProgress pProgresss;

        ViewHolder(View view) {
            findViews(view);
        }

        private void findViews(View view) {
            pName = view.findViewById(R.id.p_name);
            pMoney = view.findViewById(R.id.p_money);
            pYearlv = view.findViewById(R.id.p_yearlv);
            pSuodingdays = view.findViewById(R.id.p_suodingdays);
            pMinzouzi = view.findViewById(R.id.p_minzouzi);
            pMinnum = view.findViewById(R.id.p_minnum);
            pProgresss = view.findViewById(R.id.p_progresss);
        }
    }
}
