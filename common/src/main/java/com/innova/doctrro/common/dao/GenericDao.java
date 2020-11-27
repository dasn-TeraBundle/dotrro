package com.innova.doctrro.common.dao;

import java.io.Serializable;
import java.util.List;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;

public interface GenericDao<T, ID extends Serializable> {
    T create(T item);

    T findById(ID id);

    List<T> findAll();

    T update(ID id, T item);

    default void remove(ID id) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    void remove(T item);

    void remove();
}
