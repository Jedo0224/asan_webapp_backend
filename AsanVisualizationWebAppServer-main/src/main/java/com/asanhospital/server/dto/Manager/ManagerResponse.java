package com.asanhospital.server.dto.Manager;

import com.asanhospital.server.domain.Manager;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

public class ManagerResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManagerDTO {
        private String id;
        private String name;
        private String phoneNumber;
        private LocalDateTime createdAt;

        public static ManagerDTO toDto(Manager manager){
            return ManagerDTO.builder()
                    .id(manager.getManagerId())
                    .name(manager.getUsername())
                    .phoneNumber(manager.getPhoneNumber())
                    .createdAt(manager.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManagerDeleteDTO {
        private List<String> managerIds;
    }
}
