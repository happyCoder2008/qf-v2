package com.qf.v2.api;

import com.qf.v2.common.pojo.PageResultBean;
import com.qf.v2.common.pojo.ResutlBean;
import com.qf.v2.entity.TProduct;

import java.util.List;

/**
 * @Author chenzetao
 * @Date 2019/12/25
 */
public interface ISearchService {
    ResutlBean initAllData();

    ResutlBean updateById(Long id);

    List<TProduct> queryByKeywords(String keyword);

    PageResultBean<TProduct> queryByKeywords(String keyword, Integer pageIndex, Integer pageSize);
}
