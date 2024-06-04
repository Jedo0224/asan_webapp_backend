package com.asanhospital.server.service.ManagerService;

import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerQueryServiceImpl implements ManagerQueryService{
//    @Autowired
    private final ManagerRepository managerRepository;

    @Override
    public Manager getManagerById(String id){
        return managerRepository.findById(id).orElse(null);
    }

    @Override
    public void createManager(Manager manager){
        managerRepository.save(manager);
    }
}
