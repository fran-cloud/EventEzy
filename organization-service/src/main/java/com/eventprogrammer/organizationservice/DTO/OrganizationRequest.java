package com.eventprogrammer.organizationservice.DTO;

import java.util.ArrayList;
import java.util.List;

import com.eventprogrammer.organizationservice.entity.Event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRequest {
    @NotBlank
    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String organizationName;
    @NotBlank
    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String organizationAddress;
    @Email
    private String email;
    @NotBlank
    @NotNull
    @NotEmpty
    @Size(max = 11)
    private String partitaIva;
    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min = 8)
    private String password;
}
