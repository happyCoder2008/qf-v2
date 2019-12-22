package com.qf.qfv2background.com.qf.v2.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.qf.v2.api.IProductService;
import com.qf.v2.entity.TProduct;
import com.qf.v2.vo.ProductVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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

    //商品分页
    @RequestMapping("page/{currentPage}/{pageSize}")
    public String query(ModelMap map,
                        @PathVariable Integer currentPage,
                        @PathVariable Integer pageSize){
        PageInfo<TProduct> pageInfo = productService.getPage(currentPage,pageSize);
        map.put("pageInfo",pageInfo);
        return "product/list";
    }

    //添加商品
    @RequestMapping("add")
    public String addProduct(ProductVO productVO){
        //添加商品
       Long productId = productService.addProduct(productVO);
       //返回的商品ID后续有用
        return "redirect:/product/page/1/1";
    }
}
