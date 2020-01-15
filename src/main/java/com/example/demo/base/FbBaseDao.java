package com.example.demo.base;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author mh
 * @Date 2019/2/22
 */
public interface FbBaseDao<E> {
    E selectByPrimaryKey(Object id);

    List<E> selectByExample(E example);

    List<E> selectByExample(Map<?, ?> map);

    List<E> selectByFilter(Map<?, ?> filter);

    List<Map> selectWithColumn(E example);

    List<Map> selectColumn();

    List<Map> selectMapByFilter(Map<?, ?> filter);

}



