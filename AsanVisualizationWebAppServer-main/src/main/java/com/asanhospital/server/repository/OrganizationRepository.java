package com.asanhospital.server.repository;

import com.asanhospital.server.domain.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {
    Optional<Organization> findByOrganizationName(String organizationName);
    void deleteByOrganizationName(String organizationName);
}
