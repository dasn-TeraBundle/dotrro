package com.innova.doctrro.usrs.dao.repository;

import com.innova.doctrro.usrs.beans.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactiveUserRepository extends ReactiveMongoRepository<User, String> {
}
