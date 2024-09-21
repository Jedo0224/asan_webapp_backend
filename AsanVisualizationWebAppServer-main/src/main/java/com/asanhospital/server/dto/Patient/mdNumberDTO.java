package com.asanhospital.server.dto.Patient;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class mdNumberDTO {
    private String medicalRecordNumber;
}
