package com.asanhospital.server.dto.PatientReport;

import com.asanhospital.server.domain.PatientReport;
import com.asanhospital.server.domain.ReportData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PatientReportResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientReportDTO{
        private String medicalRecordNumber;
        private String deviceId;
        private List<ReportData> reportDataList;

        public static PatientReportDTO toDto(PatientReport patientReport){
            return PatientReportDTO.builder()
                .medicalRecordNumber(patientReport.getMedicalRecordNumber())
//                    .deviceId(patientReport.getDeviceId())
                .reportDataList(patientReport.getReportDataList())
                .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeletePatientReportDTO{
        private List<String> medicalRecordNumbers;

        public static DeletePatientReportDTO toDto(List<String> medicalRecordNumbers){
            return DeletePatientReportDTO.builder()
                .medicalRecordNumbers(medicalRecordNumbers)
                .build();
        }
    }

}
