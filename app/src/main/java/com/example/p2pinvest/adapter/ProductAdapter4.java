package com.example.p2pinvest.adapter;

import com.example.p2pinvest.bean.Product;

import java.util.List;

/**
 * @author LiuSaiSai
 * @description:
 * @date :2020/05/28 13:07
 */
public class ProductAdapter4 extends MyBaseAdapter4<Product> {
    public ProductAdapter4(List<Product> list) {
        super(list);
    }

    @Override
    protected BaseHolder4<Product> getHolder() {
        return new MyHolder4();
    }
}
