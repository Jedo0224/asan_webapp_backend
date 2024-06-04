package com.asanhospital.server.service.PatientSummaryService;

import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.PatientReport;
import com.asanhospital.server.dto.PatientSummary.PatientSummaryResponse.SummaryResponse;
import java.time.LocalDate;
import java.util.List;

public interface PatientSummaryService {

    SummaryResponse summary(Manager manager, String medicalRecordNumber, String startDate, String endDate);

}
