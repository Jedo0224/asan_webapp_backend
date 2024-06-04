package com.asanhospital.server.service.PatientReport;

import com.asanhospital.server.apiPayload.excpetion.GeneralException;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import com.asanhospital.server.domain.PatientReport;
import com.asanhospital.server.dto.PatientReport.PatientReportRequest;
import com.asanhospital.server.dto.PatientReport.PatientReportResponse;
import com.asanhospital.server.repository.PatientReportRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PatientReportQueryServiceImpl implements PatientReportQueryService{
    private final PatientReportRepository patientReportsRepository;
    @Override
    public PatientReportResponse.DeletePatientReportDTO deletePatientReport(
        PatientReportRequest.DeletePatientReportDTO medicalRecordNumber) {
        List<PatientReport> deleteReports =  new ArrayList<>();
        for(String mrn : medicalRecordNumber.getMedicalRecordNumbers()){
            PatientReport report = patientReportsRepository.findByMedicalRecordNumber(mrn).orElse(null);

            if(report == null)
                throw new GeneralException(ErrorStatus._PATIENT_REPORT_NOT_FOUND);

            deleteReports.add(report);
        }

        patientReportsRepository.deleteAll(deleteReports);

        return PatientReportResponse.DeletePatientReportDTO.toDto(medicalRecordNumber.getMedicalRecordNumbers());
    }
}
