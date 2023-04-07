package com.eventprogrammer.userservice.repository;

import com.eventprogrammer.userservice.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.eventprogrammer.userservice.entity.Reservation;

import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation, String>{

    Reservation findByReservationId(String reservationId);
    List<Reservation> findByUtenteEmail(String utenteEmail);

}
