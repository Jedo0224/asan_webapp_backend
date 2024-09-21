package com.asanhospital.server.service.PatientService;

import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.Patient;
import com.asanhospital.server.dto.Manager.ManagerRequest;
import com.asanhospital.server.dto.Manager.ManagerResponse;
import com.asanhospital.server.dto.Mobile.PatientDTO;
import com.asanhospital.server.dto.Patient.ConnectionLogDTO;
import com.asanhospital.server.dto.Patient.PatientRequest;
import com.asanhospital.server.dto.Patient.PatientResponse;

import java.util.List;

public interface PatientCommandService {
    PatientResponse.PatientDto createPatient(Manager manager, PatientRequest.CreatePatientDto createPatientDto);
    PatientResponse.PatientDto PatienttoPatientDTO(Patient patient);
    PatientResponse.PatientDto getPatientInfo(Manager manager,String medicalRecordNumber);
    PatientResponse.DeletePatientDTO deletePatient(PatientRequest.DeletePatientDto patientIds);

    ConnectionLogDTO getConnectionLogList(String medicalRecordNumber);

    List<PatientDTO> getPatientList();
}
