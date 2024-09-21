package com.asanhospital.server.service.PatientReport;

import com.asanhospital.server.apiPayload.excpetion.GeneralException;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.Patient;
import com.asanhospital.server.domain.PatientReport;
import com.asanhospital.server.domain.ReportData;
import com.asanhospital.server.domain.SensorData;
import com.asanhospital.server.domain.SensorFileReport;
import com.asanhospital.server.domain.TemporalSensorFileReport;
import com.asanhospital.server.dto.PatientReport.PatientReportRequest;
import com.asanhospital.server.dto.PatientReport.PatientReportResponse;
import com.asanhospital.server.dto.PatientReport.PatientReportResponse.PatientReportDTO;
import com.asanhospital.server.repository.ManagerRepository;
import com.asanhospital.server.repository.PatientReportRepository;
import com.asanhospital.server.repository.PatientRepository;
import com.asanhospital.server.repository.SensorFileReportRepository;
import com.asanhospital.server.repository.TemporalSensorFileReportRepository;
import com.asanhospital.server.service.DatabaseSequence.DatabaseSequenceService;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PatientReportCommandServiceImpl implements PatientReportCommandService{
    private final PatientReportRepository patientReportsRepository;
    private final TemporalSensorFileReportRepository temporalSensorFileReportRepository;
    private final PatientRepository patientRepository;
    private final DatabaseSequenceService databaseSequenceService;
    private final SensorFileReportRepository sensorFileReportRepository;
    private final ManagerRepository managerRepository;

    @Override
    public PatientReportResponse.PatientReportDTO addPatientReportData(PatientReportRequest.AddPatientReportDTO reportDatas) {

        PatientReport report = patientReportsRepository.findByMedicalRecordNumber(reportDatas.getMedicalRecordNumber()).orElse(null);
        if(report == null){
            report = PatientReport.builder()
                    .id(databaseSequenceService.generateSequence(PatientReport.SEQUENCE_NAME))
                    .medicalRecordNumber(reportDatas.getMedicalRecordNumber())
                    .reportDataList(reportDatas.getReportDataList())
                    .build();
        }
        else{
            List<ReportData> dataList = report.getReportDataList();
            dataList.addAll(reportDatas.getReportDataList());

            // 데이터 정렬
            Collections.sort(dataList, new Comparator<ReportData>() {
                @Override
                public int compare(ReportData o1, ReportData o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });

            report.setReportDataList(dataList);
        }

        patientReportsRepository.save(report);
        return PatientReportResponse.PatientReportDTO.toDto(report);
    }

    @Override
    public PatientReportDTO sensorFileToPatientReport(String medicalRecordNumber, List<SensorData> datas) {

//        TemporalSensorFileReport sensorFileReport = temporalSensorFileReportRepository.findByDeviceName(medicalRecordNumber).orElse(null);
//        if(sensorFileReport == null){
//            sensorFileReport = TemporalSensorFileReport.builder()
//                .id(databaseSequenceService.generateSequence(PatientReport.SEQUENCE_NAME))
//                .medicalRecordNumber(medicalRecordNumber)
//                .sensorDataList(new ArrayList<>())
//                .build();
//        }

        PatientReport patientReport = patientReportsRepository.findByMedicalRecordNumber(medicalRecordNumber).orElse(null);
        if(patientReport == null){
            patientReport = PatientReport.builder()
                .id(databaseSequenceService.generateSequence(PatientReport.SEQUENCE_NAME))
//                    .deviceName(deviceName)
                .medicalRecordNumber(medicalRecordNumber)
                .reportDataList(new ArrayList<>())
                .build();
        }

        //기존에 처리되지 못한 데이터 추
//        List<SensorData> dataList = sensorFileReport.getSensorDataList();
        List<SensorData> dataList = new ArrayList<>(datas);


        //리스트를 날짜별로 그룹화
        Map<LocalDate, List<SensorData>> dayGroup = dataList.stream().collect(Collectors.groupingBy(SensorData::getLocalDate));



        List<ReportData> forResult = new ArrayList<>();
        List<ReportData> reportDataList  = patientReport.getReportDataList();

//        List<SensorData> remainSensorDataList = new ArrayList<>();
//        for (LocalDate date : dayGroup.keySet()) {
////            if(date.isEqual(LocalDate.now()))
////            {
////                remainSensorDataList.addAll(dayGroup.get(date));
////                continue;
////            }
//
//            List<SensorData> sensorDataList = dayGroup.get(date);
//            ReportData reportData = makeReportDataFromSensorData(date, sensorDataList);
//            System.out.println("reportData = " + reportData);
//            reportDataList.add(reportData);
//            forResult.add(reportData);
//        }

        for (LocalDate date : dayGroup.keySet()) {
            List<SensorData> sensorDataList = dayGroup.get(date);
            ReportData reportData = makeReportDataFromSensorData(date, sensorDataList);

            // 기존의 같은 날짜에 대한 ReportData를 찾기
            Optional<ReportData> existingReportDataOpt = reportDataList.stream()
                    .filter(rd -> rd.getDate().equals(date))
                    .findFirst();

            if (existingReportDataOpt.isPresent()) {
                // 이미 존재하는 ReportData를 덮어쓰기
                ReportData existingReportData = existingReportDataOpt.get();
                existingReportData.updateWith(reportData);
            } else {
                // 새로운 ReportData 추가
                reportDataList.add(reportData);
            }
            forResult.add(reportData);
        }

        // 남은 센서데이터 저장
//        sensorFileReport.setSensorDataList(remainSensorDataList);
//        temporalSensorFileReportRepository.save(sensorFileReport);

        // 데이터 정렬
        Collections.sort(reportDataList, new Comparator<ReportData>() {
            @Override
            public int compare(ReportData o1, ReportData o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        patientReport.setReportDataList(reportDataList);
        patientReportsRepository.save(patientReport);

        // 결과 리턴을 위한 리스트 재정의 (실제 DB엔 반영되지 않음)
        patientReport.setReportDataList(forResult);
        return PatientReportResponse.PatientReportDTO.toDto(patientReport);
    }

    @Override
    public List<String[]> makePatientReportCSVFile(Manager manager, String medicalRecordNumber) {
        Patient patient = patientRepository.findBymedicalRecordNumberAndOrganization(medicalRecordNumber, manager.getOrganization()).orElse(null);
        if(patient == null){
            throw new GeneralException(ErrorStatus._PATIENT_NOT_FOUND);
        }

        PatientReport patientReport = patientReportsRepository.findByMedicalRecordNumber(medicalRecordNumber).orElse(null);
        if(patientReport == null){
            throw new GeneralException(ErrorStatus._PATIENT_REPORT_NOT_FOUND);
        }

        List<ReportData> dataList = patientReport.getReportDataList();
        if(dataList == null){
            dataList = new ArrayList<>();
        }

        List<String[]> result = new ArrayList<>();
        result.add(new String[]{"Date", "Actual Wear Time", "Actual Non Wear Time", "Actual Date Loss", "WearTime", "Non Wear Time", "VAS"});
        for(ReportData reportData : dataList){
            result.add(new String[]{reportData.getDate().toString(), String.valueOf(reportData.getActualWearTime()),
                String.valueOf(reportData.getActualNonWearTime()), String.valueOf(reportData.getActualDataLoss()),
                String.valueOf(reportData.getWearTime()), String.valueOf(reportData.getNonWearTime()),
                String.valueOf(reportData.getVAS())});
        }

        return result;
    }

    // TODO : 아산병원에서 알고리즘 제공하면 이 곳에 구현해야함
    private ReportData makeReportDataFromSensorData(LocalDate date,List<SensorData> sensorDataList) {
        Random random = new Random();
        return ReportData.builder()
            .date(date)
            .actualDataLoss(random.nextInt(100))
            .VAS(random.nextInt(10))
            .actualNonWearTime(random.nextInt(100))
            .actualWearTime(random.nextInt(100))
            .nonWearTime(random.nextFloat())
            .wearTime(random.nextFloat())
            .build();
    }
}
