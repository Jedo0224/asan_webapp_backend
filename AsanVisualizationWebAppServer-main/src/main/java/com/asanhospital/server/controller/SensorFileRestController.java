package com.asanhospital.server.controller;

import com.asanhospital.server.annotation.ManagerObject;
import com.asanhospital.server.apiPayload.ApiResponse;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.SensorData;
import com.asanhospital.server.domain.SensorFileReport;
import com.asanhospital.server.dto.PatientReport.PatientReportResponse.PatientReportDTO;
import com.asanhospital.server.dto.SensorFile.SensorFileRequest;
import com.asanhospital.server.dto.SensorFile.SensorFileResponse;
import com.asanhospital.server.repository.SensorFileReportRepository;
import com.asanhospital.server.service.FileService.FileCommandService;
import com.asanhospital.server.service.PatientReport.PatientReportCommandService;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sensorFile")
public class SensorFileRestController {
    private final FileCommandService fileCommandService;
    private final PatientReportCommandService patientReportCommandService;
    private final SensorFileReportRepository sensorFileReportRepository;
    @PostMapping("/upload")
    public ApiResponse<PatientReportDTO> uploadCSVFile(@ManagerObject Manager manager, @RequestPart("file") MultipartFile file, @RequestParam String medicalRecordNumber) throws IOException {
        // temporalSensorFileReport를 먼저 읽는다.
        // 입력받은 파일을 읽고 오늘 이전의 데이터를 temporalSensorFileReport리스트에 추가한다.
        // temporalSensorFileReport를 분석한다.
        // temporalSensorFileReport데이터를 삭제한다.
        // 입력받은 파일에서 분석된 데이터를 제외하고(오늘의 데이터를)저장한다.
        // 파일 전체는 SensorFileReport에 저장한다. (1만개씩 분할 저장)

        List<SensorData> newDatas = fileCommandService.saveSensorFileReportData(manager, medicalRecordNumber, file);
        return ApiResponse.onSuccess(patientReportCommandService.sensorFileToPatientReport(medicalRecordNumber, newDatas));
    }

    @GetMapping("/getData")
    public ApiResponse<SensorFileResponse.SensorFileDTO> getPatientSensorData(@ManagerObject Manager manager, @RequestParam("medicalRecordNumber") String medicalRecordNumber){
        SensorFileResponse.SensorFileDTO sensorFileDTO = fileCommandService.getPatientSensorData(manager, medicalRecordNumber);
        return ApiResponse.onSuccess(sensorFileDTO);
    }

    @GetMapping("/download")
    public void downloadSensorData(@ManagerObject Manager manager, @RequestParam("medicalRecordNumber") String medicalRecordNumber, HttpServletResponse response) throws IOException{
        response.setContentType("text/csv; charset=utf-8");
        String fileName = URLEncoder.encode(medicalRecordNumber + ".csv", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
        writer.write("\uFEFF");
        CSVWriter csvWriter = new CSVWriter(writer);

        csvWriter.writeAll(fileCommandService.makeSensorCSVFile(manager, medicalRecordNumber));

        csvWriter.close();
        writer.close();
    }


    @DeleteMapping("/delete")
    public ApiResponse<SensorFileResponse.DeleteSensorDataDTO> deleteSensorData(@ManagerObject Manager manager, @RequestBody SensorFileRequest.DeleteSensorDataDTO medicalRecordNumbers){
        return ApiResponse.onSuccess(fileCommandService.deleteSensorData(manager, medicalRecordNumbers));

    }
}
