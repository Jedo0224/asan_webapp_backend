package com.asanhospital.server.service.FileService;

import com.asanhospital.server.apiPayload.excpetion.GeneralException;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.Patient;
import com.asanhospital.server.domain.PatientReport;
import com.asanhospital.server.domain.SensorData;
import com.asanhospital.server.domain.SensorFileReport;
import com.asanhospital.server.dto.SensorFile.SensorFileRequest;
import com.asanhospital.server.dto.SensorFile.SensorFileResponse;
import com.asanhospital.server.repository.PatientRepository;
import com.asanhospital.server.repository.SensorFileReportRepository;
import com.asanhospital.server.service.DatabaseSequence.DatabaseSequenceService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileCommandServiceImpl implements FileCommandService {
    private final DatabaseSequenceService databaseSequenceService;
    private final PatientRepository patientRepository;
    private final SensorFileReportRepository sensorFileRepository;

    @Override
    public SensorFileResponse.SensorFileDTO getPatientSensorData(Manager manager , String medicalRecordNumber) {
        if(patientRepository.findBymedicalRecordNumberAndOrganization(medicalRecordNumber, manager.getOrganization()).isEmpty())
            throw new GeneralException(ErrorStatus._PATIENT_NOT_FOUND);

        SensorFileReport sensorFile = sensorFileRepository.findByMedicalRecordNumber(medicalRecordNumber).orElse(null);
        if(sensorFile == null){
            throw new GeneralException(ErrorStatus._PATIENT_NOT_FOUND);
        }

        return SensorFileResponse.SensorFileDTO.toDto(sensorFile);
    }

    @Override
    public List<SensorData> saveSensorFileReportData(Manager manager, String medicalRecordNumber, MultipartFile file) {
        // 환자 정보 확인
        Patient patient = patientRepository.findBymedicalRecordNumberAndOrganization(medicalRecordNumber, manager.getOrganization()).orElse(null);
        if(patient == null)
            throw new GeneralException(ErrorStatus._PATIENT_NOT_FOUND);

        //파일 확인
        if (file.isEmpty()) {
            throw new GeneralException(ErrorStatus._FILE_IS_EMPTY);
        }

        // 파일레포트 불러오기
        List<SensorFileReport> reports = sensorFileRepository.findAllByMedicalRecordNumber(medicalRecordNumber);
        // 신규 생성
        if(reports == null ||
            reports.isEmpty()){
            reports = new ArrayList<>();
            reports.add(SensorFileReport.builder()
                .id(databaseSequenceService.generateSequence(PatientReport.SEQUENCE_NAME))
                .medicalRecordNumber(medicalRecordNumber)
                .sensorDataList(new ArrayList<>())
                .build());
        }

        List<SensorData> newDatas = new ArrayList<>();
        //파일 읽기
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            reader.readLine(); // 첫 번째 줄은 헤더이므로 건너뜀

            int idx = 0;
            SensorFileReport report = reports.get(idx);
            List<SensorData> allSensorFiles = report.getSensorDataList();
            while((line = reader.readLine()) != null){
                report = reports.get(idx);
                allSensorFiles = report.getSensorDataList();
                if (allSensorFiles.size() >= 10000)
                {
                    report.setSensorDataList(allSensorFiles);
                    sensorFileRepository.save(report);
                    idx++;
                    if(idx >= reports.size()){
                        reports.add(SensorFileReport.builder()
                            .id(databaseSequenceService.generateSequence(PatientReport.SEQUENCE_NAME))
                            .medicalRecordNumber(medicalRecordNumber)
                            .sensorDataList(new ArrayList<>())
                            .build());
                    }
                }

                String[] datas = line.split(",");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

                SensorData sensorFile = SensorData.builder()
                    .Temperature(datas[0].trim().equals("null") ? null : Float.parseFloat(datas[0].trim()))
                    .BLE(datas[1].trim().equals("null") ? null : Integer.parseInt(datas[1].trim()))
                    .dateTime(datas[2].trim().equals("null") ? null : LocalDateTime.parse(datas[2].trim(), formatter))
                    .AccX(datas[3].trim().equals("null") ? null : Float.parseFloat(datas[3].trim()))
                    .AccY(datas[4].trim().equals("null") ? null : Float.parseFloat(datas[4].trim()))
                    .AccZ(datas[5].trim().equals("null") ? null : Float.parseFloat(datas[5].trim()))
                    .rating(datas[6].trim().equals("null") ? null : Integer.parseInt(datas[6].trim()))
//                   .wear(datas[7].trim().equals("null") ? null : Boolean.parseBoolean(datas[7].trim()))
                    .build();
                allSensorFiles.add(sensorFile);
                newDatas.add(sensorFile);
            }
            report.setSensorDataList(allSensorFiles);
            sensorFileRepository.save(report);
        } catch (IOException e) {
            throw new GeneralException(ErrorStatus._FILE_READ_ERROR);
        }

        return newDatas;
    }

    @Override
    public SensorFileResponse.DeleteSensorDataDTO deleteSensorData(Manager manager, SensorFileRequest.DeleteSensorDataDTO deleteSensorDataDTO) {
        List<SensorFileReport> deleteList = new ArrayList<>();

        for (String medicalRecordNumber : deleteSensorDataDTO.getMedicalRecordNumbers()) {
            if(patientRepository.findBymedicalRecordNumberAndOrganization(medicalRecordNumber, manager.getOrganization()).isEmpty())
                throw new GeneralException(ErrorStatus._PATIENT_NOT_FOUND);
            SensorFileReport report = sensorFileRepository.findByMedicalRecordNumber(medicalRecordNumber).orElse(null);

            if(report == null)
                throw new GeneralException(ErrorStatus._SENSOR_REPORT_NOT_FOUND);

            deleteList.add(report);
        }

        sensorFileRepository.deleteAll(deleteList);

        return SensorFileResponse.DeleteSensorDataDTO.builder()
            .medicalRecordNumbers(deleteSensorDataDTO.getMedicalRecordNumbers())
            .build();
    }

    @Override
    public List<String[]> makeSensorCSVFile(Manager manager, String medicalRecordNumber) {
        if(patientRepository.findBymedicalRecordNumberAndOrganization(medicalRecordNumber, manager.getOrganization()).isEmpty())
            throw new GeneralException(ErrorStatus._PATIENT_NOT_FOUND);
        List<SensorFileReport> report = sensorFileRepository.findAllByMedicalRecordNumber(medicalRecordNumber);
        if(report.isEmpty())
            throw new GeneralException(ErrorStatus._SENSOR_REPORT_NOT_FOUND);

        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{"Temperature", "BLE_Data_Count", "Date_and_Time", "AccX", "AccY", "AccZ"});
        for(SensorFileReport sensorFileReport : report) {
            for(SensorData sensorData : sensorFileReport.getSensorDataList()){
                csvData.add(new String[]{
                    String.valueOf(sensorData.getTemperature()),
                    String.valueOf(sensorData.getBLE()),
                    sensorData.getDateTime().toString(),
                    String.valueOf(sensorData.getAccX()),
                    String.valueOf(sensorData.getAccY()),
                    String.valueOf(sensorData.getAccZ())
                });
            }
        }

        return csvData;
    }
}
