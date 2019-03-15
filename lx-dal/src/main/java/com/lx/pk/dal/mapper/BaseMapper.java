package com.lx.pk.dal.mapper;

import java.io.Serializable;
import java.util.List;

/**
 * Mapper 通用基类
 *
 * @param <T>
 * @param <ID>
 */
public interface BaseMapper<T extends Serializable, ID extends Serializable> {

    long insert(T entity);

    int update(T entity);

    T findOne(ID id);

    List<T> findAll();

    long count();

    List<T> getByIds(Iterable<ID> ids);

//    List<T> findList(MapperQuery mapperQuery);
//    long countList(MapperQuery mapperQuery);
}
