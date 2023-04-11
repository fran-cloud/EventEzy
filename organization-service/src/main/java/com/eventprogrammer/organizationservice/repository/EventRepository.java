package com.eventprogrammer.organizationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eventprogrammer.organizationservice.entity.Event;

import java.util.List;


public interface EventRepository extends MongoRepository<Event, String> {

    Event findByEventId(String eventId);

    List<Event> findByOrganizationEmail(String email);
    
}
