package com.eventprogrammer.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eventprogrammer.userservice.entity.Event;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String>{

    Event findByEventId(String eventId);
    List<Event> findByOrganizationEmail(String organizationEmail);

    List<Event> findByNome(String nome);

    List<Event> fingByDescrizione(String descrizione);
}
