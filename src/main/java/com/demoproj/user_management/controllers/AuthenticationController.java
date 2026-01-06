package com.demoproj.user_management.controllers;

import com.demoproj.user_management.DTOs.LoginDTO;
import com.demoproj.user_management.DTOs.RefreshTokenRequestDTO;
import com.demoproj.user_management.DTOs.TokenResponseDTO;
import com.demoproj.user_management.DTOs.UserResponseDTO;
import com.demoproj.user_management.entities.RefreshToken;
import com.demoproj.user_management.services.RefreshTokenService;
import com.demoproj.user_management.utils.JWTUtil;
import com.demoproj.user_management.utils.TokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JWTUtil  jwtUtil;
    private final TokenUtil tokenUtil;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginDTO login)
    {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
            );

            TokenResponseDTO tokenResponse = TokenResponseDTO.builder()
                    .accessToken(jwtUtil.generateToken(login.getUsername()))
                    .accessTokenExpiresIn(tokenUtil.getAccess().getExpire_in_ms())
                    .refreshToken(refreshTokenService.generateRefreshToken(login.getUsername()).getRefreshToken())
                    .refreshTokenExpiresIn(tokenUtil.getRefresh().getExpire_in_ms())
                    .build();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(tokenResponse);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO>  refreshToken(@RequestBody RefreshTokenRequestDTO refreshRequest)
    {
        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(refreshRequest.getRefreshToken());
        TokenResponseDTO tokenResponse = TokenResponseDTO.builder()
                .accessToken(jwtUtil.generateToken(refreshToken.getUser().getUsername()))
                .accessTokenExpiresIn(tokenUtil.getAccess().getExpire_in_ms())
                .refreshToken(refreshTokenService.generateRefreshToken(refreshToken.getUser().getUsername()).getRefreshToken())
                .refreshTokenExpiresIn(tokenUtil.getRefresh().getExpire_in_ms())
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tokenResponse);
    }
}
