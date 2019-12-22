package com.qf.v2.api;

import com.github.pagehelper.PageInfo;
import com.qf.v2.common.base.IBaseService;
import com.qf.v2.entity.TProduct;
import com.qf.v2.vo.ProductVO;

/**
 * @Author chenzetao
 * @Date 2019/12/20
 */
public interface IProductService extends IBaseService<TProduct> {
    PageInfo<TProduct> getPage(Integer currentPage, Integer pageSize);

    Long addProduct(ProductVO productVO);
}
