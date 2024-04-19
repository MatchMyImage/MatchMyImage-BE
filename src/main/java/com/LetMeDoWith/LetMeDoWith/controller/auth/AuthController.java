package com.LetMeDoWith.LetMeDoWith.controller.auth;

import com.LetMeDoWith.LetMeDoWith.dto.requestDto.CreateTokenRefreshReqDto;
import com.LetMeDoWith.LetMeDoWith.dto.requestDto.createAccessTokenReqDto;
import com.LetMeDoWith.LetMeDoWith.dto.responseDto.CreateTokenRefreshResDto;
import com.LetMeDoWith.LetMeDoWith.dto.valueObject.AccessTokenVO;
import com.LetMeDoWith.LetMeDoWith.enums.common.SuccessResponseStatus;
import com.LetMeDoWith.LetMeDoWith.service.AuthService;
import com.LetMeDoWith.LetMeDoWith.service.Member.MemberService;
import com.LetMeDoWith.LetMeDoWith.util.HeaderUtil;
import com.LetMeDoWith.LetMeDoWith.util.ResponseUtil;
import java.util.Optional;
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
    public ResponseEntity createAccessToken(@RequestBody createAccessTokenReqDto createAccessTokenReqDto) {
        Optional<AccessTokenVO> tokenRequestResult = authService.createToken(
            createAccessTokenReqDto);
        
        return ResponseUtil.createSuccessResponse(
            tokenRequestResult.isPresent()
                ? tokenRequestResult.get()
                : SuccessResponseStatus.OK);
    }
}