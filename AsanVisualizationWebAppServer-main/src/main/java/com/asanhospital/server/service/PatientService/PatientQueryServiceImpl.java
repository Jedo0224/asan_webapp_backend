package com.asanhospital.server.service.PatientService;

import com.asanhospital.server.apiPayload.excpetion.GeneralException;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.Patient;
import com.asanhospital.server.dto.Patient.PatientRequest;
import com.asanhospital.server.dto.Patient.PatientResponse;
import com.asanhospital.server.dto.Patient.PatientResponse.PatientMainInfoDTO;
import com.asanhospital.server.repository.ManagerRepository;
import com.asanhospital.server.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientQueryServiceImpl implements PatientQueryService {
    //    @Autowired
    private final PatientRepository patientRepository;
    private final ManagerRepository managerRepository;
    @Autowired
    private final MongoTemplate mongoTemplate;

    @Override
    public Patient getPatientByMedicalRecordNumber(String medicalRecordNumber){
        return patientRepository.findById(medicalRecordNumber).orElse(null);
    }

    @Override
    public PatientResponse.SearchPatientDTO searchPatient(Manager manager, PatientRequest.SearchPatientDto searchConditions) {
        if(manager == null){
            throw new GeneralException(ErrorStatus._MANAGER_NOT_FOUND);
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("organization").is(manager.getOrganization()));

        if (searchConditions.getPatientName() != null) {
            query.addCriteria(Criteria.where("username").regex(".*" + searchConditions.getPatientName()+".*"));
        }
        if(searchConditions.getAuxiliaryDeviceType() != null){
            query.addCriteria(Criteria.where("auxiliaryDeviceType").regex(".*" + searchConditions.getAuxiliaryDeviceType()+".*"));
        }
        if(searchConditions.getBirthDate() != null){
            query.addCriteria(Criteria.where("dateOfBirth").is(searchConditions.getBirthDate()));
        }
        if(searchConditions.getDeviceName() != null){
            query.addCriteria(Criteria.where("deviceName").is(searchConditions.getDeviceName()));
        }
        if(searchConditions.getStartDate() != null)
        {
            query.addCriteria(Criteria.where("startDate").is(searchConditions.getStartDate()));
        }
        if(searchConditions.getResponsiblePersonName() != null)
        {
            query.addCriteria(Criteria.where("responsiblePersonName").regex(".*" + searchConditions.getResponsiblePersonName()+".*"));
        }

        Pageable pageable = PageRequest.of(searchConditions.getPage(), searchConditions.getSize(), Sort.unsorted());

        query.with(pageable)
                .skip(pageable.getPageSize() * pageable.getPageNumber())
                .limit(pageable.getPageSize());

        List<Patient> patients = mongoTemplate.find(query, Patient.class);
        Page<Patient> patientPage = PageableExecutionUtils.getPage(
                patients,
                pageable,
                () -> mongoTemplate.count(query, Patient.class)
        );

        List<PatientMainInfoDTO> patientInfos = patientPage.getContent().stream().map(PatientMainInfoDTO::toDto).toList();

        return PatientResponse.SearchPatientDTO.builder()
            .medicalRecordNumbers(patientInfos)
            .totalElements(patientPage.getTotalElements())
            .numberOfElements(patientPage.getNumberOfElements())
            .totalPages(patientPage.getTotalPages())
            .pageSize(patientPage.getSize())
            .pageNumber(patientPage.getNumber())
            .build();
    }

    @Override
    public PatientResponse.PatientDto updatePatient(Manager manager, PatientRequest.UpdatePatientDto updatePatientDto) {
        Query query = new Query(Criteria.where("medicalRecordNumber").is(updatePatientDto.getMedicalRecordNumber()));
        query.addCriteria(Criteria.where("organization").is(manager.getOrganization()));

        Update update = new Update();

        if(updatePatientDto.getAuxiliaryDeviceType() != null){
            update.set("auxiliaryDeviceType", updatePatientDto.getAuxiliaryDeviceType());
        }
        if(updatePatientDto.getUsername() != null){
            update.set("username", updatePatientDto.getUsername());
        }
        if(updatePatientDto.getGender() != null){
            update.set("gender", updatePatientDto.getGender());
        }
        if(updatePatientDto.getDateOfBirth() != null){
            update.set("dateOfBirth", updatePatientDto.getDateOfBirth());
        }
        if(updatePatientDto.getPassword() != null){
            update.set("password", updatePatientDto.getPassword());
        }
        if(updatePatientDto.getEmail() != null){
            update.set("email", updatePatientDto.getEmail());
        }
        if(updatePatientDto.getPhoneNumber() != null){
            update.set("phoneNumber", updatePatientDto.getPhoneNumber());
        }
        if(updatePatientDto.getAddress() != null){
            update.set("address", updatePatientDto.getAddress());
        }
        if(updatePatientDto.getFormFactorNumber() != null){
            update.set("formFactorNumber", updatePatientDto.getFormFactorNumber());
        }
        if(updatePatientDto.getDeviceName() != null){
            update.set("deviceName", updatePatientDto.getDeviceName());
        }
        if(updatePatientDto.getGuardianName() != null){
            update.set("guardianName", updatePatientDto.getGuardianName());
        }
        if(updatePatientDto.getGuardianRelationship() != null){
            update.set("guardianRelationship", updatePatientDto.getGuardianRelationship());
        }
        if(updatePatientDto.getGuardianPhoneNumber() != null){
            update.set("guardianPhoneNumber", updatePatientDto.getGuardianPhoneNumber());
        }
        if(updatePatientDto.getInitialEvaluation() != null){
            update.set("initialEvaluation", updatePatientDto.getInitialEvaluation());
        }
        if(updatePatientDto.getResponsiblePersonName() != null){
            update.set("responsiblePersonName", updatePatientDto.getResponsiblePersonName());
        }
        if(updatePatientDto.getResponsiblePersonNumber() != null){
            update.set("responsiblePersonNumber", updatePatientDto.getResponsiblePersonNumber());
        }
//        if(updatePatientDto.getResponsiblePersonId() != null){
//            Manager responsiblePerson = managerRepository.findByManagerId(updatePatientDto.getResponsiblePersonId());
//            if(responsiblePerson == null){
//                throw new GeneralException(ErrorStatus._MANAGER_NOT_FOUND);
//            }
//
//            if(updatePatientDto.getOrganization() == null &&
//                    !responsiblePerson.getOrganization().equals(manager.getOrganization())){
//                throw new GeneralException(ErrorStatus._PATIENT_MANAGER_ORGANIZATION_NOT_MATCH);
//            }
//
//            if(updatePatientDto.getOrganization() != null &&
//                    !updatePatientDto.getOrganization().equals(responsiblePerson.getOrganization())){
//                throw new GeneralException(ErrorStatus._PATIENT_MANAGER_ORGANIZATION_NOT_MATCH);
//            }
//
//            update.set("responsiblePersonNumber", responsiblePerson.getPhoneNumber());
//            update.set("responsiblePersonName", responsiblePerson.getUsername());
//        }
        if(updatePatientDto.getEndDate() != null){
            update.set("endDate", updatePatientDto.getEndDate());
        }
        if(updatePatientDto.getStartDate() != null){
            update.set("startDate", updatePatientDto.getStartDate());
        }

        mongoTemplate.findAndModify(query, update, Patient.class);
        Patient patient = patientRepository.findBymedicalRecordNumber(updatePatientDto.getMedicalRecordNumber());
        if(patient == null){
            throw new GeneralException(ErrorStatus._MEMBER_NOT_FOUND);
        }

        return PatientResponse.PatientDto.toDto(patient);
    }

    @Override
    public void createPatient(Patient patient){
        patientRepository.save(patient);
    }
}
