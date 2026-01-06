package com.demoproj.user_management.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "refreshtokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RefreshToken {
    @Id
    private String refreshToken;
    private Instant expires;

    @ManyToOne
    private User user;
}
