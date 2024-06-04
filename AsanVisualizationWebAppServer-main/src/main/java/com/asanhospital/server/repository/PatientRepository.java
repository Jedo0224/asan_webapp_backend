package com.asanhospital.server.repository;

import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.Patient;
import com.asanhospital.server.dto.Patient.PatientResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
    Patient findBymedicalRecordNumber(String medicalRecordNumber);
    Optional<Patient> findBymedicalRecordNumberAndOrganization(String medicalRecordNumber, String organization);
    boolean existsByUsername(String username);

    PatientResponse.PatientDto findByMedicalRecordNumberAndPassword(String medicalRecordNumber, String password);
}
