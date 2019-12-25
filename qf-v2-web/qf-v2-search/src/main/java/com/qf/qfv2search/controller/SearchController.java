package com.qf.qfv2search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v2.api.ISearchService;
import com.qf.v2.common.pojo.PageResultBean;
import com.qf.v2.common.pojo.ResutlBean;
import com.qf.v2.entity.TProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author chenzetao
 * @Date 2019/12/25
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    //全量复制数据到索引库
    @RequestMapping("init")
    @ResponseBody
    public ResutlBean initAllData(){
       return searchService.initAllData();
    }


    //条件搜索
    @RequestMapping("queryByKeywords")
    public String queryByKeywords(String keyword, ModelMap map){
        List<TProduct> list = searchService.queryByKeywords(keyword);
        map.put("list",list);
        return "search";
    }

    //条件搜索加分页,从索引库查询分页信息
    @RequestMapping("queryByKeywords/{pageIndex}/{pageSize}")
    public String queryAndPage(String keyword
                               , ModelMap map
                                , @PathVariable Integer pageIndex
                                , @PathVariable Integer pageSize){
        PageResultBean<TProduct> pageResultBean = searchService.queryByKeywords(keyword, pageIndex, pageSize);
        map.put("page",pageResultBean);
        return "search";
    }
}
