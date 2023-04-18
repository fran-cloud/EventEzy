package com.eventprogrammer.organizationservice.DTO;

import java.util.Date;
import java.util.List;

import com.eventprogrammer.organizationservice.entity.Reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String nome;
    private String cognome;
    private String indirizzo;
    private String email;
    private Date dataNascita;

}
