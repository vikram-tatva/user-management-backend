package com.demoproj.user_management.services.impl;

import com.demoproj.user_management.DTOs.RefreshTokenRequestDTO;
import com.demoproj.user_management.DTOs.TokenResponseDTO;
import com.demoproj.user_management.entities.RefreshToken;
import com.demoproj.user_management.entities.User;
import com.demoproj.user_management.exceptions.UnauthorizedException;
import com.demoproj.user_management.repositories.RefreshTokenRepository;
import com.demoproj.user_management.repositories.UserRepository;
import com.demoproj.user_management.services.RefreshTokenService;
import com.demoproj.user_management.utils.TokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenSericeImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;

    @Override
    public RefreshToken generateRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .expires(Instant.now().plusMillis(tokenUtil.getRefresh().getExpire_in_ms()))
                .user(userRepository.findByUsername(username).get())
                .build();

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    @Override
    public RefreshToken validateRefreshToken(String refreshToken) {

        RefreshToken token = refreshTokenRepository.findById(refreshToken).orElseThrow(() ->
                new UnauthorizedException("Invalid Refresh Token!"));

        if (token.getExpires().isBefore(Instant.now())) {
            refreshTokenRepository.deleteById(refreshToken);
            throw new UnauthorizedException("Refresh Token is expired!");
        }

        return token;
    }

    @Override
    public RefreshToken refreshToken(RefreshTokenRequestDTO refreshRequest) {
        return validateRefreshToken(refreshRequest.getRefreshToken());
    }
}
