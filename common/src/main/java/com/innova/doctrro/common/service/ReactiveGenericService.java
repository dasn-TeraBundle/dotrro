package com.innova.doctrro.common.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;

/**
 * @param <T>  Input type
 * @param <R>  Result type
 * @param <ID> Primary key type of input
 */
public interface ReactiveGenericService<T, R, ID extends Serializable> {
    <S extends T> Mono<R> create(S item);

    Mono<R> findById(ID id);

    Flux<R> findAll();

    <S extends T> Mono<R> update(ID id, S item);

    Mono<Void> remove(ID id);

    default Mono<Void> remove(T item) {
        return Mono.error(new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE));
    }

    Mono<Void> remove();
}
