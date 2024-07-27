package com.LetMeDoWith.LetMeDoWith.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LetMeDoWith.LetMeDoWith.common.enums.common.FailResponseStatus;
import com.LetMeDoWith.LetMeDoWith.common.exception.RestApiException;
import com.LetMeDoWith.LetMeDoWith.common.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/health-check")
@RequiredArgsConstructor
public class HealthCheckController {

	@GetMapping("")
	public ResponseEntity retrieveMemberInfo(HttpServletRequest request) {
		return ResponseUtil.createSuccessResponse();
	}

	@GetMapping("/test")
	public ResponseEntity testException() {

		throw new RestApiException(FailResponseStatus.UNAUTHORIZED);

	}
}

