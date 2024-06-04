package com.asanhospital.server.dto.SensorFile;

import com.asanhospital.server.domain.SensorData;
import com.asanhospital.server.domain.SensorFileReport;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SensorFileResponse {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SensorFileDTO{
        private String medicalRecordNumber;
        private List<SensorData> allSensorDataList;

        public static SensorFileDTO toDto(SensorFileReport sensorFileReport){
            return SensorFileDTO.builder()
                .medicalRecordNumber(sensorFileReport.getMedicalRecordNumber())
                .allSensorDataList(sensorFileReport.getSensorDataList())
                .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteSensorDataDTO{
        private List<String> medicalRecordNumbers;
    }

}
