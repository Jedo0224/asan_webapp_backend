package com.asanhospital.server.domain;

import com.asanhospital.server.dto.Organization.OrganizationResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "organizations")
public class Organization {
    @Transient
    public static final String SEQUENCE_NAME = "orgainization_sequence";

    private Long id;
    private String organizationName;

    public OrganizationResponse.OrganizationDTO toDTO(){
        return OrganizationResponse.OrganizationDTO.builder()
                .organizationName(organizationName)
                .build();
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
