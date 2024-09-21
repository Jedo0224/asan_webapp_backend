package com.asanhospital.server.repository;

import com.asanhospital.server.domain.Patient;
import com.asanhospital.server.domain.PatientReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientReportRepository extends MongoRepository<PatientReport, Long> {
   Optional<PatientReport> findByMedicalRecordNumber(String medicalRecordNumber);
    PatientReport findByDeviceName(String DeviceName);
}
