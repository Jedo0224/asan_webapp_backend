package com.asanhospital.server.service.FileService;

import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.SensorData;
import com.asanhospital.server.domain.SensorFileReport;
import com.asanhospital.server.dto.SensorFile.SensorFileRequest;
import com.asanhospital.server.dto.SensorFile.SensorFileResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileCommandService {
    SensorFileResponse.SensorFileDTO getPatientSensorData(Manager manager, String medicalRecordNumber);
    List<SensorData> saveSensorFileReportData(Manager manager, String medicalRecordNumber, MultipartFile file);
    SensorFileResponse.DeleteSensorDataDTO deleteSensorData(Manager manager, SensorFileRequest.DeleteSensorDataDTO medicalRecordNumber);

    List<String[]> makeSensorCSVFile(Manager manager, String medicalRecordNumber);
}
