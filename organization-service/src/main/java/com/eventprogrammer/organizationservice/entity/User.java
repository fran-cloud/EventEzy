package com.eventprogrammer.organizationservice.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value =  "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;

    private String nome;
    private String cognome;
    private String indirizzo;
    private String email;
    private Date dataNascita;
    private String role;
    private String password;
    private Boolean locked;
    private Boolean enabled;

    
}
