package com.asanhospital.server.controller;

import com.asanhospital.server.apiPayload.ApiResponse;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import com.asanhospital.server.domain.BeaconData;
import com.asanhospital.server.dto.Mobile.BeaconDataRequest;
import com.asanhospital.server.dto.Mobile.BeaconDataResponse;
import com.asanhospital.server.service.Mobile.BeaconDataService;
import com.asanhospital.server.repository.BeaconDataRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/beacon-data")
public class BeaconDataController {

    private final BeaconDataService beaconDataService;
    private final BeaconDataRepository beaconDataRepository;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<BeaconDataResponse>> receiveBeaconData(@RequestBody List<BeaconDataRequest> beaconDataRequestList) {
        log.info("Received POST request at /beacon-data/upload with {} items", beaconDataRequestList.size());

        try {
            for (BeaconDataRequest beaconDataRequest : beaconDataRequestList) {
                beaconDataService.processBeaconData(beaconDataRequest);
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

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<BeaconData>>> getAllBeaconData() {
        List<BeaconData> beaconDataList = beaconDataRepository.findAll();
        return ResponseEntity.ok(ApiResponse.onSuccessList(beaconDataList));
    }
}