package com.innova.doctrro.usrs.dao.impl;

import com.innova.doctrro.usrs.beans.User;
import com.innova.doctrro.usrs.dao.UserDao;
import com.innova.doctrro.usrs.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;

@Component
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;

    @Autowired
    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "users", key = "#user.email"),
            @CacheEvict(cacheNames = "users", key = "'ALL_USERS'")
    })
    public User create(User user) {
        return userRepository.insert(user);
    }

    @Override
    @Cacheable(cacheNames = "users", key = "#email")
    public User findById(String email) {
        return userRepository.findById(email).orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "users", key = "'ALL_USERS'")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Caching(put = {
            @CachePut(cacheNames = "users", key = "#id")
    }, evict = {
            @CacheEvict(cacheNames = "users", key = "#user.email"),
            @CacheEvict(cacheNames = "users", key = "'ALL_USERS'")
    })
    public User update(String id, User user) {
        return userRepository.save(user);
    }

    @Override
    public void remove(String s) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "users", key = "#user.email"),
            @CacheEvict(cacheNames = "users", key = "'ALL_USERS'")
    })
    public void remove(User user) {
        userRepository.delete(user);
    }

    @Override
    @CacheEvict(cacheNames = "users", allEntries = true)
    public void remove() {
        userRepository.deleteAll();
    }
}
