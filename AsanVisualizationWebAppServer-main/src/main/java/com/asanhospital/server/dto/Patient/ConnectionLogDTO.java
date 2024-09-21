package com.asanhospital.server.dto.Patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionLogDTO {
    private List<LocalDateTime> connectionLogList;
    private List<LocalDateTime> disconnectionLogList;
}
