package com.asanhospital.server.apiPayload.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    //환자 관련 응답
    _PATIENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "PATIENT001", "존재하지 않는 환자입니다."),
    _PATIENT_MANAGER_ORGANIZATION_NOT_MATCH(HttpStatus.BAD_REQUEST, "PATIENT002", "환자의 담당자와 조직이 일치하지 않습니다."),

    //멤버 관련 응답
    _MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER001", "존재하지 않는 회원입니다."),
    _MEMBER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "MEMBER002", "이미 존재하는 회원입니다."),

    //환자 레포트 관련 응답
    _PATIENT_REPORT_NOT_FOUND(HttpStatus.BAD_REQUEST, "PATIENT_REPORT001", "존재하지 않는 환자 레포트입니다."),

    //환자 레포트 관련 응답
    _SENSOR_REPORT_NOT_FOUND(HttpStatus.BAD_REQUEST, "SENSOR_REPORT001", "존재하지 않는 센서 정보입니다."),

    //메니저 관련 응답
    _MANAGER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MANAGER001", "존재하지 않는 담당자입니다."),

    //조직(소속)관련 응답
    _ORGANIZATION_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "ORGANIZATION001", "조직이 이미 존재합니다"),
    _ORGANIZATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "ORGANIZATION002", "조직이 존재하지 않습니다"),

    //파일 관련 응답
    _FILE_IS_EMPTY(HttpStatus.BAD_REQUEST, "FILE001", "파일이 비어있습니다."),
    _FILE_READ_ERROR(HttpStatus.BAD_REQUEST, "FILE002", "파일을 읽는 중 에러가 발생했습니다1."),
    _FILE_READ_ERROR2(HttpStatus.BAD_REQUEST, "FILE002", "파일을 읽는 중 에러가 발생했습니다2.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
