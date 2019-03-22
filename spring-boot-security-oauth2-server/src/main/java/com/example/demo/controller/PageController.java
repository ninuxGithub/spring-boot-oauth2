package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

	private static final Logger logger = LoggerFactory.getLogger(PageController.class);

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		logger.info("获得权限访问首页： enter index controller");
		return "index";
	}

	@RequestMapping(value = { "/auth/login" }, method = RequestMethod.GET)
	public String login() {
		return "login";
	}
}
