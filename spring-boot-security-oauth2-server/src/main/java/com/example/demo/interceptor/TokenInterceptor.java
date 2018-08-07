package com.example.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TokenInterceptor implements HandlerInterceptor{
	private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("---------------------开始进入请求地址拦截----------------------------");  
		System.out.println("preHandle "+request.getSession().getAttribute("passwordAuth"));
		/*Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			System.err.println(headerName+":" +request.getHeader(headerName));
		}*/
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
