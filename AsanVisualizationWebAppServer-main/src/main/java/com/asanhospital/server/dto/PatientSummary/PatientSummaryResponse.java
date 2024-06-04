package com.asanhospital.server.dto.PatientSummary;

import com.asanhospital.server.domain.ReportData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class PatientSummaryResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SummaryResponse {
        private String medicalRecordNumber;
        Float wearTimeRatio;                 // 착용 시간 비율
        Float nonWearTimeRatio;              // 미착용 시간 비율
        Float VASAverage;                    // 주관적 통증 정도 평균
        List<ReportData> summaryDatas;       // 요약 데이터 리스트
    }

    public static SummaryResponse toResponseDTO(String medicalRecordNumber, List<ReportData> dataList){
        Float wearTimeRatio = 0.0f;                 // 착용 시간 비율
        Float nonWearTimeRatio = 0.0f;              // 미착용 시간 비율
        Float VASAverage = 0.0f;                    // 주관적 통증 정도 평균

        int wearTimeSize = 0;
        int nonWearTimeSize = 0;
        int VASSize = 0;

        for (ReportData data : dataList) {
            if (data.getWearTime() != null) {
                wearTimeRatio += data.getWearTime();
                wearTimeSize++;
            }
            if (data.getNonWearTime() != null) {
                nonWearTimeRatio += data.getNonWearTime();
                nonWearTimeSize++;
            }
            if (data.getVAS() != null) {
                VASAverage += data.getVAS();
                VASSize++;
            }
        }

        wearTimeRatio /= wearTimeSize;
        nonWearTimeRatio /= nonWearTimeSize;
        VASAverage /= VASSize;

        return SummaryResponse.builder()
                .medicalRecordNumber(medicalRecordNumber)
                .wearTimeRatio(wearTimeRatio)
                .nonWearTimeRatio(nonWearTimeRatio)
                .VASAverage(VASAverage)
                .summaryDatas(dataList)
                .build();
    }
}
