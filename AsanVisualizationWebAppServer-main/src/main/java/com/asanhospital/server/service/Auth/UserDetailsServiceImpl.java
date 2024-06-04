package com.asanhospital.server.service.Auth;

import com.asanhospital.server.domain.Manager;
import com.asanhospital.server.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return managerRepository.findById(id)
                .map(this::createManagerDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 관리자를 찾을 수 없습니다."));
    }

    // 해당 관리자 계정 있으면 UserDetails 객체로 만들어 return
    private UserDetails createManagerDetails(Manager manager){
        return User.builder()
                .username(manager.getManagerId())
                .password(passwordEncoder.encode(manager.getPassword()))
                .roles(manager.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }
}
