package com.eventprogrammer.userservice.DTO;

import com.eventprogrammer.userservice.entity.Event;
import com.eventprogrammer.userservice.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    
    private String reservationId;
    private String eventId;
    private String eventName;
    private String eventAddress;
    private Date eventData;
    private String organizationEmail;
    private String utenteEmail;
}
