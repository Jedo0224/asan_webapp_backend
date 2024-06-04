package com.asanhospital.server.dto.Mobile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeaconDataRequest {
    private String deviceName;
    private String deviceAddress;
    private Integer temperature;
    private Integer bleDataCount;
    private String currentDateAndTime;
    private String timestampNanos;
    private Integer valueX;
    private Integer valueY;
    private Integer valueZ;
    private Integer rating;

}
