package com.LetMeDoWith.LetMeDoWith.enums;

import com.LetMeDoWith.LetMeDoWith.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.enums.converter.member.SocialProviderConverter;
import com.LetMeDoWith.LetMeDoWith.exception.RestApiException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonDeserialize(using = SocialProviderConverter.class)
public enum SocialProvider implements BaseEnum {
    
    KAKAO("KAKAO", "카카오", "https://kauth.kakao.com"),
    GOOGLE("GOOGLE", "구글", "https://accounts.google.com"),
    APPLE("APPLE", "애플", "");
    
    private final String code;
    private final String description;
    // 자격증명 제공자 검증을 위한 필드
    private final String issUrl;
    
    
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
                     .orElseThrow(() -> new RestApiException(FailResponseStatus.MANDATORY_PARAM_ERROR_PROVIDER));
    }
    
}