package com.eventprogrammer.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eventprogrammer.userservice.entity.Organization;

public interface OrganizationRepository extends MongoRepository<Organization, String>{
    
    Organization findByOrganizationId(String organizationId);

    Organization findByOrganizationName(String organizationName);
    
}
