package com.asanhospital.server.apiPayload.excpetion;

import com.asanhospital.server.apiPayload.ApiResponse;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExcpetionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleGeneralException(GeneralException e, HttpServletRequest request){
        log.error("GeneralException: {}", e.getCode().getMessage());
        return handleExceptionInternal(e, e.getCode(), null, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorStatus reason,
                                                           HttpHeaders headers, HttpServletRequest request) {

        ApiResponse<Object> body = ApiResponse.onFailure(reason,null);
//        e.printStackTrace();

        WebRequest webRequest = new ServletWebRequest(request);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                reason.getHttpStatus(),
                webRequest
        );
    }
}
