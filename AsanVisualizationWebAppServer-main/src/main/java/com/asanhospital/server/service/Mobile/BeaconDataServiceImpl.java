package com.asanhospital.server.service.Mobile;

import com.asanhospital.server.domain.BeaconData;
import com.asanhospital.server.dto.Mobile.BeaconDataRequest;
import com.asanhospital.server.dto.Mobile.BeaconDataResponse;
import com.asanhospital.server.repository.BeaconDataRepository;
import com.asanhospital.server.service.Mobile.BeaconDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List; 

@Service
@RequiredArgsConstructor
public class BeaconDataServiceImpl implements BeaconDataService {

    private final BeaconDataRepository beaconDataRepository;


    // 여기서 redis를 활용해서 heartbeat 계산
    @Override
    public BeaconDataResponse processBeaconData(BeaconDataRequest beaconDataRequest) {
        BeaconData beaconData = new BeaconData();
        // Map fields from beaconDataRequest to beaconData
        beaconData.setDeviceName(beaconDataRequest.getDeviceName());
        beaconData.setDeviceAddress(beaconDataRequest.getDeviceAddress());
        beaconData.setTemperature(beaconDataRequest.getTemperature());
        beaconData.setBleDataCount(beaconDataRequest.getBleDataCount());
        beaconData.setCurrentDateAndTime(beaconDataRequest.getCurrentDateAndTime());
        beaconData.setTimestampNanos(beaconDataRequest.getTimestampNanos());
        beaconData.setValueX(beaconDataRequest.getValueX());
        beaconData.setValueY(beaconDataRequest.getValueY());
        beaconData.setValueZ(beaconDataRequest.getValueZ());
        beaconData.setRating(beaconDataRequest.getRating());

        beaconDataRepository.save(beaconData);

        return BeaconDataResponse.builder()
                .status("success")
                .message("Data processed successfully")
                .build();
    }

    public void processBeaconDataList(List<BeaconDataRequest> beaconDataRequestList) {
        for (BeaconDataRequest beaconDataRequest : beaconDataRequestList) {
            processBeaconData(beaconDataRequest);
        }
    }
}