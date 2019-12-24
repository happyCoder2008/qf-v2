package com.qf.qfv2background.com.qf.v2.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.v2.api.IProductTypeService;
import com.qf.v2.entity.TProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author chenzetao
 * @Date 2019/12/20
 */
@RestController
@RequestMapping("productType")
public class ProductTypeController {

    @Reference
    private IProductTypeService productTypeService;

    @GetMapping("getList")
    public List<TProductType> list(){
        return productTypeService.getList();
    }


    @GetMapping("getListByJsonP")
    public String getListByJsonP(String callback){
        //获取要回调的客户端函数
        System.out.println("callback: "+callback);
        //回调这个函数
        List<TProductType> list = productTypeService.getList();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return callback+"("+json+")";
        //jsonp = json +padding
    }
}
