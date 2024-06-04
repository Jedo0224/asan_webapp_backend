package com.asanhospital.server.controller;

import com.asanhospital.server.apiPayload.ApiResponse;
import com.asanhospital.server.apiPayload.excpetion.GeneralException;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import com.asanhospital.server.dto.TempResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
public class TempRestController {

//    private final TempQueryService tempQueryService;

    @GetMapping("/test")
    public ApiResponse<TempResponse.TempDTO> testAPI(@RequestParam(name = "testString") String testString){

        if(testString.equals("test")){
            throw new GeneralException(ErrorStatus._BAD_REQUEST);
        }

        TempResponse.TempDTO response = TempResponse.TempDTO.builder()
                .testString("Test!!!!!!!!!!!!!!!!!!")
                .build();

        return ApiResponse.onSuccess(response);
    }
}
