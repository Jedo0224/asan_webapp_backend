package com.asanhospital.server.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "beacon_data")
public class BeaconData {
    @Id
    private String id;
    private String deviceName;
    private String deviceAddress;
    private byte[] manufacturerData;
    private Integer temperature;
    private Integer bleDataCount;
    private String currentDateAndTime;
    private String timestampNanos;
    private Integer valueX;
    private Integer valueY;
    private Integer valueZ;
    private Integer rating;
}
