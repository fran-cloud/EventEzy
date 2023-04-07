package com.eventprogrammer.organizationservice.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.eventprogrammer.organizationservice.entity.Organization;


public interface OrganizationRepository extends MongoRepository<Organization, String> {

    Organization findByOrganizationId(String organizationId);
    
}
