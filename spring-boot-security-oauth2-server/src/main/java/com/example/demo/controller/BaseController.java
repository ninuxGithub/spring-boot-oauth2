package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

	/**
	 * 授权链接
	 */
	@Value("${spring-oauth-authorize}")
	protected String springOauthAuthorize;

	/**
	 * 获取token链接
	 */
	@Value("${spring-oauth-token}")
	protected String springOauthToken;

}
