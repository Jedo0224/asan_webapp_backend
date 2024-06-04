package com.asanhospital.server.service.Auth;

import com.asanhospital.server.dto.JwtToken;

public interface AuthService {

    JwtToken login(String id, String password);

}
