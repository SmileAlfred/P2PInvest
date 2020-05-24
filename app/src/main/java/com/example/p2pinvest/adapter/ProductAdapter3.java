package com.example.p2pinvest.adapter;

import com.example.p2pinvest.bean.Product;

import java.util.List;

/**
 * Created by shkstart on 2016/12/5 0005.
 */
public class ProductAdapter3 extends MyBaseAdapter3<Product> {
    public ProductAdapter3(List<Product> list) {
        super(list);
    }

    @Override
    protected BaseHolder<Product> getHolder() {
        return new MyHolder();
    }
}
