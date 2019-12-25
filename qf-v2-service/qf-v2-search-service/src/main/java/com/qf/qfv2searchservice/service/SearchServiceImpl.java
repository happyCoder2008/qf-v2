package com.qf.qfv2searchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v2.api.ISearchService;
import com.qf.v2.common.pojo.ResutlBean;
import com.qf.v2.entity.TProduct;
import com.qf.v2.mapper.TProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author chenzetao
 * @Date 2019/12/25
 */
@Component
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public ResutlBean initAllData() {
        //查询所有的商品信息
        List<TProduct> list = productMapper.list();
        //生成一个document，把数据库数据同步到索引库
        for (TProduct product : list) {
            //创建一个document对象
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id",product.getId());
            document.setField("product_name",product.getName());
            document.setField("product_price",product.getPrice());
            document.setField("product_sale_point",product.getSalePoint());
            document.setField("product_images",product.getImage());

            //添加
            try {
                solrClient.add(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
               return ResutlBean.error("添加到索引库失败!");
            }
        }
        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return ResutlBean.error("添加到索引库失败!");
        }
        //添加成功
        return ResutlBean.success("添加到索引库成功!");
    }

    @Override
    public List<TProduct> queryByKeywords(String keyword) {

        //设置查询条件
        SolrQuery condition = new SolrQuery();
        if(!StringUtils.isAllEmpty(keyword)){
            condition.setQuery("product_keywords:"+keyword);
        }else{
            condition.setQuery("product_keywords:*");
        }

        List<TProduct> productList = null;
        try {
            //执行查询
            QueryResponse queryResponse = solrClient.query(condition);
            //得到结果
            SolrDocumentList list = queryResponse.getResults();

            productList = new ArrayList<>(list.size());
            //SolrDocumentList -- List<TProduct>
            for (SolrDocument document : list) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
                product.setName(document.getFieldValue("product_name").toString());
                product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                product.setImage(document.getFieldValue("product_images").toString());
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));

                productList.add(product);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
