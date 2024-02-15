package com.ssafy.tosi.jwt;

import com.ssafy.tosi.user.dto.UserInfo;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "refreshTokens")
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer tokenId;

    private Integer userId;

    private String refreshToken;

    public void update(RefreshToken refreshToken) {
        this.userId = refreshToken.getUserId();
        this.refreshToken = refreshToken.getRefreshToken();
    }

    @Builder
    public RefreshToken (Integer userId, String refreshToken){
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}
