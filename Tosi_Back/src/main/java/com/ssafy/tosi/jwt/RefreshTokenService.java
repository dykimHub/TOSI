package com.ssafy.tosi.jwt;

import com.ssafy.tosi.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }

//    public void insertRefreshToken(RefreshToken refreshToken) {
//        refreshTokenRepository.save(refreshToken);
//    }

    public void updateRefreshToken(RefreshToken refreshToken) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByUserId(refreshToken.getUserId());

        if(optionalRefreshToken.isPresent()) {
            RefreshToken foundRefreshToken = optionalRefreshToken.get();

            foundRefreshToken.update(refreshToken);
            refreshTokenRepository.save(foundRefreshToken);
            //            foundRefreshToken.setUserId(refreshToken.getUserId());
//            foundRefreshToken.setRefreshToken(refreshToken.getRefreshToken());
        } else {
            refreshTokenRepository.save(refreshToken);
        }
//
//        Optional<User> user = userRepository.findById(userInfo.getUserId());
//        Optional<RefreshToken> foundRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken.getRefreshToken());
//        foundRefreshToken.orElse(null).update(refreshToken);

    }


//    private void saveRefreshToken(Integer userId, String newRefreshToken) {
//        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
//                .map(entity -> entity.update(newRefreshToken))
//                .orElse(new RefreshToken(userId, newRefreshToken));
//
//        refreshTokenRepository.save(refreshToken);
//    }

}

