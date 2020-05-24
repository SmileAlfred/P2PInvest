package com.example.p2pinvest.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.p2pinvest.R;
import com.example.p2pinvest.common.BaseFragment;
import com.example.p2pinvest.util.UIUtils;
import com.loopj.android.http.RequestParams;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shkstart
 * @date 2016/11/30 0030
 * ???.getFragmentManager() 过时
 */
public class InvestFragment extends BaseFragment {

    private ImageView ivTitleBack;
    private TextView tvTitle;
    private ImageView ivTitleSetting;

    private TabPageIndicator tabpageInvest;
    private ViewPager vpInvest;

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected RequestParams getParams() {
        return null;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest;
    }

    @Override
    protected void findViews(View view) {
        ivTitleBack = view.findViewById(R.id.iv_title_back);
        tvTitle = view.findViewById(R.id.tv_title);
        ivTitleSetting = view.findViewById(R.id.iv_title_setting);
        tabpageInvest = view.findViewById(R.id.tabpage_invest);
        vpInvest = view.findViewById(R.id.vp_invest);
    }

    @Override
    protected void initData(String content) {
        //1.加载三个不同的Fragment：ProductListFragment,ProductRecommondFragment,ProductHotFragment.
        initFragments();
        //2.ViewPager设置三个Fragment的显示
        MyAdapter adapter = new MyAdapter(getFragmentManager());
        vpInvest.setAdapter(adapter);
        //将TabPagerIndicator与ViewPager关联
        tabpageInvest.setViewPager(vpInvest);
    }

    private List<Fragment> fragmentList = new ArrayList<>();

    private void initFragments() {
        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommondFragment productRecommondFragment = new ProductRecommondFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();
        //添加到集合中
        fragmentList.add(productListFragment);
        fragmentList.add(productRecommondFragment);
        fragmentList.add(productHotFragment);
    }

    /**
     * 提供PagerAdapter的实现
     * 如果ViewPager中加载的是Fragment,则提供的Adpater可以继承于具体的：FragmentStatePagerAdapter或FragmentPagerAdapter
     * FragmentStatePagerAdapter:适用于ViewPager中加载的Fragment过多，会根据最近最少使用算法，实现内存中Fragment的清理，避免溢出
     * FragmentPagerAdapter:适用于ViewPager中加载的Fragment不多时，系统不会清理已经加载的Fragment。
     */
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }

        //提供TabPageIndicator的显示内容
        @Override
        public CharSequence getPageTitle(int position) {
            //方式一：
//            if(position == 0){
//                return "全部理财";
//            }else if(position == 1){
//                return "推荐理财";
//            }else if(position == 2){
//                return "热门理财";s
//            }
            //方式二：
            return UIUtils.getStringArr(R.array.invest_tab)[position];
        }
    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("投资");
        ivTitleSetting.setVisibility(View.GONE);
    }
}
