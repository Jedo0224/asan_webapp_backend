package com.asanhospital.server.service.PatientService;

import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.Patient;
import com.asanhospital.server.dto.Patient.PatientRequest;
import com.asanhospital.server.dto.Patient.PatientResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientQueryService {

    // ID를 통해 DB에서 Patient 찾아 반환하는 메소드
    Patient getPatientByMedicalRecordNumber(String medicalRecordNumber);
    PatientResponse.SearchPatientDTO searchPatient(Manager manager, PatientRequest.SearchPatientDto searchConditions);
    PatientResponse.PatientDto updatePatient(Manager manager, PatientRequest.UpdatePatientDto updatePatientDto);
    void createPatient(Patient patient);
}
