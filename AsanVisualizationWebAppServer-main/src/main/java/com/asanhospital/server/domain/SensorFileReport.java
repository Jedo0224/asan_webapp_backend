package com.asanhospital.server.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Builder
@Document(collection = "sensorFileReport")
public class SensorFileReport {
    @Transient
    public static final String SEQUENCE_NAME = "sensorFileReports_sequence";

    @Id
    private Long id;
    private String medicalRecordNumber;
    private String deviceName;
    private List<SensorData> sensorDataList;

    public void setID(Long id) {
        this.id = id;
    }

    public void setSensorDataList(List<SensorData> sensorFileList) {
        this.sensorDataList = sensorFileList;
    }
}
