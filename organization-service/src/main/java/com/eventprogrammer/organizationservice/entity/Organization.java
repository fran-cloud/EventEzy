package com.eventprogrammer.organizationservice.entity;



import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "organizations" )
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    @Id
    private String organizationId;
    private String organizationName;
    private String organizationAddress;
    private String email;
    private String partitaIva;
    private List<String> eventiOrganizzati;


}