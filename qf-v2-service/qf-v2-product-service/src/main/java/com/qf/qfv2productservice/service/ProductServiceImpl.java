package com.qf.qfv2productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.v2.api.IProductService;
import com.qf.v2.common.base.BaseServiceImpl;
import com.qf.v2.common.base.IBaseDao;
import com.qf.v2.entity.TProduct;
import com.qf.v2.mapper.TProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author chenzetao
 * @Date 2019/12/20
 */
@Component
@Service
public class ProductServiceImpl extends BaseServiceImpl<TProduct> implements IProductService {

    @Autowired
    private TProductMapper productMapper;

    @Override
    public IBaseDao<TProduct> getBaseDao() {
        return productMapper;
    }

    @Override
    public PageInfo<TProduct> getPage(Integer currentPage, Integer pageSize) {
        //设置分页的参数
        PageHelper.startPage(currentPage,pageSize);
        List<TProduct> list = productMapper.list();
        //后面的参数可以设置连续出现的页码个数
        PageInfo pageInfo = new PageInfo(list,3);
        return pageInfo;
    }
}
