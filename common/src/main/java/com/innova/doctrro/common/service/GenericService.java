package com.innova.doctrro.common.service;

import java.io.Serializable;
import java.util.List;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;

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

    default void remove(T item) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    void remove();
}
