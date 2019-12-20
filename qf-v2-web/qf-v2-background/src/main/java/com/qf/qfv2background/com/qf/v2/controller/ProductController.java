package com.qf.qfv2background.com.qf.v2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author chenzetao
 * @Date 2019/12/20
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @RequestMapping("list")
    public String query(){
        return "product/list";
    }
}
