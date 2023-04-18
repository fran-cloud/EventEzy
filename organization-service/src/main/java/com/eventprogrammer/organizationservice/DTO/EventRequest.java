package com.eventprogrammer.organizationservice.DTO;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.eventprogrammer.organizationservice.entity.Organization;

import com.eventprogrammer.organizationservice.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    private String nome;
    private String tipologia;
    private String indirizzo;
    private Date dataEoraDate;
    private int maxPrenotati;
    
}
