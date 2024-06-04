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
}