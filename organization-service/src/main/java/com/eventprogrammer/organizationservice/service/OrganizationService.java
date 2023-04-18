package com.eventprogrammer.organizationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventprogrammer.organizationservice.DTO.OrganizationRequest;
import com.eventprogrammer.organizationservice.entity.Organization;
import com.eventprogrammer.organizationservice.repository.OrganizationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    /*Metodo per creare una organizzazione */
    public void createOrganization(OrganizationRequest organizationRequest){
        Organization organization = Organization.builder()
        .organizationName(organizationRequest.getOrganizationName())
        .organizationAddress(organizationRequest.getOrganizationAddress())
        .email(organizationRequest.getEmail())
        .partitaIva(organizationRequest.getPartitaIva())
        .build();

        organizationRepository.save(organization);
        log.info("L'organizzazione {} è stata registrata", organization.getOrganizationId());
    }

    public Organization saveOrganization(Organization organization) {
        log.info("All'interno del metodo saveOrganization del service di organization.");
        return organizationRepository.save(organization);
 
    }

    
    public Organization findOrganizationById(String organizationId) {
        log.info("All'interno del metodo findOrganizationById del service di organization.");
        return organizationRepository.findByOrganizationId(organizationId);

    }

    
   
}
