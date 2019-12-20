package com.qf.qfv2background.com.qf.v2.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v2.api.IProductService;
import com.qf.v2.entity.TProduct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author chenzetao
 * @Date 2019/12/20
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @Reference
    private IProductService productService;


    @RequestMapping("list")
    public String query(ModelMap map){
        List<TProduct> list = productService.getList();
        map.put("productList",list);
        return "product/list";
    }
}
