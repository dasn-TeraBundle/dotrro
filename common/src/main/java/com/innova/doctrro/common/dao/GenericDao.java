package com.innova.doctrro.common.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {
    T create(T item);

    T findById(ID id);

    List<T> findAll();

    T update(ID id, T item);

    void remove(ID id);

    void remove(T item);

    void remove();
}
