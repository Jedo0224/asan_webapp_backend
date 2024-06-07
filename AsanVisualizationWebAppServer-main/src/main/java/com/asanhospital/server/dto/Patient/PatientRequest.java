package com.asanhospital.server.dto.Patient;

import com.asanhospital.enums.Gender;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.Patient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

public class PatientRequest {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePatientDto{
        private String auxiliaryDeviceType;   // 보조기 종류
        @Id
        private String medicalRecordNumber;   // 병록번호(아이디)
        private String username;                  // 성함
        private Gender gender;                // 성별
        private LocalDate dateOfBirth;          // 생년월일
        private String deviceId;                // 기기아이디
        private String deviceName;              // 기기종류
        @NotNull
        private String password;              // 비밀번호(전화번호 끝자리)
        @Email(message = "Invalid email format")
        private String email;                 // 이메일
        @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "Phone number must be in the format 000-0000-0000")
        private String phoneNumber;           // 연락처
        private String address;               // 주소
        private String formFactorNumber;      // 폼팩터 넘버

        // 보호자 정보
        private String guardianName;          // 이름(보호자)
        private String guardianRelationship;  // 관계
        @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "Phone number must be in the format 000-0000-0000")
        private String guardianPhoneNumber;   // 연락처(보호자)

        // 초기 평가 정보
        private String initialEvaluation;    //초기평가

        private LocalDate endDate;              // 환자의 종료일
        private LocalDate startDate;            // 환자의 시작일

        public Patient toEntity(Manager manager, String encodedPassword, List<String> roles){
            Patient patient = Patient.builder()
                    .auxiliaryDeviceType(auxiliaryDeviceType)
                    .medicalRecordNumber(medicalRecordNumber)
                    .username(username)
                    .gender(gender)
                    .dateOfBirth(dateOfBirth)
                    .deviceId(deviceId)
                    .deviceName(deviceName)
                    .password(encodedPassword)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .address(address)
                    .formFactorNumber(formFactorNumber)
                    .guardianName(guardianName)
                    .guardianRelationship(guardianRelationship)
                    .guardianPhoneNumber(guardianPhoneNumber)
                    .initialEvaluation(initialEvaluation)
                    .responsiblePersonName(manager.getUsername())
                    .responsiblePersonNumber(manager.getPhoneNumber())
                    .organization(manager.getOrganization())
                    .endDate(endDate)
                    .startDate(startDate)
                    .roles(roles)
                    .build();

            return patient;
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdatePatientDto{
        @NotNull
        private String auxiliaryDeviceType;   // 보조기 종류
        @Id
        private String medicalRecordNumber;   // 병록번호(아이디)
        private String username;                  // 성함
        private Gender gender;                // 성별
        private LocalDate dateOfBirth;          // 생년월일
        private String password;              // 비밀번호(전화번호 끝자리)
        @Email(message = "Invalid email format")
        private String email;                 // 이메일
        @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "Phone number must be in the format 000-0000-0000")
        private String phoneNumber;           // 연락처
        private String address;               // 주소
        private String formFactorNumber;      // 폼팩터 넘버

        // 보호자 정보
        private String guardianName;          // 이름(보호자)
        private String guardianRelationship;  // 관계
        @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "Phone number must be in the format 000-0000-0000")
        private String guardianPhoneNumber;   // 연락처(보호자)

        // 초기 평가 정보
        private String initialEvaluation;    //초기평가

        //        // 담당 세부사항
        private String organization;          // 기관명
        private String responsiblePersonName;   // 담당자 이름
        private String responsiblePersonNumber; // 담당자 연락처

        private LocalDate endDate;              // 환자의 종료일
        private LocalDate startDate;            // 환자의 시작일
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetPatientDto {
        private String medicalRecordNumber;
    }

    /*
    환자 검색 RequestDTO
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchPatientDto {
        private String patientName;             //환자명
        private String auxiliaryDeviceType;     //보조기종류
        private LocalDate birthDate;               //생년월일
        private LocalDate startDate;        //등록일
        private String responsiblePersonName; //담당자이름
        private int page;                       //페이지
        private int size;                       //사이즈
        private String deviceId;
        private String deviceName;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeletePatientDto {
        private List<String> medicalRecordNumbers;
    }

}
