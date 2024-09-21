package com.asanhospital.server.service.PatientReport;

import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.SensorData;
import com.asanhospital.server.domain.SensorFileReport;
import com.asanhospital.server.dto.PatientReport.PatientReportRequest;
import com.asanhospital.server.dto.PatientReport.PatientReportResponse;
import java.util.List;

public interface PatientReportCommandService {
    PatientReportResponse.PatientReportDTO addPatientReportData(PatientReportRequest.AddPatientReportDTO reportDatas);
    /*
    [sensorFileToPatientReport]
    sensorFileReport : 파일로부터 생성된 센서 데이터 레포트 파일
    Description: 센서 데이터입력 입력 후 알고리즘을 통해 일일 환자 정보 데이터 생성하는 메소드이다.
    이전에 추가된 데이터와 병합하여 환자의 일일 데이터를 생성한다.
    서버시간으로 당일의 데이터는 생성하지 않고 센서데이터 DB에 저장해두고 다음 입력때 생성한다.
     */

    PatientReportResponse.PatientReportDTO sensorFileToPatientReport(String deviceName, List<SensorData> datas);

    List<String[]> makePatientReportCSVFile(Manager manager, String medicalRecordNumber);
}
