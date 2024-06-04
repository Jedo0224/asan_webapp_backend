package com.asanhospital.server.apiPayload.excpetion;

import com.asanhospital.server.apiPayload.status.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException{
    private final ErrorStatus code;

    public GeneralException(ErrorStatus code) {
        super(code.getMessage());
        this.code = code;
    }
}
