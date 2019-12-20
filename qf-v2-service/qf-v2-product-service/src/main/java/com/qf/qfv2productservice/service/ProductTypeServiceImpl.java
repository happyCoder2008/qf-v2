package com.qf.qfv2productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v2.api.IProductTypeService;
import com.qf.v2.common.base.BaseServiceImpl;
import com.qf.v2.common.base.IBaseDao;
import com.qf.v2.entity.TProductType;
import com.qf.v2.mapper.TProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author chenzetao
 * @Date 2019/12/20
 */
@Component
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<TProductType> implements IProductTypeService {

    @Autowired
    private TProductTypeMapper productTypeMapper;

    @Override
    public IBaseDao<TProductType> getBaseDao() {
        return productTypeMapper;
    }
}
