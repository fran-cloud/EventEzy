package com.eventprogrammer.userservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(value = "reservations")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    private String reservationId;

    private Event evento;
    private String utenteEmail;
  
    

    
}