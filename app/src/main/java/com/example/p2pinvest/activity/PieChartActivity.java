package com.example.p2pinvest.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.p2pinvest.R;
import com.example.p2pinvest.common.BaseActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivTitleBack;
    private TextView tvTitle;
    private ImageView ivTitleSetting;
    private PieChart pieChart;
    //声明字体库
    private Typeface mTf;

    @Override
    protected void findViews() {
        ivTitleBack = findViewById(R.id.iv_title_back);
        tvTitle = findViewById(R.id.tv_title);
        ivTitleSetting = findViewById(R.id.iv_title_setting);
        pieChart = findViewById(R.id.pie_chart);

        ivTitleBack.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_back:
                removeCurrentActivity(PieChartActivity.this);
                break;
            default:
                break;
        }
    }
    @Override
    protected void initData() {
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        // apply styling
        pieChart.setDescription("android厂商2016年手机占有率");
        pieChart.setHoleRadius(52f);//最内层的圆的半径
        pieChart.setTransparentCircleRadius(57f);//包裹内层圆的半径
        pieChart.setCenterText("Android\n厂商占比");
        pieChart.setCenterTextTypeface(mTf);
        pieChart.setCenterTextSize(22f);
        //是否使用百分比。true:各部分的百分比的和是100%。
        pieChart.setUsePercentValues(true);

        PieData pieData = generateDataPie();
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTypeface(mTf);
        pieData.setValueTextSize(14f);
        pieData.setValueTextColor(Color.RED);
        // set data
        pieChart.setData(pieData);

        //获取右上角的描述结构的对象
        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setYEntrySpace(3f);//相邻的entry在y轴上的间距
        l.setYOffset(10f);//第一个entry距离最顶端的间距

        // do not forget to refresh the chart
        // pieChart.invalidate();
        pieChart.animateXY(900, 900);
    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("饼状图demo");
        ivTitleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pie_chart;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private PieData generateDataPie() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < 4; i++) {
            entries.add(new Entry((int) (Math.random() * 70) + 30, i));
        }

        PieDataSet d = new PieDataSet(entries, "");

        // 相邻模块的间距
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData cd = new PieData(getQuarters(), d);
        return cd;
    }

    private ArrayList<String> getQuarters() {

        ArrayList<String> q = new ArrayList<String>();
        q.add("三星");
        q.add("华为");
        q.add("oppo");
        q.add("vivo");

        return q;
    }
}