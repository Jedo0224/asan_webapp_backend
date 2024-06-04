package com.asanhospital.server.controller;

import com.asanhospital.server.apiPayload.ApiResponse;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.dto.Manager.ManagerRequest;
import com.asanhospital.server.dto.Manager.ManagerResponse;
import com.asanhospital.server.dto.Patient.PatientRequest;
import com.asanhospital.server.service.ManagerService.ManagerCommandService;
import com.asanhospital.server.service.ManagerService.ManagerQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerRestController {
    @Autowired
    ManagerCommandService managerCommandService;
    @Autowired
    ManagerQueryService managerQueryService;

    @PostMapping("/new")
    public ApiResponse<ManagerResponse.ManagerDTO> createManager(@RequestBody @Valid ManagerRequest.CreateManagerDto createManagerDto){
        Manager manager = managerCommandService.createManager(createManagerDto);
        managerQueryService.createManager(manager);

        return ApiResponse.onSuccess(managerCommandService.ManagertoManagerDTO(manager));
    }

    @GetMapping("")
    public ApiResponse<ManagerResponse.ManagerDTO> getManager(@RequestParam String managerID) {
        return ApiResponse.onSuccess(managerCommandService.getManager(managerID));
    }

    @PatchMapping("/update")
    public ApiResponse<ManagerResponse.ManagerDTO> updateManager(@RequestBody ManagerRequest.CreateManagerDto updateManagerDto) {
        return ApiResponse.onSuccess(managerCommandService.updateManager(updateManagerDto));
    }
  
    @DeleteMapping("/delete")
    public ApiResponse<ManagerResponse.ManagerDeleteDTO> deleteManager(@RequestBody ManagerRequest.ManagerDeleteDTO managerIds) {

        return ApiResponse.onSuccess(managerCommandService.deleteManager(managerIds));

    }
}
