package com.poscodx.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 사용자 로그아웃 요청을 가로채고,
 * 현재 세션에서 사용자 정보를 제거한 후 세션을 무효화 시킴
 * 그런 다음 메인화면으로 리디렉션하여 로그아웃 작업 완료
 */
public class LogoutInterceptor implements HandlerInterceptor { // HTTP 요청이 컨트롤러로 전달되기 전에 실행되는 메서드

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(); // 현재 세션을 가져와서
		session.removeAttribute("authUser"); // "authUser"라는 속성을 제거
		session.invalidate(); // 현재 세션을 무료화시킴 -> 세션에 저장된 모든 데이터 삭제
		
		response.sendRedirect(request.getContextPath()); // main 화면으로 리디렉션
		return false; // 이후의 인터셉터 실행을 중단하고 컨트롤러로 요청을 전달하지 않음
	}

}