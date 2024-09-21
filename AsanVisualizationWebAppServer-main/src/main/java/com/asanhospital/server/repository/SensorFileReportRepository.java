package com.asanhospital.server.repository;

import com.asanhospital.server.domain.SensorFileReport;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface SensorFileReportRepository extends MongoRepository<SensorFileReport, Long> {
    Optional<SensorFileReport> findByMedicalRecordNumber(String medicalRecordNumber);
    List<SensorFileReport> findAllByMedicalRecordNumber(String medicalRecordNumber);

//    List<SensorFileReport> findAllByDeviceName(String medicalRecordNumber);

//    List<SensorFileReport> findAllByDeviceId(String deviceId);



}
