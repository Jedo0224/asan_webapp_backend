package com.asanhospital.server.dto.PatientReport;

import com.asanhospital.server.domain.PatientReport;
import com.asanhospital.server.domain.ReportData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PatientReportRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddPatientReportDTO{
        private String medicalRecordNumber;
        private String deviceName;
        private List<ReportData> reportDataList;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeletePatientReportDTO{
        private List<String> medicalRecordNumbers;
    }
}
