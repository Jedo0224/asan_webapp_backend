package com.asanhospital.server.dto.Organization;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrganizationRequest {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddOrganizationRequest {
        private String organizationName;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateOrganizationRequest {
        private String originalOrganizationName;
        private String newOrganizationName;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteOrganizationRequest {
        private List<String> organizationNames;
    }

}
