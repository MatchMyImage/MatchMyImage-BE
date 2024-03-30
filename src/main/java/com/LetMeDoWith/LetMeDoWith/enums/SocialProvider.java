package com.LetMeDoWith.LetMeDoWith.enums;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialProvider implements BaseEnum {
    
    KAKAO("KAKAO", "카카오", "https://kauth.kakao.com", "kakaoAud"),
    GOOGLE("GOOGLE", "구글", "https://accounts.google.com", "googleAud"),
    APPLE("APPLE", "애플", "", "appleAud");
    
    private final String code;
    private final String description;
    // 자격증명 제공자 검증을 위한 필드
    private final String issUrl;
    // 자격증명 제공자에 등록된 애플리케이션 아이디/
    private final String aud;
    
    
    /**
     * OIDC id 토큰의 iss 필드 값으로 social provider 조회
     *
     * @param issUrl id 토큰 내 iss 필드 값
     * @return 자격 증명 제공자의 social provider 타입
     */
    public SocialProvider fromIssUrl(String issUrl) {
        return Arrays.stream(SocialProvider.values())
                     .filter(socialProvider -> socialProvider.getIssUrl().equals(issUrl))
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new);
    }
    
}