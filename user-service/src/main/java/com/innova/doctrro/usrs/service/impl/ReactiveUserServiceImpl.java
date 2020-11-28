package com.innova.doctrro.usrs.service.impl;

import com.innova.doctrro.usrs.beans.User;
import com.innova.doctrro.usrs.dao.ReactiveUserDao;
import com.innova.doctrro.usrs.exception.UserNotFoundException;
import com.innova.doctrro.usrs.service.Converter;
import com.innova.doctrro.usrs.service.ReactiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import com.innova.doctrro.usrs.dto.UserDtoRequest;
import static com.innova.doctrro.common.dto.UserDto.UserDtoResponse;
import static com.innova.doctrro.usrs.service.Converter.convert;


@Service
public class ReactiveUserServiceImpl implements ReactiveUserService {

    private final ReactiveUserDao reactiveUserDao;

    @Autowired
    public ReactiveUserServiceImpl(ReactiveUserDao reactiveUserDao) {
        this.reactiveUserDao = reactiveUserDao;
    }

    @Override
    public Mono<UserDtoResponse> findOrCreate(String email, UserDtoRequest request) {
        return reactiveUserDao.findById(email)
                .map(user -> {
                    if (user == null) {
                        User u = convert(request);
                        return reactiveUserDao.create(u);
                    }

                    return Mono.just(user);
                })
                .flatMap(Function.identity())
                .map(Converter::convert);
    }

    @Override
    public Mono<UserDtoResponse> create(UserDtoRequest item) {
        User user = convert(item);
        return reactiveUserDao.create(user)
                .map(Converter::convert);
//        return convert(userDao.create(user));
    }

    @Override
    public Mono<UserDtoResponse> findById(String s) {
        return reactiveUserDao.findById(s)
                .map(u -> {
                    if (u == null) {
                        throw new UserNotFoundException();
                    }
                    return u;
                })
                .map(Converter::convert);

//        return convert(user);
    }

    @Override
    public Flux<UserDtoResponse> findAll() {
        return reactiveUserDao.findAll()
                .map(Converter::convert);
    }

    @Override
    public Mono<UserDtoResponse> update(String s, UserDtoRequest item) {
        return null;
    }

    @Override
    public Mono<UserDtoResponse> addRole(String email, String role) {
        return reactiveUserDao.findById(email)
                .map(u -> {
                    if (u == null) {
                        throw new UserNotFoundException();
                    }

                    u.addRole(role);
                    u.setActive(true);
                    u.setEnabled(true);
                    return u;
                })
                .flatMap(user -> reactiveUserDao.update(email, user))
                .map(Converter::convert);
    }

    @Override
    public Mono<Void> remove(String s) {
        return reactiveUserDao.findById(s)
                .map(u -> {
                    if (u == null) {
                        throw new UserNotFoundException();
                    }

                    return u;
                }).flatMap(reactiveUserDao::remove);

    }

    @Override
    public Mono<Void> remove() {
        return reactiveUserDao.remove();
    }
}
