package com.asanhospital.server.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.OptionalInt;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SensorData {
    private Float Temperature;
    private Integer BLE;
    private LocalDateTime dateTime;
    private Float AccX;
    private Float AccY;
    private Float AccZ;
    private Integer rating;
    private Boolean wear;

    public static LocalDateTime createWithKST(LocalDateTime utcDateTime) {
        ZonedDateTime utcZonedDateTime = utcDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime kstZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        return kstZonedDateTime.toLocalDateTime();
    }

    public LocalDate getLocalDate(){
        return dateTime.toLocalDate();
    }
}
