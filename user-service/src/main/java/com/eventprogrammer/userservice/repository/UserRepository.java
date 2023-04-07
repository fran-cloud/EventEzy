package com.eventprogrammer.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eventprogrammer.userservice.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    
    User findByUserId(String userId);

    User findByEmail(String email);

}
