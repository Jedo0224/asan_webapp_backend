package com.asanhospital.server.repository;

import com.asanhospital.server.domain.TemporalSensorFileReport;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemporalSensorFileReportRepository extends
    MongoRepository<TemporalSensorFileReport, Long> {
    Optional<TemporalSensorFileReport> findByMedicalRecordNumber(String medicalRecordNumber);
    List<TemporalSensorFileReport> findAllByMedicalRecordNumber(String medicalRecordNumber);
}
