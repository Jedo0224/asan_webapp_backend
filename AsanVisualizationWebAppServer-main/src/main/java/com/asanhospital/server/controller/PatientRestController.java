package com.asanhospital.server.controller;

import com.asanhospital.server.annotation.ManagerObject;
import com.asanhospital.server.apiPayload.ApiResponse;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.Patient;
import com.asanhospital.server.dto.JwtToken;
import com.asanhospital.server.dto.Manager.ManagerRequest;
import com.asanhospital.server.dto.Mobile.PatientDTO;
import com.asanhospital.server.dto.Patient.ConnectionLogDTO;
import com.asanhospital.server.dto.Patient.PatientRequest;
import com.asanhospital.server.dto.Patient.PatientResponse;
import com.asanhospital.server.dto.Patient.mdNumberDTO;
import com.asanhospital.server.service.Auth.AuthService;
import com.asanhospital.server.service.ManagerService.ManagerCommandService;
import com.asanhospital.server.service.PatientService.PatientCommandService;
import com.asanhospital.server.service.PatientService.PatientQueryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientRestController {
    @Autowired
    private PatientCommandService patientCommandService;
    @Autowired
    private PatientQueryService patientQueryService;
    @GetMapping("/info/{medicalRecordNumber}")
    public ApiResponse<PatientResponse.PatientDto> getPatientInfo(@ManagerObject Manager manager,@PathVariable String medicalRecordNumber){
        //TODO 헤더에서 사용자 ID 받아와야함

        PatientResponse.PatientDto savedPatientDto = patientCommandService.getPatientInfo(manager,medicalRecordNumber);
//        log.info(String.valueOf(savedPatientDto));

        if (savedPatientDto == null) {
            // Handle the case when savedPatientDto is null (patient not found)
            return ApiResponse.onFailure(ErrorStatus._MEMBER_NOT_FOUND, null);
        }

        return ApiResponse.onSuccess(savedPatientDto);
    }

    @PostMapping("/register")
    public ApiResponse<PatientResponse.PatientDto> createPatient(@ManagerObject Manager manager,@Valid @RequestBody PatientRequest.CreatePatientDto createPatientDto) {
        return ApiResponse.onSuccess(patientCommandService.createPatient(manager,createPatientDto));
    }

    @DeleteMapping("/delete") // Specify the URL path parameter for the user ID
    public ApiResponse<PatientResponse.DeletePatientDTO> deletePatient(@RequestBody PatientRequest.DeletePatientDto getPatientDto) {

        return ApiResponse.onSuccess(patientCommandService.deletePatient(getPatientDto));
    }

    @PatchMapping("/update")
    public ApiResponse<PatientResponse.PatientDto> updatePatient(@ManagerObject Manager manager,@RequestBody PatientRequest.UpdatePatientDto updatePatientDto){
        PatientResponse.PatientDto updatedPatientDto = patientQueryService.updatePatient(manager, updatePatientDto);
        return ApiResponse.onSuccess(updatedPatientDto);
    }

    @PostMapping("/search")
    public ApiResponse<PatientResponse.SearchPatientDTO> searchPatient(@ManagerObject Manager manager, @RequestBody PatientRequest.SearchPatientDto searchPatientDto){
        return ApiResponse.onSuccess(patientQueryService.searchPatient(manager, searchPatientDto));
    }

    @GetMapping("/getPatientList")
    public ApiResponse<List<PatientDTO>> getPatientList(){
        return ApiResponse.onSuccess(patientCommandService.getPatientList());
    }

    @GetMapping("/getConnectionLogList/{medicalRecordNumber}")
    public ApiResponse<ConnectionLogDTO> getConnectionLogList(@PathVariable String medicalRecordNumber){
        return ApiResponse.onSuccess(patientCommandService.getConnectionLogList(medicalRecordNumber));
    }

}
