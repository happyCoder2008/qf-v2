package com.qf.qfv2search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v2.api.ISearchService;
import com.qf.v2.common.pojo.ResutlBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author chenzetao
 * @Date 2019/12/25
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @RequestMapping("init")
    @ResponseBody
    public ResutlBean initAllData(){
       return searchService.initAllData();
    }
}
