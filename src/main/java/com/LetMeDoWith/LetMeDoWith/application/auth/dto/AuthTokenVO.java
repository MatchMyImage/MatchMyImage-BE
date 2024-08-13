package com.LetMeDoWith.LetMeDoWith.application.auth.dto;

import com.LetMeDoWith.LetMeDoWith.domain.auth.RefreshToken;
import java.time.LocalDateTime;

public record AuthTokenVO(String token, LocalDateTime expireAt) {
    
    /**
     * Refresh Token을 AuthTokenVO 타입으로 변환
     *
     * @param rtk
     * @return
     */
    public static AuthTokenVO fromRtk(RefreshToken rtk) {
        return new AuthTokenVO(rtk.getToken(),
                               LocalDateTime.now().plusSeconds(rtk.getExpireSec()));
    }
    
}