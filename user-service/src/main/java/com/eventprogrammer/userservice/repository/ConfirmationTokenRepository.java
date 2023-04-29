package com.eventprogrammer.userservice.repository;

import com.eventprogrammer.userservice.entity.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, Long> {
    ConfirmationToken findByToken(String token);
}
