package com.eventprogrammer.organizationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eventprogrammer.organizationservice.entity.Event;



public interface EventRepository extends MongoRepository<Event, String> {

    Event findByEventId(String eventId);
    
}
