package com.auth_service.service.impl;

import com.auth_service.dto.UserDTO;
import com.auth_service.service.UserService;

import java.util.Map;

public class UserServiceImpl implements UserService {
    @Override
    public void register(UserDTO userDTO) {

    }

    @Override
    public Map<String, String> login(UserDTO userDTO) {
        return Map.of();
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        return "";
    }
}
