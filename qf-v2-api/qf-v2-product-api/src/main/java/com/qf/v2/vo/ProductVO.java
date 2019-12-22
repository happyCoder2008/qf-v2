package com.qf.v2.vo;

import com.qf.v2.entity.TProduct;

import java.io.Serializable;

/**
 * @Author chenzetao
 * @Date 2019/12/20
 */
public class ProductVO implements Serializable {
    private TProduct product;
    private String productDesc;

    public TProduct getProduct() {
        return product;
    }

    public void setProduct(TProduct product) {
        this.product = product;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
