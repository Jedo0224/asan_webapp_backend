package com.asanhospital.server.controller;

import com.asanhospital.server.annotation.ManagerObject;
import com.asanhospital.server.apiPayload.ApiResponse;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.dto.JwtToken;
import com.asanhospital.server.dto.Manager.ManagerRequest;
import com.asanhospital.server.dto.Manager.ManagerResponse;
import com.asanhospital.server.service.Auth.AuthService;
import com.asanhospital.server.service.ManagerService.ManagerCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService managerService;
    @Autowired
    private ManagerCommandService managerCommandService;

    @PostMapping("/login")
    public ApiResponse<JwtToken> login(@RequestBody ManagerRequest.LoginDto loginDto){
        String username = loginDto.getManagerId();
        String password = loginDto.getPassword();
        log.info("request id = {}, password = {}", username, password);

        return ApiResponse.onSuccess(managerService.login(username, password));
    }
    @PostMapping("/signUp")
    public ApiResponse<Manager> createManager(@RequestBody ManagerRequest.CreateManagerDto createManagerDto) {
        Manager savedMemberDto = managerCommandService.createManager(createManagerDto);
        return ApiResponse.onSuccess(savedMemberDto);
    }

    @GetMapping("/info")
    public ApiResponse<ManagerResponse.ManagerDTO> getManager(@ManagerObject Manager manager) {
        return ApiResponse.onSuccess(ManagerResponse.ManagerDTO.toDto(manager));
    }

    @GetMapping("/test")
    public ApiResponse<String> testPostRequest() {
        log.info("POST request successful");
        return ApiResponse.onSuccess("POST request successful");
    }
}
