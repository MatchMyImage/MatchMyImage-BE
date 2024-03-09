package com.LetMeDoWith.LetMeDoWith.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.LetMeDoWith.LetMeDoWith.util.AuthTokenUtil;
import com.LetMeDoWith.LetMeDoWith.util.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticateInterceptor implements HandlerInterceptor {

	private final AuthTokenUtil authTokenUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		if(request.getMethod().equals("OPTIONS")) {
			return true;
		}

		String memberId = authTokenUtil.validateAccessToken(HeaderUtil.getAccessToken());
		request.setAttribute("memberId", memberId);

		return true;

	}
}
