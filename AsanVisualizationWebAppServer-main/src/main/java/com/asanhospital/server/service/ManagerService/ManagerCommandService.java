package com.asanhospital.server.service.ManagerService;

import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.dto.Manager.ManagerRequest;
import com.asanhospital.server.dto.Manager.ManagerRequest.ManagerDeleteDTO;
import com.asanhospital.server.dto.Manager.ManagerResponse;
import com.asanhospital.server.dto.Manager.ManagerResponse.ManagerDTO;

public interface ManagerCommandService {
    Manager createManager(ManagerRequest.CreateManagerDto createManagerDto);
    ManagerResponse.ManagerDTO ManagertoManagerDTO(Manager manager);
    ManagerResponse.ManagerDTO updateManager(ManagerRequest.CreateManagerDto updateManagerDto);
    ManagerResponse.ManagerDeleteDTO deleteManager(ManagerDeleteDTO managerIds);
    ManagerDTO getManager(String managerId);
}
