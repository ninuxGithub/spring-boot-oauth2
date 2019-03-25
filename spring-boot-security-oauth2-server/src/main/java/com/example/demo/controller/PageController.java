package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
//@SessionAttributes("authorizationRequest")
public class PageController {

	private static final Logger logger = LoggerFactory.getLogger(PageController.class);

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		logger.info("获得权限访问首页： enter index controller");
		return "index";
	}

	/*@RequestMapping(value = { "/auth/login" }, method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping("/oauth/confirm_access")
	public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
		AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
		ModelAndView view = new ModelAndView();
		view.setViewName("grant");
		view.addObject("clientId", authorizationRequest.getClientId());
		return view;
	}*/
}
