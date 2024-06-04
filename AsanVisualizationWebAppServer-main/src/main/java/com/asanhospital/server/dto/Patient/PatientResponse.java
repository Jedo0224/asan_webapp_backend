package com.asanhospital.server.dto.Patient;

import com.asanhospital.enums.Gender;
import com.asanhospital.server.domain.Patient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PatientResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientDto {
        @NotNull
        private String auxiliaryDeviceType;   // 보조기 종류
        @Id
        @NotNull
        private String medicalRecordNumber;   // 병록번호(아이디)
        @NotNull
        private String username;                  // 성함
        @NotNull
        private Gender gender;                // 성별
        @NotNull
        private LocalDate dateOfBirth;          // 생년월일
        @NotNull
        private String password;              // 비밀번호(전화번호 끝자리)
        @NotNull
        @Email(message = "Invalid email format")
        private String email;                 // 이메일
        @NotNull
        @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "Phone number must be in the format 000-0000-0000")
        private String phoneNumber;           // 연락처
        @NotNull
        private String address;               // 주소
        @NotNull
        private String formFactorNumber;      // 폼팩터 넘버

        // 보호자 정보
        @NotNull
        private String guardianName;          // 이름(보호자)
        @NotNull
        private String guardianRelationship;  // 관계
        @NotNull
        @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "Phone number must be in the format 000-0000-0000")
        private String guardianPhoneNumber;   // 연락처(보호자)

        // 초기 평가 정보
        @NotNull
        private String initialEvaluation;   // 초기평가

        // 담당 세부사항
        private String organization;          // 기관명
        private String responsiblePersonName; // 담당자 이름
        @NotNull
        @Pattern(regexp = "\\d{2}-\\d{3}-\\d{4}", message = "Phone number must be in the format 000-0000-0000")
        private String responsiblePersonNumber; // 담당자 번호

        private LocalDate endDate;              // 환자의 종료일
        private LocalDate startDate;            // 환자의 시작일
        @NotNull
        private LocalDateTime registrationDate;   // 등록 날짜

        @Builder.Default
        private List<String> roles = new ArrayList<>();

        static public PatientResponse.PatientDto toDto(Patient patient) {
            return PatientResponse.PatientDto.builder()
                .auxiliaryDeviceType(patient.getAuxiliaryDeviceType())
                .medicalRecordNumber(patient.getMedicalRecordNumber())
                .username(patient.getUsername())
                .gender(patient.getGender())
                .dateOfBirth(patient.getDateOfBirth())
                .password(patient.getPassword())
                .email(patient.getEmail())
                .phoneNumber(patient.getPhoneNumber())
                .address(patient.getAddress())
                .formFactorNumber(patient.getFormFactorNumber())
                .guardianName(patient.getGuardianName())
                .guardianRelationship(patient.getGuardianRelationship())
                .guardianPhoneNumber(patient.getGuardianPhoneNumber())
                .initialEvaluation(patient.getInitialEvaluation())
                .organization(patient.getOrganization())
                .endDate(patient.getEndDate())
                .startDate(patient.getStartDate())
                .responsiblePersonName(patient.getResponsiblePersonName())
                .responsiblePersonNumber(patient.getResponsiblePersonNumber())
                .registrationDate(patient.getRegistrationDate())
                .roles(patient.getRoles())
                .build();
        }
    }

    public static List<PatientDto> patientListToDTOList(List<Patient> patients){
        List<PatientDto> patientDtoList = new ArrayList<>();
        for (Patient p : patients){
            patientDtoList.add(PatientDto.toDto(p));
        }

        return patientDtoList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeletePatientDTO{
        private List<String> medicalRecordNumbers;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientMainInfoDTO{
        private String auxiliaryDeviceType;   // 보조기 종류
        private String medicalRecordNumber;   // 병록번호(아이디)
        private String username;                  // 성함
        private Gender gender;                // 성별
        private LocalDate dateOfBirth;          // 생년월일
        private String responsiblePersonName; // 담당자 이름
        private LocalDate startDate;            // 환자의 시작일
        private Long remainingDays;            //남은기간

        static public PatientMainInfoDTO toDto(Patient patient) {
            Long remainingDays = null;
            if (patient.getEndDate() != null)
                remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), patient.getEndDate());

            return PatientMainInfoDTO.builder()
                .auxiliaryDeviceType(patient.getAuxiliaryDeviceType())
                .medicalRecordNumber(patient.getMedicalRecordNumber())
                .username(patient.getUsername())
                .gender(patient.getGender())
                .dateOfBirth(patient.getDateOfBirth())
                .responsiblePersonName(patient.getResponsiblePersonName())
                .startDate(patient.getStartDate())
                .remainingDays(remainingDays)
                .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchPatientDTO{
        private List<PatientMainInfoDTO> medicalRecordNumbers;
        private Long totalElements;
        private int numberOfElements;
        private int totalPages;
        private int pageSize;
        private int pageNumber;
    }


}
