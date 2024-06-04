package com.asanhospital.server.service.Organization;

import com.asanhospital.server.dto.Organization.OrganizationRequest;
import com.asanhospital.server.dto.Organization.OrganizationRequest.DeleteOrganizationRequest;
import com.asanhospital.server.dto.Organization.OrganizationResponse;

import java.util.List;

public interface OrganizationService {
    OrganizationResponse.OrganizationDTO addOrganization(OrganizationRequest.AddOrganizationRequest addOrganizationRequest);
    List<OrganizationResponse.OrganizationDTO> getOrganizations();
    OrganizationResponse.OrganizationDeleteDTO deleteOrganization(
        DeleteOrganizationRequest deleteOrganizationRequest);

    OrganizationResponse.OrganizationDTO updateOrganization(OrganizationRequest.UpdateOrganizationRequest updateOrganizationRequest);
}
