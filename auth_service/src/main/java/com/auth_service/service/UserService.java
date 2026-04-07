package com.auth_service.service;

import com.auth_service.dto.UserDTO;

import java.util.Map;

public interface UserService {
    void register(UserDTO userDTO);

    Map<String, String> login(UserDTO userDTO);

    String refreshAccessToken(String refreshToken);
}
