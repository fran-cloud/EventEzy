package com.eventprogrammer.userservice.DTO;

import java.util.Date;

import com.eventprogrammer.userservice.entity.Organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    
    private String eventId;
    private String nome;
    private String tipologia;
    private String indirizzo;
    private Date dataEoraDate;
    private String organizationEmail;
    private int maxPrenotati;
    private int postiDisponibili;
}
