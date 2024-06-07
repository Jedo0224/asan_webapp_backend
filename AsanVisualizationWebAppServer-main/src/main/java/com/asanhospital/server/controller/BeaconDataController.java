package com.asanhospital.server.controller;

import com.asanhospital.server.apiPayload.ApiResponse;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import com.asanhospital.server.domain.BeaconData;
import com.asanhospital.server.dto.Mobile.BeaconDataRequest;
import com.asanhospital.server.dto.Mobile.BeaconDataResponse;
import com.asanhospital.server.dto.Mobile.BeaconDataDTO;
import com.asanhospital.server.dto.Mobile.BeaconRegisterDTO;
import com.asanhospital.server.service.Mobile.BeaconDataService;
import com.asanhospital.server.repository.BeaconDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/beacon-data")
public class BeaconDataController {

    private final BeaconDataService beaconDataService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<BeaconDataResponse>> receiveBeaconData(@RequestBody List<BeaconDataRequest> beaconDataRequestList) {
        log.info("Received POST request at /beacon-data/upload with {} items", beaconDataRequestList.size());

        try {
            for (BeaconDataRequest beaconDataRequest : beaconDataRequestList) {
                beaconDataService.beaconCheckByRedis(beaconDataRequest);
            }
            BeaconDataResponse response = BeaconDataResponse.builder()
                    .status("success")
                    .message("Data processed successfully")
                    .build();
            return ResponseEntity.status(201).body(ApiResponse.onSuccess(response));
        } catch (Exception e) {
            log.error("Error processing request", e);
            BeaconDataResponse errorResponse = BeaconDataResponse.builder()
                    .status("error")
                    .message("Failed to process data")
                    .build();
            return ResponseEntity.status(500).body(ApiResponse.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR, errorResponse));
        }
    }

    @GetMapping("/getDeviceLives")
    public ResponseEntity<Map<String, Object>> getAllConnectedBeacon() {
        Map<String, Object> beaconDataList = beaconDataService.getAllBeaconData();
        return ResponseEntity.ok(beaconDataList);
    }

    @DeleteMapping("/deleteDevice")
    public ResponseEntity<?> deleteDevice(@RequestBody List<BeaconDataDTO> beaconDataDTOs){

        try {
            for (BeaconDataDTO beaconList : beaconDataDTOs) {
                beaconDataService.deleteBeaconData(beaconList);
            }
            BeaconDataResponse response = BeaconDataResponse.builder()
                    .status("success")
                    .message("Device delete successfully")
                    .build();
            return ResponseEntity.status(201).body(ApiResponse.onSuccess(response));
        } catch (Exception e) {
            log.error("Error processing request", e);
            BeaconDataResponse errorResponse = BeaconDataResponse.builder()
                    .status("error")
                    .message("Failed to delete Device")
                    .build();
            return ResponseEntity.status(500).body(ApiResponse.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR, errorResponse));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<BeaconDataResponse>> registerBeaconDevice(@RequestBody List<BeaconDataDTO> beaconDataDTOs ) {
        try {
            for (BeaconDataDTO beaconList : beaconDataDTOs) {
                beaconDataService.saveBeaconData(beaconList);
            }
            BeaconDataResponse response = BeaconDataResponse.builder()
                    .status("success")
                    .message("Data processed successfully")
                    .build();
            return ResponseEntity.status(201).body(ApiResponse.onSuccess(response));
        } catch (Exception e) {
            log.error("Error processing request", e);
            BeaconDataResponse errorResponse = BeaconDataResponse.builder()
                    .status("error")
                    .message("Failed to process data")
                    .build();
            return ResponseEntity.status(500).body(ApiResponse.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR, errorResponse));
        }
    }

    @GetMapping("/getDevices")
    public ResponseEntity<List<BeaconDataDTO>> getAllBeaconDevice() {
        List<BeaconDataDTO> beaconDataList = beaconDataService.getAllBeaconDevice();
        return ResponseEntity.ok(beaconDataList);
    }
}