package com.asanhospital.server.repository;

import com.asanhospital.server.domain.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends MongoRepository<Manager, String> {
    Manager findByManagerId(String managerId);
    boolean existsByUsername(String username);
}
