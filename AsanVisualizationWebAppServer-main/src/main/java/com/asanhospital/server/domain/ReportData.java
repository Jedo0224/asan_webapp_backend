package com.asanhospital.server.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReportData {
    private LocalDate date;
    private Integer actualWearTime;
    private Integer actualNonWearTime;
    private Integer actualDataLoss;
    private Integer VAS;
    private Float wearTime;
    private Float nonWearTime;

    public void updateWith(ReportData newData) {
        if (newData == null) {
            return;
        }

        this.actualWearTime = newData.getActualWearTime();
        this.actualNonWearTime = newData.getActualNonWearTime();
        this.actualDataLoss = newData.getActualDataLoss();
        this.VAS = newData.getVAS();
        this.wearTime = newData.getWearTime();
        this.nonWearTime = newData.getNonWearTime();
    }
}