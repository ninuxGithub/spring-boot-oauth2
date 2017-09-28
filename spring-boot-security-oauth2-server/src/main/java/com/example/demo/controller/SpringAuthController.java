package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.utils.HttpClientUtil;

@Controller
@RequestMapping("/springOauth")
public class SpringAuthController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpringAuthController.class);

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "index";
	}

	@RequestMapping(value = { "authorizationCode" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView authorizationCode(ModelAndView mv) {
		System.err.println("#######");
		mv.addObject("oauthUrl", springOauthAuthorize);
		mv.setViewName("authorizationCode");
		return mv;
	}

	@RequestMapping(value = "/authorizationCodePage", method = { RequestMethod.GET, RequestMethod.POST })
	public String authorizationCodePage() {
		return "authorizationCodePage";
	}
	@RequestMapping(value = "/passwordOauth", method = { RequestMethod.GET, RequestMethod.POST })
	public String passwordOauth() {
		return "passwordOauth";
	}
	@RequestMapping(value = "/clientOauth", method = { RequestMethod.GET, RequestMethod.POST })
	public String clientOauth() {
		return "clientOauth";
	}
	
	@RequestMapping(value = { "implicitOauth" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView implicitOauth(ModelAndView mv) {
		mv.addObject("oauthUrl", springOauthAuthorize);
		mv.setViewName("implicitOauth");
		return mv;
	}

	
	
	/**
	 * 不可以采用中中方式， 会导致匿名用户的错误
	 * @param clientId
	 * @param clientSecret
	 * @param redirectUri
	 * @param grantType
	 * @param code
	 * @param csrf
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/authToken" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String authorizationCodeForm(@RequestParam("client_id") String clientId,
			@RequestParam("client_secret") String clientSecret, @RequestParam("redirect_uri") String redirectUri,
			@RequestParam("grant_type") String grantType,
			@RequestParam("code") String code, @RequestParam("_csrf") String csrf, HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> params = new HashMap<>();
		params.put("client_id", clientId);
		params.put("client_secret", clientSecret);
		params.put("grant_type", grantType);
		params.put("redirect_uri", redirectUri);
		params.put("code", code);
		
		
		
		try {
			String postData = HttpClientUtil.connectPostHttps(springOauthToken, params);
			if (StringUtils.isNotBlank(postData)) {
				logger.info(postData);
				return postData;
			}

			System.out.println("postData:" + postData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "authorizationCodePage";
	}
}
