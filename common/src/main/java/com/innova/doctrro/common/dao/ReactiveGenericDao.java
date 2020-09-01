package com.innova.doctrro.common.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

public interface ReactiveGenericDao<T, ID extends Serializable> {
    Mono<T> create(T item);

    Mono<T> findById(ID id);

    Flux<T> findAll();

    Mono<T> update(ID id, T item);

    Mono<Void> remove(ID id);

    Mono<Void> remove(T item);

    Mono<Void> remove();
}
