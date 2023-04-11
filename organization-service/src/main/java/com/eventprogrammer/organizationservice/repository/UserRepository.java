package com.eventprogrammer.organizationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eventprogrammer.organizationservice.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUserId(String userId);

    User findByEmail(String email);

}
