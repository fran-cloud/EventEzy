package com.eventprogrammer.organizationservice.repository;

import com.eventprogrammer.organizationservice.entity.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;



import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation, String>{

    Reservation findByReservationId(String reservationId);
    List<Reservation> findByEventId(String eventId);

}