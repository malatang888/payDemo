package com.example.demo.base;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author mh
 * @Date 2019/2/22
 */
@Component
public interface FbBaseService<E> {
    void setBaseMapper();
    /**
     * 通过编号获取一个实体对象
     * @param id
     * @return
     */
    E getEntityById(final String id);


    // /**
    //  * 通过分页、排序查询实体列表
    //  * @param pageNum
    //  * @param pageSize
    //  * @param sortField
    //  * @param sortOrder
    //  * @param filter  Map类型的过滤器
    //  * @return
    //  */
    // public Page listEntityByMapFilter(Integer pageNum, Integer pageSize, String sortField, Integer sortOrder, Map<?, ?> filter);


    /**
     * 通过filter对象查询实体列表
     * @param map
     * @return
     */
    List<E> getEntityByMap(Map<?, ?> map);


    /**
     * 通过实体获取一个实体对象
     * @param entity
     * @return
     */
    List<E> getEntityByEntity(E entity);

    /**
     * 通过filter过滤器查询符合条件的实体列表
     * @param filter
     * @return
     */
    List<E> getEntityByFilter(Map<?, ?> filter);

    List<Map> getWithColumn(E example);

    List<Map> getColumn();

    List<Map> getMapByFilter(Map<?, ?> filter);

}
