package com.asanhospital.server.service.Mobile;

import com.asanhospital.server.domain.BeaconData;
import com.asanhospital.server.dto.Mobile.BeaconDataDTO;
import com.asanhospital.server.dto.Mobile.BeaconDataRequest;
import com.asanhospital.server.repository.BeaconDataRepository;
import com.asanhospital.server.service.DatabaseSequence.DatabaseSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BeaconDataServiceImpl implements BeaconDataService {

    private final BeaconDataRepository beaconDataRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final DatabaseSequenceService sequenceService;;
    private static final String SEQUENCE_NAME = "beacon_data_sequence";


    public Map<String, Object> getAllBeaconData() {
        Set<String> keys = redisTemplate.keys("*");
        Map<String, Object> allBeaconData = new HashMap<>();

        if (keys != null) {
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            for (String key : keys) {
                allBeaconData.put(key, valueOperations.get(key));
            }
        }

        return allBeaconData;
    }




    public void beaconCheckByRedis(BeaconDataRequest beaconDataRequest) {
    String deviceName = beaconDataRequest.getDeviceName();
    String deviceAddress = beaconDataRequest.getDeviceAddress();

    if (deviceName != null && deviceAddress != null) {
        String key = deviceAddress;

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, deviceName, 10, TimeUnit.SECONDS);
        }
    }


    // 여기서 redis를 활용해서 heartbeat 계산
//    @Override
//    public BeaconDataResponse processBeaconData(BeaconDataRequest beaconDataRequest) {
//        BeaconData beaconData = new BeaconData();
//        // Map fields from beaconDataRequest to beaconData
//        beaconData.setDeviceName(beaconDataRequest.getDeviceName());
//        beaconData.setDeviceAddress(beaconDataRequest.getDeviceAddress());
//
////        beaconDataRepository.save(beaconData);
//
//        return BeaconDataResponse.builder()
//                .status("success")
//                .message("Data processed successfully")
//                .build();
//    }

    @Override
    public void saveBeaconData(BeaconDataDTO beaconDataDTO) {

        Optional<BeaconData> existingData = beaconDataRepository.findByDeviceAddress(beaconDataDTO.getDeviceAddress());

        if (existingData.isPresent()) {
            throw new RuntimeException(beaconDataDTO.getDeviceAddress() + " : 기기 주소가" +  " 이미 존재합니다.");
        }


        BeaconData beaconData = BeaconData.builder()
                .id(String.valueOf(sequenceService.generateSequence(SEQUENCE_NAME)))
                .deviceName(beaconDataDTO.getDeviceName())
                .deviceAddress(beaconDataDTO.getDeviceAddress()).build();

        beaconDataRepository.save(beaconData);
    }

    @Override
    public void deleteBeaconData(BeaconDataDTO beaconList) {
        Optional<BeaconData> beaconData = beaconDataRepository.findById(beaconList.getId());
        if (beaconData.isPresent()) {
            beaconDataRepository.delete(beaconData.get());
        } else {

            System.out.println("Beacon data not found for id: " + beaconList.getId());
        }
    }


    @Override
    public List<BeaconDataDTO> getAllBeaconDevice(){
       List<BeaconData> beaconDataListAll = beaconDataRepository.findAll();
       List<BeaconDataDTO> beaconDataDTOList = new ArrayList<>();
       for (BeaconData beaconData : beaconDataListAll ){
           BeaconDataDTO beaconDataDTO = BeaconDataDTO.builder()
                   .id(beaconData.getId())
                   .deviceName(beaconData.getDeviceName())
                   .deviceAddress(beaconData.getDeviceAddress()).build();

           beaconDataDTOList.add(beaconDataDTO);
       }
        return beaconDataDTOList;
    }

//    public void processBeaconDataList(List<BeaconDataRequest> beaconDataRequestList) {
//        for (BeaconDataRequest beaconDataRequest : beaconDataRequestList) {
//            processBeaconData(beaconDataRequest);
//        }
//    }
}