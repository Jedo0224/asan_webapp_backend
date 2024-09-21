package com.asanhospital.server.service.PatientService;

import com.asanhospital.server.apiPayload.excpetion.GeneralException;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import com.asanhospital.server.domain.*;
import com.asanhospital.server.dto.Mobile.PatientDTO;
import com.asanhospital.server.dto.Patient.ConnectionLogDTO;
import com.asanhospital.server.dto.Patient.PatientRequest;
import com.asanhospital.server.dto.Patient.PatientResponse;
import com.asanhospital.server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientCommandServiceImpl implements PatientCommandService {
    private final PatientRepository patientRepository;
    private final ConnectionLogRepository connectionLogRepository;
    private final PatientReportRepository patientReportRepository;
    private final SensorFileReportRepository sensorFileReportRepository;




    @Override
    @Transactional
    public PatientResponse.PatientDto createPatient(Manager manager, PatientRequest.CreatePatientDto createPatientDto){
        if(manager == null){
            throw new GeneralException(ErrorStatus._MANAGER_NOT_FOUND);
        }

        if (patientRepository.existsById(createPatientDto.getMedicalRecordNumber())) {
            throw new GeneralException(ErrorStatus._MEMBER_ALREADY_EXIST);
        }

        List<String> roles = new ArrayList<>();
        roles.add("PATIENT");  // PATIENT 권한 부여

        Patient patient =createPatientDto.toEntity(manager, createPatientDto.getPassword(), roles);

        return PatientResponse.PatientDto.toDto(patientRepository.save(patient));
    }


    @Override
    public PatientResponse.PatientDto PatienttoPatientDTO(Patient patient){

        return PatientResponse.PatientDto.builder()
                .auxiliaryDeviceType(patient.getAuxiliaryDeviceType())
                .medicalRecordNumber(patient.getMedicalRecordNumber())
                .username(patient.getUsername())
                .gender(patient.getGender())
                .dateOfBirth(patient.getDateOfBirth())
                .password(patient.getPassword())
                .email(patient.getEmail())
                .phoneNumber(patient.getPhoneNumber())
                .address(patient.getAddress())
                .formFactorNumber(patient.getFormFactorNumber())
                .guardianName(patient.getGuardianName())
                .guardianRelationship(patient.getGuardianRelationship())
                .guardianPhoneNumber(patient.getGuardianPhoneNumber())
                .initialEvaluation(patient.getInitialEvaluation())
                .organization(patient.getOrganization())
//                .responsiblePersonName(patient.getResponsiblePersonName())
                .responsiblePersonNumber(patient.getResponsiblePersonNumber())
                .registrationDate(patient.getRegistrationDate())
                .roles(patient.getRoles())
                .build();
    }

    // 아이디와 비밀번호를 이용하여 환자 정보를 조회하고 DTO로 반환
    @Override
    public PatientResponse.PatientDto getPatientInfo(Manager manager, String medicalRecordNumber) {
        if(manager == null){
            throw new GeneralException(ErrorStatus._MANAGER_NOT_FOUND);
        }

        Patient patient = patientRepository.findBymedicalRecordNumberAndOrganization(medicalRecordNumber,manager.getOrganization()).orElse(null);
        if(patient == null){
            throw new GeneralException(ErrorStatus._PATIENT_NOT_FOUND);
        }

        return PatientResponse.PatientDto.toDto(patient);
    }

    @Override
    public PatientResponse.DeletePatientDTO deletePatient(PatientRequest.DeletePatientDto patientIds){
        List<Patient> deletPatientList = new ArrayList<>();
        List<SensorFileReport> deletSensorFileList = new ArrayList<>();
        List<PatientReport> deletPatientReportList = new ArrayList<>();
        List<ConnectionLog> deleteLogList = new ArrayList<>();

        for(String id : patientIds.getMedicalRecordNumbers()){
            Patient patient = patientRepository.findBymedicalRecordNumber(id);
            Optional<SensorFileReport> sensorFileReport = sensorFileReportRepository.findByMedicalRecordNumber(id);
            Optional<PatientReport> patientReport = patientReportRepository.findByMedicalRecordNumber(id);
            Optional<ConnectionLog> ConnectionLog = connectionLogRepository.findByMedicalRecordNumber(id);



            if(patient == null){
                throw new GeneralException(ErrorStatus._MEMBER_NOT_FOUND);
            }
            deletPatientList.add(patient);
            sensorFileReport.ifPresent(deletSensorFileList::add);
            patientReport.ifPresent(deletPatientReportList::add);
            ConnectionLog.ifPresent(deleteLogList::add);

        }

        patientRepository.deleteAll(deletPatientList);
        sensorFileReportRepository.deleteAll(deletSensorFileList);
        patientReportRepository.deleteAll(deletPatientReportList);
        connectionLogRepository.deleteAll(deleteLogList);



        return PatientResponse.DeletePatientDTO.builder()
            .medicalRecordNumbers(patientIds.getMedicalRecordNumbers())
            .build();
    }

    @Override
    public ConnectionLogDTO getConnectionLogList(String medicalRecordNumber) {

        Optional<ConnectionLog> logList = connectionLogRepository.findByMedicalRecordNumber(medicalRecordNumber);

        ConnectionLogDTO connectionLogDTO = null;
        if (logList.isPresent()) {
            connectionLogDTO = ConnectionLogDTO.builder()
                    .connectionLogList(logList.get().getConnectionLogList())
                    .disconnectionLogList(logList.get().getDisconnectionLogList()).build();

        }

        return connectionLogDTO;
    }

    @Override
    public List<PatientDTO> getPatientList() {
        List<Patient> patientList = patientRepository.findAll();

        List<PatientDTO> patientDtoList = new ArrayList<>();
        for(Patient patient : patientList){
            PatientDTO patientDTO = PatientDTO.builder().deviceId(patient.getDeviceId()).deviceName(patient.getDeviceName()).medicalRecordNumber(patient.getMedicalRecordNumber()).build();
            patientDtoList.add(patientDTO);
        }

        return  patientDtoList;
    }
}
