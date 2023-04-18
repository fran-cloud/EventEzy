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
public class ReservationRequest {
    
    private String eventId;
    private String utenteEmail;
}
