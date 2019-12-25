package com.qf.qfv2searchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v2.api.ISearchService;
import com.qf.v2.common.pojo.PageResultBean;
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

import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    //全量复制
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

    //增量复制
    @Override
    public ResutlBean updateById(Long id) {
        //查询所有的商品信息
        TProduct product= productMapper.selectByPrimaryKey(id);
        //生成一个document，把数据库数据同步到索引库
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

        //1.设置查询条件
        SolrQuery condition = new SolrQuery();
        if(!StringUtils.isAllEmpty(keyword)){
            condition.setQuery("product_keywords:"+keyword);
        }else{
            condition.setQuery("product_keywords:iphoneX");
        }

        //2.设置高亮信息
        condition.setHighlight(true);
        condition.addHighlightField("product_name");
        condition.addHighlightField("product_sale_point");
        //在高亮信息前后加上样式
        condition.setHighlightSimplePre("<font color='red'>");
        condition.setHighlightSimplePost("</font>");

        List<TProduct> productList = null;
        try {
            //执行查询
            QueryResponse queryResponse = solrClient.query(condition);
            //得到结果
            SolrDocumentList list = queryResponse.getResults();

            //获取高亮信息
            //map中的key值string，表示查询到的高亮信息的id
            //map中的value值map,表示查询到的高亮信息的集合
            //Map<String, List<String>>  集合中的key，表示高亮的字段名称
            //Map<String, List<String>>  集合中的value，表示具体的高亮信息
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

            productList = new ArrayList<>(list.size());
            //SolrDocumentList -- List<TProduct>
            for (SolrDocument document : list) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
//                product.setName(document.getFieldValue("product_name").toString());
                product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                product.setImage(document.getFieldValue("product_images").toString());
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));

                //能过id获取当前字段的高亮信息
                Map<String, List<String>> map = highlighting.get(document.getFieldValue("id").toString());
                //获取product_name字段的高亮信息
                List<String> hightlightList = map.get("product_name");
                if(hightlightList!=null && hightlightList.size()>0){
                    //设置高亮信息
                    product.setName(hightlightList.get(0));
                }else{
                    //普通的信息
                    product.setName(document.getFieldValue("product_name").toString());
                }

                //获取product_sale_point字段的高亮信息
                List<String> salePointhightlightList = map.get("product_sale_point");
                if(salePointhightlightList!=null && salePointhightlightList.size()>0){
                    //重新设置高亮信息
                    product.setSalePoint(salePointhightlightList.get(0));
                }else{
                    //普通的信息
                    product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                }
                productList.add(product);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public PageResultBean<TProduct> queryByKeywords(String keyword, Integer pageIndex, Integer pageSize) {
       PageResultBean<TProduct> pageResultBean = new PageResultBean<>();
        //1.设置查询条件
        SolrQuery condition = new SolrQuery();
        if(!StringUtils.isAllEmpty(keyword)){
            condition.setQuery("product_keywords:"+keyword);
        }else{
            condition.setQuery("product_keywords:*");
        }

        //2.设置高亮信息
        condition.setHighlight(true);
        condition.addHighlightField("product_name");
        condition.addHighlightField("product_sale_point");
        //在高亮信息前后加上样式
        condition.setHighlightSimplePre("<font color='red'>");
        condition.setHighlightSimplePost("</font>");

        //3.分页设置
        condition.setStart((pageIndex-1)*pageSize);
        condition.setRows(pageSize);

        List<TProduct> productList = null;
        long total = 0L;
        try {
            //执行查询
            QueryResponse queryResponse = solrClient.query(condition);
            //得到结果
            SolrDocumentList list = queryResponse.getResults();

            //可以得到分页的总条数
            total = list.getNumFound();
            //获取高亮信息
            //map中的key值string，表示查询到的高亮信息的id
            //map中的value值map,表示查询到的高亮信息的集合
            //Map<String, List<String>>  集合中的key，表示高亮的字段名称
            //Map<String, List<String>>  集合中的value，表示具体的高亮信息
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

            productList = new ArrayList<>(list.size());
            //SolrDocumentList -- List<TProduct>
            for (SolrDocument document : list) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
//                product.setName(document.getFieldValue("product_name").toString());
                product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                product.setImage(document.getFieldValue("product_images").toString());
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));

                //能过id获取当前字段的高亮信息
                Map<String, List<String>> map = highlighting.get(document.getFieldValue("id").toString());
                //获取product_name字段的高亮信息
                List<String> hightlightList = map.get("product_name");
                if(hightlightList!=null && hightlightList.size()>0){
                    //设置高亮信息
                    product.setName(hightlightList.get(0));
                }else{
                    //普通的信息
                    product.setName(document.getFieldValue("product_name").toString());
                }

                //获取product_sale_point字段的高亮信息
                List<String> salePointhightlightList = map.get("product_sale_point");
                if(salePointhightlightList!=null && salePointhightlightList.size()>0){
                    //重新设置高亮信息
                    product.setSalePoint(salePointhightlightList.get(0));
                }else{
                    //普通的信息
                    product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                }
                productList.add(product);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        //给对象的属性赋值
        pageResultBean.setPageNum(pageIndex);
        pageResultBean.setPageSize(pageSize);
        pageResultBean.setTotal(total);
        pageResultBean.setPages((int) (total%pageSize==0?(total/pageSize):(total/pageSize)+1));
        pageResultBean.setList(productList);
        return pageResultBean;
    }


}
