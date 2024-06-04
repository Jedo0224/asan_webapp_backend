package com.asanhospital.server.controller;

import com.asanhospital.server.annotation.ManagerObject;
import com.asanhospital.server.apiPayload.ApiResponse;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.dto.PatientSummary.PatientSummaryResponse;
import com.asanhospital.server.service.PatientSummaryService.PatientSummaryService;
import jakarta.websocket.server.PathParam;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/summary")
public class PatientSummaryController {
    private final PatientSummaryService patientSummaryService;

    @GetMapping("/{patientId}")
    public ApiResponse<PatientSummaryResponse.SummaryResponse> getSummary(@ManagerObject Manager manager,@PathVariable String patientId,
        @PathParam("startDate") String startDate, @PathParam("endDate") String endDate){

        return ApiResponse.onSuccess(patientSummaryService.summary(manager, patientId, startDate, endDate));
    }

}
