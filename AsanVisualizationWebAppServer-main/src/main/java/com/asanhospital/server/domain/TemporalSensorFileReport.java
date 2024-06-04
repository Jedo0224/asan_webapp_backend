package com.asanhospital.server.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "temporalSensorFileReport")
public class TemporalSensorFileReport {
    @Transient
    public static final String SEQUENCE_NAME = "sensorFileReports_sequence";

    @Id
    private Long id;
    private String medicalRecordNumber;
    private List<SensorData> sensorDataList;

    public void setID(Long id) {
        this.id = id;
    }

    public void setSensorDataList(List<SensorData> sensorFileList) {
        this.sensorDataList = sensorFileList;
    }
}
