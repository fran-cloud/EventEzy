package com.eventprogrammer.userservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Document(value = "reservations")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    private String reservationId;

    private String eventId;
    private String eventName;
    private String eventAddress;
    private Date eventData;
    private String organizationEmail;
    private String utenteEmail;
  
    

    
}