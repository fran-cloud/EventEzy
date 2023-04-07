package com.eventprogrammer.userservice.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "events")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    private String eventId;

    private String nome;
    private String descrizione;
    private Date dataEoraDate;
    private String organizationEmail;
    private int maxPrenotati;
    private List<Reservation> prenotazioni;
    

    
}