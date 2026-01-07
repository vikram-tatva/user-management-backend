package com.demoproj.user_management.services;

import com.demoproj.user_management.DTOs.RefreshTokenRequestDTO;
import com.demoproj.user_management.DTOs.TokenResponseDTO;
import com.demoproj.user_management.entities.RefreshToken;

import java.sql.Ref;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken(String username);
    RefreshToken validateRefreshToken(String refreshToken);
    RefreshToken refreshToken(RefreshTokenRequestDTO refreshRequest);
}
