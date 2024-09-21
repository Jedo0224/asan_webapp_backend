package com.asanhospital.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@Document(collection = "patientReports")
public class PatientReport {
    @Transient
    public static final String SEQUENCE_NAME = "patientReports_sequence";

    @Id
    private Long id;
    private String medicalRecordNumber;
    private String deviceName;
    private List<ReportData> reportDataList;

    public void setID(Long id) {
        this.id = id;
    }

    public void setReportDataList(List<ReportData> reportDataList) {
        this.reportDataList = reportDataList;
    }
}




