package com.example.demo.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("authorizationRequest")
public class ErrorController {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
	
	@RequestMapping("/oauth/error")
	public String error(@RequestParam Map<String,String> parameters) {
		String uri = parameters.get("redirect_uri");
		logger.info("重定向到：{}", uri);
		if(StringUtils.isBlank(uri)){
			logger.info("跳转的Uri 为空...");
			return "redirect:/";
		}
		return "redirect:"+ uri+"?error=1";
	}
}
