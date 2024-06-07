package com.asanhospital.server.service.Mobile;

import com.asanhospital.server.dto.Mobile.BeaconDataRequest;
import com.asanhospital.server.dto.Mobile.BeaconDataResponse;
import com.asanhospital.server.dto.Mobile.BeaconDataDTO;
import com.asanhospital.server.dto.Mobile.BeaconRegisterDTO;

import java.util.List;
import java.util.Map;


public interface BeaconDataService {

    Map<String, Object> getAllBeaconData();
    void beaconCheckByRedis(BeaconDataRequest beaconDataRequest);
//    BeaconDataResponse processBeaconData(BeaconDataRequest beaconDataRequest);

    void saveBeaconData(BeaconDataDTO beaconList);

    void deleteBeaconData(BeaconDataDTO beaconList);


    List<BeaconDataDTO> getAllBeaconDevice();




//    void deleteDevices(BeaconRegisterDTO beaconRegisterDTO);
}
