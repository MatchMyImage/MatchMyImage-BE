package com.LetMeDoWith.LetMeDoWith.controller.auth;

import com.LetMeDoWith.LetMeDoWith.dto.requestDto.AccessTokenReqDto;
import com.LetMeDoWith.LetMeDoWith.dto.requestDto.CreateTokenRefreshReqDto;
import com.LetMeDoWith.LetMeDoWith.dto.responseDto.CreateTokenRefreshResDto;
import com.LetMeDoWith.LetMeDoWith.dto.valueObject.AccessTokenVO;
import com.LetMeDoWith.LetMeDoWith.entity.member.Member;
import com.LetMeDoWith.LetMeDoWith.enums.common.SuccessResponseStatus;
import com.LetMeDoWith.LetMeDoWith.service.AuthService;
import com.LetMeDoWith.LetMeDoWith.service.Member.MemberService;
import com.LetMeDoWith.LetMeDoWith.util.HeaderUtil;
import com.LetMeDoWith.LetMeDoWith.util.ResponseUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final MemberService memberService;
    
    @PostMapping("/token/refresh")
    public ResponseEntity createTokenRefresh(@RequestBody CreateTokenRefreshReqDto requestBody) {
        
        String userAgent = HeaderUtil.getUserAgent();
        
        CreateTokenRefreshResDto result = authService.createTokenRefresh(requestBody.accessToken(),
                                                                         requestBody.refreshToken(),
                                                                         userAgent);
        
        return ResponseUtil.createSuccessResponse(result);
    }
    
    @PostMapping("/token")
    public ResponseEntity getAccessToken(@RequestBody AccessTokenReqDto accessTokenReqDto) {
        Jws<Claims> verifiedIdToken = authService.getVerifiedOidcIdToken(accessTokenReqDto.provider(),
                                                                         accessTokenReqDto.idToken());
        
        Claims body = verifiedIdToken.getBody();
        String email = body.get("email", String.class);
        
        // 이미 가입되어 있는 유저인지 확인
        Member registeredMember = memberService.getRegisteredMember(accessTokenReqDto.provider(),
                                                                    email);
        
        if (registeredMember != null) {
            // 이미 가입되어 있다면 access token을 발급한다.
            AccessTokenVO atk = authService.login(registeredMember);
            
            return ResponseUtil.createSuccessResponse(atk);
        } else {
            // 가입되어 있지 않다면 회원가입 절차를 진행한다.
            authService.proceedToSignup(accessTokenReqDto);
            
            return ResponseUtil.createSuccessResponse(SuccessResponseStatus.OK);
        }
    }
}