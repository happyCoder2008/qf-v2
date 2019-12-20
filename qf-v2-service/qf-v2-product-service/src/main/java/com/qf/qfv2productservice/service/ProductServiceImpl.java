package com.qf.qfv2productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v2.api.IProductService;
import com.qf.v2.common.base.BaseServiceImpl;
import com.qf.v2.common.base.IBaseDao;
import com.qf.v2.entity.TProduct;
import com.qf.v2.mapper.TProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
