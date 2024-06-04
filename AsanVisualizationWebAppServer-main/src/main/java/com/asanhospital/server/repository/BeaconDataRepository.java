package com.asanhospital.server.repository;

import com.asanhospital.server.domain.BeaconData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeaconDataRepository extends MongoRepository<BeaconData, String> {
    // Define custom query methods if needed
}
