package com.example.demo.base;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author mh
 * @Date 2019/2/22
 */
public abstract class FbBaseServiceImpl<E> implements FbBaseService<E> {
    private FbBaseDao<E> baseMapper;
    public void setBaseMapper(FbBaseDao<E> baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public E getEntityById(final String id){
        return baseMapper.selectByPrimaryKey(id);
    }


    @Override
    public List<E> getEntityByMap(Map<?, ?> map) {
        return baseMapper.selectByExample(map);
    }


    @Override
    public List<E> getEntityByFilter(Map<?, ?> filter) {
        return baseMapper.selectByFilter(filter);
    }

    @Override
    public List<E> getEntityByEntity(E entity) {
        return baseMapper.selectByExample(entity);
    }

    @Override
    public List<Map> getWithColumn(E example) {
        return baseMapper.selectWithColumn(example);
    }

    @Override
    public List<Map> getColumn() {
        return baseMapper.selectColumn();
    }

    @Override
    public List<Map> getMapByFilter(Map<?, ?> filter) {
        return baseMapper.selectMapByFilter(filter);
    }
}
