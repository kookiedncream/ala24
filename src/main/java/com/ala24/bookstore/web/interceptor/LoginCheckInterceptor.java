package com.ala24.bookstore.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.ala24.bookstore.web.session.SessionName.LOGIN_MEMBER;

/**
 * 로그인 유무를 확인하는 컨트롤러
 */
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestURI = request.getRequestURI();
		
		log.info("인증 체크 인터셉터 시작 : {}", requestURI);

		HttpSession session = request.getSession(false);

		if (hasNotPermission(session)) {
			log.info("미인증 사용자 요청");
			response.sendRedirect("/login?redirectURL=" + requestURI);
			return false;
		}
		
		return true;
	}

	private boolean hasNotPermission(HttpSession session) {
		return session == null ||
				session.getAttribute(LOGIN_MEMBER) == null;
	}
}
