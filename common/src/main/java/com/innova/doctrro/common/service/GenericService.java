package com.innova.doctrro.common.service;

import java.io.Serializable;
import java.util.List;

/**
 * @param <T>  Input type
 * @param <R>  Result type
 * @param <ID> Primary key type of input
 */
public interface GenericService<T, R, ID extends Serializable> {
    R create(T item);

    R findById(ID id);

    List<R> findAll();

    R update(ID id, T item);

    void remove(ID id);

    void remove(T item);

    void remove();
}
