package com.asanhospital.server.service.Mobile;

import com.asanhospital.server.dto.Mobile.BeaconDataRequest;
import com.asanhospital.server.dto.Mobile.BeaconDataResponse;

public interface BeaconDataService {
    BeaconDataResponse processBeaconData(BeaconDataRequest beaconDataRequest);
}
