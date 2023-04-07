package com.eventprogrammer.userservice.DTO;

import java.util.Date;
import java.util.List;

import com.eventprogrammer.userservice.entity.Reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    
    private String nome;
    private String cognome;
    private String indirizzo;
    private String email;
    private Date dataNascita;
    private List<Reservation> prenotazioni;

}
