package com.eventprogrammer.organizationservice.DTO;



import java.util.Date;

//import com.eventprogrammer.organizationservice.entity.Organization;

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
