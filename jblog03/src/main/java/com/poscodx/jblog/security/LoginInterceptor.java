package com.poscodx.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;


/**
 * 사용자 로그인 요청을 가로채고, 사용자가 제공한 이메일과 비밀번호를 사용하여 인증 시도
 * 인증 성공하면 세션에 사용자 정보를 저장하고 홈 메인 화면으로 리디렉션
 * 인증 실패하면 다시 로그인 화면으로 이동
 */
public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception { // 요청이 컨트롤러에 도달하기 전에 실행되는 메서드
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");

		UserVo authUser = userService.getUser(id, password);
		
		// 사용자 인증 실패
		if(authUser == null) {
			request.setAttribute("id", id); // 실패한 이메일을 request의 속성에 저장
			request
				.getRequestDispatcher("/WEB-INF/views/user/login.jsp")
				.forward(request, response); // 로그인 화면으로 포워딩
			
			return false; // 이후의 인터셉터 실행을 중단하고 컨트롤러로 요청을 전달하지 않음
		}
		
		// 사용자 인증 성공
		HttpSession session = request.getSession(true); // 기존 세션 가져와서
		session.setAttribute("authUser", authUser); // authUser 객체를 세션에 저장
		response.sendRedirect(request.getContextPath()); // main 화면으로 리디렉션
		
		return false;
	}
}