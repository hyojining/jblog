package com.poscodx.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.vo.UserVo;

/**
 * 컨트롤러 메서드 호출 전에 실행
 * 블로그 관리자 페이지에 대한 접근 권한을 검사
 */
public class AdminInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 요청 URL에서 블로그 ID 추출
        String[] pathInfo = request.getRequestURI().split("/");
        String blogId = pathInfo[2]; // URL 경로에서 블로그 ID 추출

        // 세션에서 현재 로그인한 사용자 정보 가져오기
        UserVo authUser = (UserVo) request.getSession().getAttribute("authUser");

        if (authUser == null || !authUser.getId().equals(blogId)) {
            // 권한 없는 경우
        	System.out.println("관리자 페이지 접근 불가");
            response.sendRedirect(request.getContextPath() + "/" + blogId); // 블로그 메인 페이지로 
            return false; // 권한이 없으므로 요청 처리 중단
        }
        return true; // 권한이 있으므로 요청 처리 진행
	}

}
