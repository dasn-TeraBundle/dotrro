package com.innova.doctrro.usrs.dao.impl;

import com.innova.doctrro.usrs.beans.User;
import com.innova.doctrro.usrs.dao.ReactiveUserDao;
import com.innova.doctrro.usrs.dao.repository.ReactiveUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import reactor.cache.CacheMono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;

@Component
public class ReactiveUserDaoImpl implements ReactiveUserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveUserDaoImpl.class);
    private final ReactiveUserRepository reactiveUserRepository;


    @Autowired
    public ReactiveUserDaoImpl(ReactiveUserRepository reactiveUserRepository) {
        this.reactiveUserRepository = reactiveUserRepository;
    }

    @Override
    public Mono<User> create(User user) {
        return reactiveUserRepository.insert(user);
    }

    @Override
    public Mono<User> findById(String email) {
        LOGGER.info("Fetching from DB");
        return reactiveUserRepository.findById(email).cache();
    }

    @Override
    public Flux<User> findAll() {
        return reactiveUserRepository.findAll();
    }

    @Override
    public Mono<User> update(String id, User user) {
        return reactiveUserRepository.save(user);
    }

    @Override
    public Mono<Void> remove(String s) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    @Override
    public Mono<Void> remove(User user) {
        return reactiveUserRepository.delete(user);
    }

    @Override
    public Mono<Void> remove() {
        return reactiveUserRepository.deleteAll();
    }


}
