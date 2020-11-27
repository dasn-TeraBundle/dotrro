package com.innova.doctrro.common.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;

public interface ReactiveGenericDao<T, ID extends Serializable> {
    Mono<T> create(T item);

    Mono<T> findById(ID id);

    Flux<T> findAll();

    Mono<T> update(ID id, T item);

    default Mono<Void> remove(ID id) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    Mono<Void> remove(T item);

    Mono<Void> remove();
}
