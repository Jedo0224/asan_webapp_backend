package com.asanhospital.server.dto.Mobile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeaconDataResponse {
    private String status;
    private String message;
}
