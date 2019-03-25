package com.example.demo.controller;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author shenzm
 * @date 2019-3-25
 * @description 作用
 */

@Controller
@SessionAttributes("authorizationRequest")
public class AuthController {
    @RequestMapping(value = { "/auth/login" }, method = RequestMethod.GET)
	public String login() {
		return "login";
	}


	//http://localhost/oauth/authorize?response_type=token&client_id=password_auth_mode&redirect_uri=http://localhost/springOauth/authorizationCodePage
	@RequestMapping(value = "/oauth/confirm_access", method = RequestMethod.GET)
	public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
		AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
		ModelAndView view = new ModelAndView();
		view.setViewName("grant");
		view.addObject("clientId", authorizationRequest.getClientId());
		return view;
	}
}
