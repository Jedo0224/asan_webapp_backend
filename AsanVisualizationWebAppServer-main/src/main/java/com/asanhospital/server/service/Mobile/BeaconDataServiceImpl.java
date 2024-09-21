package com.asanhospital.server.service.Mobile;

import com.asanhospital.server.domain.BeaconData;
\


























































\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\import com.asanhospital.server.domain.ConnectionLog;
import com.asanhospital.server.domain.Patient;
import com.asanhospital.server.dto.Mobile.BeaconDataDTO;
import com.asanhospital.server.dto.Mobile.BeaconDataRequest;
import com.asanhospital.server.repository.BeaconDataRepository;
import com.asanhospital.server.repository.ConnectionLogRepository;
import com.asanhospital.server.repository.PatientRepository;
import com.asanhospital.server.service.DatabaseSequence.DatabaseSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BeaconDataServiceImpl implements BeaconDataService {

    private final BeaconDataRepository beaconDataRepository;
    private final PatientRepository patientRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final DatabaseSequenceService sequenceService;
    private final ConnectionLogRepository connectionLogRepository;
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

        Patient patient = patientRepository.findByDeviceName(deviceName);

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        if (deviceName != null && patient != null ) {
            Integer disconnectCount = patient.getDisconnectionCount();
            if (valueOperations.get(deviceName) == null){
                LocalDateTime localDateTime = LocalDateTime.now();



                Optional<ConnectionLog> connectionLog = connectionLogRepository.findByMedicalRecordNumber(patient.getMedicalRecordNumber());
                if(connectionLog.isPresent()){
                    connectionLog.get().getConnectionLogList().add(localDateTime);
                    connectionLogRepository.save(connectionLog.get());
                }
                else {
                    ConnectionLog newConnectionLog = ConnectionLog.builder().medicalRecordNumber(patient.getMedicalRecordNumber())
                            .connectionLogList(new ArrayList<>())
                            .disconnectionLogList(new ArrayList<>()).build();

                    newConnectionLog.getConnectionLogList().add(localDateTime);
                    connectionLogRepository.save(newConnectionLog);
                }

            }

        valueOperations.set(deviceName, disconnectCount.toString(), 60*10, TimeUnit.SECONDS);
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