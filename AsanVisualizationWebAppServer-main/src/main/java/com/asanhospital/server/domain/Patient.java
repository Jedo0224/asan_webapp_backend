package com.asanhospital.server.domain;

import com.asanhospital.enums.Gender;
import com.asanhospital.server.dto.Patient.PatientResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@Document(collection = "patients")
public class Patient implements UserDetails {
    @NotEmpty
    private String auxiliaryDeviceType;   // 보조기 종류
    @Id
    @NotEmpty
    private String medicalRecordNumber;   // 병록번호(아이디)
    @NotEmpty
    private String username;                  // 성함
    @NotEmpty
    private Gender gender;                // 성별
    @NotEmpty
    private LocalDate dateOfBirth;          // 생년월일
    @NotEmpty
    private String password;              // 비밀번호(전화번호 끝자리)
    @NotEmpty
    private String email;                 // 이메일
    @NotEmpty
    private String phoneNumber;           // 연락처
    @NotEmpty
    private String address;               // 주소
    @NotEmpty
    private String formFactorNumber;      // 폼팩터 넘버

    @NotEmpty
    private String deviceId; // 기기 아이디

    @NotEmpty
    private String deviceName; // 기기 이름


    private Integer disconnectionCount; // 끊긴 연결 수


    //        // 보호자 정보
    @NotEmpty
    private String guardianName;          // 이름(보호자)
    @NotEmpty
    private String guardianRelationship;  // 관계
    @NotEmpty
    private String guardianPhoneNumber;   // 연락처(보호자)

    // 초기 평가 정보
    @NotEmpty
    private String initialEvaluation;         // 초기평가

    //        // 담당 세부사항
    private String organization;          // 기관명
    private String responsiblePersonName; // 담당자 이름
    private String responsiblePersonNumber; // 담당자 번호

    private LocalDate endDate;              // 환자의 종료일
    private LocalDate startDate;            // 환자의 시작일

    @CreatedDate @Builder.Default
    private LocalDateTime registrationDate = LocalDateTime.now();   // 등록 날짜

    @Builder.Default
    private List<String> roles = new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public void incrementDisconnectionCount() {
        if (this.disconnectionCount == null) {
            this.disconnectionCount = 1;
        } else {
            this.disconnectionCount += 1;
        }
    }
    public void setCreatedAt(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
