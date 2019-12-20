package com.qf.v2.common.base;

import java.util.List;

/**
 * @Author chenzetao
 * @Date 2019/12/19
 */
public interface IBaseDao<T> {

    int deleteByPrimaryKey(Long id);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);

    List<T> list();
}
