package com.eventprogrammer.userservice.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eventprogrammer.userservice.entity.Reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import javax.validation.constraints.Email;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Inserire un nome valido")
    @NotNull(message = "Inserire un nome valido")
    @NotEmpty
    @Size(max = 50)
    private String nome;
    @NotBlank(message = "Inserire un cognome valido")
    @NotNull(message = "Inserire un cognome valido")
    @NotEmpty
    @Size(max = 50)
    private String cognome;
    @NotBlank(message = "Inserire un indirizzo valido")
    @NotNull(message = "Inserire un indirizzo valido")
    @NotEmpty
    @Size(max = 50)
    private String indirizzo;
    @Email(message = "Inserire un indirizzo email valido")
    private String email;
    @Past(message = "La data non può essere presente o futura")
    private Date dataNascita;
    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min = 8)
    private String password;

}
