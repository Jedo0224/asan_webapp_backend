package com.asanhospital.server.controller;

import com.asanhospital.server.annotation.ManagerObject;
import com.asanhospital.server.apiPayload.ApiResponse;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.dto.PatientReport.PatientReportRequest;
import com.asanhospital.server.dto.PatientReport.PatientReportResponse;
import com.asanhospital.server.service.PatientReport.PatientReportCommandService;
import com.asanhospital.server.service.PatientReport.PatientReportQueryService;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/patientReport")
public class PatientReportRestController {
    private final PatientReportCommandService patientReportCommandService;
    private final PatientReportQueryService patientReportQueryService;
    @PatchMapping("")
    public ApiResponse<PatientReportResponse.PatientReportDTO> addPatientReportData(@RequestBody PatientReportRequest.AddPatientReportDTO reportDatas){
        return ApiResponse.onSuccess(patientReportCommandService.addPatientReportData(reportDatas));
    }

    @GetMapping("/download")
    public void downloadSensorData(@ManagerObject Manager manager, @RequestParam("medicalRecordNumber") String medicalRecordNumber, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=utf-8");
        String fileName = URLEncoder.encode("report_" + medicalRecordNumber + ".csv", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
        writer.write("\uFEFF");
        CSVWriter csvWriter = new CSVWriter(writer);

        csvWriter.writeAll(patientReportCommandService.makePatientReportCSVFile(manager, medicalRecordNumber));

        csvWriter.close();
        writer.close();
    }

    @DeleteMapping("")
    public ApiResponse<PatientReportResponse.DeletePatientReportDTO> deletePatientReport(@RequestBody PatientReportRequest.DeletePatientReportDTO medicalRecordNumbers){
        return ApiResponse.onSuccess(patientReportQueryService.deletePatientReport(medicalRecordNumbers));
    }
}
