package com.innova.doctrro.usrs.service.impl;

import com.innova.doctrro.usrs.beans.User;
import com.innova.doctrro.usrs.dao.UserDao;
import com.innova.doctrro.usrs.exception.UserNotFoundException;
import com.innova.doctrro.usrs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;
import static com.innova.doctrro.common.dto.UserDto.UserDtoRequest;
import static com.innova.doctrro.common.dto.UserDto.UserDtoResponse;
import static com.innova.doctrro.usrs.service.Converter.convert;


@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDtoResponse findOrCreate(String email, UserDtoRequest request) {
        User user = userDao.findById(email);
        if (user == null) {
            return create(request);
        }

        return convert(user);
    }

    @Override
    public UserDtoResponse create(UserDtoRequest item) {
        return convert(userDao.create(convert(item)));
    }

    @Override
    public UserDtoResponse findById(String s) {
        User user = userDao.findById(s);
        if (user == null) {
            throw new UserNotFoundException();
        }

        return convert(user);
    }

    @Override
    public List<UserDtoResponse> findAll() {
        return convert(userDao.findAll());
    }

    @Override
    public UserDtoResponse update(String s, UserDtoRequest item) {
        return null;
    }

    @Override
    public UserDtoResponse addRole(String email, String role) {
        User user = userDao.findById(email);
        if (user == null) {
            throw new UserNotFoundException();
        }

        user.addRole(role);
        user.setActive(true);
        user.setEnabled(true);

        return convert(userDao.update(email, user));
    }

    @Override
    public void remove(String s) {
        User user = userDao.findById(s);
        if (user == null) {
            throw new UserNotFoundException();
        }

        userDao.remove(user);
    }

    @Override
    public void remove(UserDtoRequest item) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    @Override
    public void remove() {
        userDao.remove();
    }
}
