package com.asanhospital.server.service.ManagerService;

import com.asanhospital.server.domain.Manager;

public interface ManagerQueryService {

    // ID를 통해 DB에서 Manager를 찾아 반환하는 메소드
    Manager getManagerById(String id);
    void createManager(Manager manager);
}
