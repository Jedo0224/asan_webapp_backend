package com.asanhospital.server.repository;

import com.asanhospital.server.domain.BeaconData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeaconDataRepository extends MongoRepository<BeaconData, String> {
    Optional<BeaconData> findByDeviceAddress(String deviceAddress);


}
