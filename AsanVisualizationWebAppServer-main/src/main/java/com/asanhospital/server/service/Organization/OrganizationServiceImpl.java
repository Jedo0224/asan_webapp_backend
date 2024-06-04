package com.asanhospital.server.service.Organization;

import com.asanhospital.server.apiPayload.excpetion.GeneralException;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import com.asanhospital.server.domain.Organization;
import com.asanhospital.server.dto.Organization.OrganizationRequest;
import com.asanhospital.server.dto.Organization.OrganizationRequest.DeleteOrganizationRequest;
import com.asanhospital.server.dto.Organization.OrganizationResponse;
import com.asanhospital.server.repository.OrganizationRepository;
import com.asanhospital.server.service.DatabaseSequence.DatabaseSequenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class OrganizationServiceImpl implements OrganizationService{
    private final OrganizationRepository organizationRepository;
    private final DatabaseSequenceService databaseSequenceService;

    @Override
    public OrganizationResponse.OrganizationDTO addOrganization(OrganizationRequest.AddOrganizationRequest addOrganizationRequest) {
        if(organizationRepository.findByOrganizationName(addOrganizationRequest.getOrganizationName()).isPresent()){
            throw new GeneralException(ErrorStatus._ORGANIZATION_ALREADY_EXIST);
        }

        organizationRepository.save(Organization.builder()
                .id(databaseSequenceService.generateSequence(Organization.SEQUENCE_NAME))
                .organizationName(addOrganizationRequest.getOrganizationName())
                .build()
        );

        return OrganizationResponse.OrganizationDTO.builder()
                .organizationName(addOrganizationRequest.getOrganizationName())
                .build();
    }

    @Override
    public List<OrganizationResponse.OrganizationDTO> getOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();

        List<OrganizationResponse.OrganizationDTO> organizationDTOList = new ArrayList<>();
        for(Organization organization : organizations){
            organizationDTOList.add(organization.toDTO());
        }

        return organizationDTOList;
    }

    @Override
    public OrganizationResponse.OrganizationDeleteDTO deleteOrganization(
        DeleteOrganizationRequest deleteOrganizationRequest) {
        List<Organization> deletList = new ArrayList<>();

        for (String name : deleteOrganizationRequest.getOrganizationNames()) {
            Organization organization = organizationRepository.findByOrganizationName(name).orElse(null);
            if(organization == null){
                throw new GeneralException(ErrorStatus._ORGANIZATION_NOT_FOUND);
            }

            deletList.add(organization);
        }

        organizationRepository.deleteAll(deletList);

        return OrganizationResponse.OrganizationDeleteDTO.builder()
                .organizationName(deleteOrganizationRequest.getOrganizationNames())
                .build();
    }

    @Override
    public OrganizationResponse.OrganizationDTO updateOrganization(OrganizationRequest.UpdateOrganizationRequest updateOrganizationRequest) {
        Organization organization = organizationRepository.findById(updateOrganizationRequest.getOriginalOrganizationName()).orElse(null);
        if(organization == null){
            throw new GeneralException(ErrorStatus._ORGANIZATION_NOT_FOUND);
        }

        organization.setOrganizationName(updateOrganizationRequest.getNewOrganizationName());
        organizationRepository.save(organization);

        return OrganizationResponse.OrganizationDTO.builder()
                .organizationName(updateOrganizationRequest.getNewOrganizationName())
                .build();
    }
}
