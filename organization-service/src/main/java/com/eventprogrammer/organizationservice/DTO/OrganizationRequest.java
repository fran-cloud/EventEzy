package com.eventprogrammer.organizationservice.DTO;

import java.util.ArrayList;
import java.util.List;

import com.eventprogrammer.organizationservice.entity.Event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRequest {
    
    private String organizationName;
    private String organizationAddress;
    private String email;
    private String partitaIva;

}
