package com.asanhospital.server.controller;

import com.asanhospital.server.apiPayload.ApiResponse;
import com.asanhospital.server.dto.Organization.OrganizationRequest;
import com.asanhospital.server.dto.Organization.OrganizationResponse;
import com.asanhospital.server.service.Organization.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationRestController {
    private final OrganizationService organizationService;

    @PostMapping("")
    public ApiResponse<OrganizationResponse.OrganizationDTO> addOrganization(@RequestBody OrganizationRequest.AddOrganizationRequest addOrganizationRequest){
        return ApiResponse.onSuccess(organizationService.addOrganization(addOrganizationRequest));
    }

    @PatchMapping("")
    public ApiResponse<OrganizationResponse.OrganizationDTO> updateOrganization(@RequestBody OrganizationRequest.UpdateOrganizationRequest updateOrganizationRequest){
        return ApiResponse.onSuccess(organizationService.updateOrganization(updateOrganizationRequest));
    }

    @GetMapping("")
    public ApiResponse<List<OrganizationResponse.OrganizationDTO>> getOrganizations(){
        return ApiResponse.onSuccess(organizationService.getOrganizations());
    }

    @DeleteMapping("")
    public ApiResponse<OrganizationResponse.OrganizationDeleteDTO> deleteOrganization(@RequestBody OrganizationRequest.DeleteOrganizationRequest organizationNames){
        return ApiResponse.onSuccess(organizationService.deleteOrganization(organizationNames));
    }
}
