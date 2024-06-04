package com.asanhospital.server.service.PatientSummaryService;

import com.asanhospital.server.apiPayload.excpetion.GeneralException;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.PatientReport;
import com.asanhospital.server.domain.ReportData;
import com.asanhospital.server.dto.PatientSummary.PatientSummaryResponse;
import com.asanhospital.server.dto.PatientSummary.PatientSummaryResponse.SummaryResponse;
import com.asanhospital.server.repository.PatientReportRepository;
import com.asanhospital.server.repository.PatientRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientSummaryServiceImpl implements PatientSummaryService {
    private final PatientReportRepository patientReportRepository;
    private final PatientRepository patientRepository;

    @Override
    public SummaryResponse summary(Manager manager, String medicalRecordNumber, String startDate, String endDate){
        if(patientRepository.findBymedicalRecordNumberAndOrganization(medicalRecordNumber, manager.getOrganization()).isEmpty()){
            new GeneralException(ErrorStatus._PATIENT_NOT_FOUND);
        }

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        PatientReport patientReport = patientReportRepository.findByMedicalRecordNumber(medicalRecordNumber).orElse(null);
        List<ReportData> patientReports = patientReport.getReportDataList();

        patientReports.removeIf(reportData -> (reportData.getDate().isBefore(start) || reportData.getDate().isAfter(end)));

        return PatientSummaryResponse.toResponseDTO(medicalRecordNumber, patientReports);
    }
}
