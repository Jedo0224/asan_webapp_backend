package com.asanhospital.server.service.PatientReport;

import com.asanhospital.server.dto.PatientReport.PatientReportRequest;
import com.asanhospital.server.dto.PatientReport.PatientReportResponse;

public interface PatientReportQueryService {
    PatientReportResponse.DeletePatientReportDTO deletePatientReport(
        PatientReportRequest.DeletePatientReportDTO medicalRecordNumber);

}
