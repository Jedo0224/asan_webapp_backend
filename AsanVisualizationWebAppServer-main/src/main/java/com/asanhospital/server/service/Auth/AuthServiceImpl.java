package com.asanhospital.server.service.Auth;

import com.asanhospital.server.dto.JwtToken;
import com.asanhospital.server.service.Jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final CustomAuthenticationManager customAuthenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    public JwtToken login(String id, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);
        Authentication authentication = customAuthenticationManager.authenticate(authenticationToken);
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        log.info(jwtToken.getAccessToken());
        return jwtToken;
    }


}
