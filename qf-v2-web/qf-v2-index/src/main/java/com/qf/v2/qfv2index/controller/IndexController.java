package com.qf.v2.qfv2index.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v2.api.IProductTypeService;
import com.qf.v2.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author chenzetao
 * @Date 2019/12/23
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Reference
    private IProductTypeService productTypeService;

    @RequestMapping("show")
    public String show(ModelMap map){
        List<TProductType> list = productTypeService.getList();
        map.put("productTypeList",list);
        return "index";
    }

    //前后端分离方式，后端返回数据，前端调用接口
    @RequestMapping("list")
    @ResponseBody
    public List<TProductType> list(){
        List<TProductType> list = productTypeService.getList();
        return list;
    }
}
