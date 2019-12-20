package com.qf.qfv2productservice.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Author chenzetao
 * @Date 2019/12/20
 *
 * 用对象的方式替换掉分页插件配置的方式
 */
@Configuration //参数配置
public class CommonConfig {

    @Bean
    public PageHelper getPageHelper(){
        PageHelper pageHelper = new PageHelper();
        //设置参数
        Properties properties = new Properties();
        //设置分页插件的参数
        properties.setProperty("dialect","mysql");
        properties.setProperty("reasonable","true");
        //设置到pageHepler
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
