package com.eventprogrammer.organizationservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Document(value = "reservations")
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
