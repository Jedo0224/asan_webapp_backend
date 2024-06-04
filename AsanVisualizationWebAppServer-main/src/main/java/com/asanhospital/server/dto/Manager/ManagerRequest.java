package com.asanhospital.server.dto.Manager;

import com.asanhospital.enums.Role;
import com.asanhospital.server.domain.Manager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ManagerRequest {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateManagerDto{
        private String managerId;
        private String password;
        private String username;
        private String phoneNumber;
        private String organization;

        public Manager newManager(String encodedPassword, List<Role> roles){
            Manager manager = Manager.builder()
                    .managerId(managerId)
                    .password(encodedPassword)
                    .username(username)
                    .phoneNumber(phoneNumber)
                    .organization(organization)
                    .roles(roles)
                    .build();

            manager.setCreatedAt(LocalDateTime.now());
            manager.setUpdatedAt(LocalDateTime.now());
            return manager;
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDto {
        private String managerId;
        private String password;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManagerSearchDTO{
        String id;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManagerDeleteDTO{
        List<String> managerIDs;
    }
}
