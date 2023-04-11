package com.eventprogrammer.userservice.DTO;

import com.eventprogrammer.userservice.entity.Event;
import com.eventprogrammer.userservice.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    
    private String eventId;
    private String eventName;
    private String utenteEmail;
}
