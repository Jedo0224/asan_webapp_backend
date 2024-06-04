package com.asanhospital.server.service.ManagerService;

import com.asanhospital.enums.Role;
import com.asanhospital.server.apiPayload.excpetion.GeneralException;
import com.asanhospital.server.apiPayload.status.ErrorStatus;
import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.domain.Patient;
import com.asanhospital.server.dto.Manager.ManagerRequest;
import com.asanhospital.server.dto.Manager.ManagerRequest.ManagerDeleteDTO;
import com.asanhospital.server.dto.Manager.ManagerResponse;
import com.asanhospital.server.dto.Manager.ManagerResponse.ManagerDTO;
import com.asanhospital.server.repository.ManagerRepository;
import com.asanhospital.server.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerCommandServiceImpl implements ManagerCommandService{
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrganizationRepository organizationRepository;

    @Autowired
    private final MongoTemplate mongoTemplate;

    @Override
    @Transactional
    public Manager createManager(ManagerRequest.CreateManagerDto createManagerDto){
        organizationRepository.findByOrganizationName(createManagerDto.getOrganization())
                .orElseThrow(() -> new GeneralException(ErrorStatus._ORGANIZATION_NOT_FOUND));

        if (managerRepository.existsById(createManagerDto.getManagerId())) {
            throw new GeneralException(ErrorStatus._MEMBER_ALREADY_EXIST);
        }

        // Password 암호화
        String encodedPassword = passwordEncoder.encode(createManagerDto.getPassword());
        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);  // USER 권한 부여
        return managerRepository.save(createManagerDto.newManager(encodedPassword, roles));
    }

    @Override
    public ManagerResponse.ManagerDTO ManagertoManagerDTO(Manager manager){

        return ManagerResponse.ManagerDTO.builder()
                .id(manager.getManagerId())
                .name(manager.getUsername())
                .phoneNumber(manager.getPhoneNumber())
                .createdAt(manager.getCreatedAt())
                .build();
    }

    @Override
    public ManagerResponse.ManagerDTO updateManager(ManagerRequest.CreateManagerDto updateManagerDto) {

        Query query = new Query(Criteria.where("managerId").is(updateManagerDto.getManagerId()));
        Update update = new Update();

        if(updateManagerDto.getManagerId() != null){
            update.set("managerId", updateManagerDto.getManagerId());
        }

        if(updateManagerDto.getPassword() != null){
            update.set("password", updateManagerDto.getPassword());
        }

        if(updateManagerDto.getUsername() != null){
            update.set("username", updateManagerDto.getUsername());
        }

        if(updateManagerDto.getPhoneNumber() != null){
            update.set("phoneNumber", updateManagerDto.getPhoneNumber());
        }

        if(updateManagerDto.getOrganization() != null){
            update.set("organization", updateManagerDto.getOrganization());
        }

        mongoTemplate.findAndModify(query, update, Manager.class);
        Manager manager = managerRepository.findByManagerId(updateManagerDto.getManagerId());
        if(manager == null){
            throw new GeneralException(ErrorStatus._MANAGER_NOT_FOUND);
        }

        return ManagerResponse.ManagerDTO.toDto(manager);
    }

    public ManagerResponse.ManagerDeleteDTO deleteManager(ManagerDeleteDTO managerDeleteDTO) {
        List<Manager> deletList = new ArrayList<>();
        for (String id : managerDeleteDTO.getManagerIDs()) {
            Manager manager = managerRepository.findByManagerId(id);
            if(manager == null){
                throw new GeneralException(ErrorStatus._MANAGER_NOT_FOUND);
            }
            deletList.add(manager);
        }

        managerRepository.deleteAll(deletList);

        return ManagerResponse.ManagerDeleteDTO.builder()
                .managerIds(managerDeleteDTO.getManagerIDs())
                .build();
    }

    @Override
    public ManagerDTO getManager(String managerId) {
        Manager manager = managerRepository.findByManagerId(managerId);
        if(manager == null){
            throw new GeneralException(ErrorStatus._MANAGER_NOT_FOUND);
        }
        return ManagerDTO.toDto(manager);
    }
}
