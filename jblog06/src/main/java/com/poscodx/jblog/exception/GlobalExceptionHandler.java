package com.poscodx.jblog.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 애플리케이션 내에서 발생하는 예외를 중앙에서 처리하고, 
 * 예외 정보를 로깅하며, 
 * 사용자에게 오류 페이지를 표시하는 역할을 수행
 */

// 여러 컨트롤러 클래스에서 발생하는 예외를 한 곳에서 처리
@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class); // 로깅 객체 초기화 (로그를 기록하기 위해 사용)
	
	//전체 애플리케이션에서 발생하는 예외를 처리
	@ExceptionHandler(Exception.class)
	public String handlerException(Model model, Exception e) {
		
		// 1. 404 Error 처리
		if(e instanceof NoHandlerFoundException) {
			return "error/404";
		}
		
		// 2. 로깅(Logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logger.error(errors.toString());

		// 3. 사과 페이지
		model.addAttribute("errors", errors.toString());
		return "error/exception";
	}
}
