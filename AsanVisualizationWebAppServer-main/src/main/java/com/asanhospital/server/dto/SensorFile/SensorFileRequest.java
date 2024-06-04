package com.asanhospital.server.dto.SensorFile;

import com.asanhospital.server.domain.SensorData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class SensorFileRequest {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetSensorFileDto{
        private float Temperature;
        private int BLE_Data_Count;
        private LocalDateTime Date_and_Time;
        private float AccX;
        private float AccY;
        private float AccZ;
        // Todo: 아직 feature 확정 안된 상태라 확실한 데이터타입 가늠 안가는건 String으로 처리해둠
        private float Rating;
        private boolean Wear;

        public SensorData newSensorFile(SensorData sensorFile){
            return SensorData.builder()
                    .Temperature(sensorFile.getTemperature())
                    .BLE(sensorFile.getBLE())
                    .dateTime(sensorFile.getDateTime())
                    .AccX(sensorFile.getAccX())
                    .AccY(sensorFile.getAccY())
                    .AccZ(sensorFile.getAccZ())
                    .rating(sensorFile.getRating())
                    .wear(sensorFile.getWear())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchSensorData {
        private String patientName;             //환자명
        private String auxiliaryDeviceType;     //보조기종류
        private String birthDate;               //생년월일
        private String registrationDate;        //등록일
        private String responsiblePersonNumber; //담당자번호
        private int page;                       //페이지
        private int size;                       //사이즈
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteSensorDataDTO{
        private List<String> medicalRecordNumbers;
    }
}
