package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.utils.HttpClientUtil;
import com.xiaoleilu.hutool.http.HttpUtil;

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
	public ModelAndView passwordOauth(ModelAndView mv) {
		mv.addObject("key", "value");
		mv.setViewName("passwordOauth");
		return mv;
	}
//	@RequestMapping(value = "/passwordOauth", method = { RequestMethod.GET, RequestMethod.POST })
//	public String passwordOauth() {
//		return "passwordOauth";
//	}
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
			String postData = HttpClientUtil.sendPost(springOauthToken, params);
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
	
	/**
	 * 密码方式登录，如果成功 将登录信息保存到session
	 * @param clientId
	 * @param clientSecret
	 * @param username
	 * @param password
	 * @param grantType
	 * @param dataMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/passwordLogin" }, method = {RequestMethod.POST})
	public Map<String, Object> passwordLogin(@RequestParam("client_id") String clientId,
			@RequestParam("client_secret") String clientSecret,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("grant_type") String grantType,
			HttpSession session) {
		System.err.println("enter passwordLogin...");
		Map<String, Object> dataMap = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		params.put("client_id", clientId);
		params.put("client_secret", clientSecret);
		params.put("grant_type", grantType);
		params.put("username", username);
		params.put("password", password);
		boolean loginStatus = false;
		try {
			String postData = HttpUtil.post(springOauthToken, params);
			if (StringUtils.isNotBlank(postData)) {
				logger.info(postData);
				JSONObject jsonObject = JSONObject.parseObject(postData);
				if(jsonObject.containsKey("access_token")) {
					loginStatus = true;
					String access_token = jsonObject.getString("access_token");
					System.out.println(access_token);
					//如果判断包含access_token 那么就将返回信息保存到session
					session.setAttribute("passwordAuth", postData);
				}
			}
		} catch (Exception e) {
			logger.error("passwordLogin error happend :{}", e.getMessage());
		}
		
		dataMap.put("loginStatus", loginStatus);
		return dataMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/passwordAuth", method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String, String> loadSessionPasswordAuth( HttpSession session){
		Map<String,String> map = new HashMap<String, String>();
		map.put("access_token", null);
		String passwordAuth = (String) session.getAttribute("passwordAuth");
		if (StringUtils.isNotBlank(passwordAuth)) {
			JSONObject jsonObject = JSONObject.parseObject(passwordAuth);
			if (jsonObject.containsKey("access_token")) {
				String access_token = jsonObject.getString("access_token");
				String expires_in = jsonObject.getString("expires_in");
				String token_type = jsonObject.getString("token_type");
				if (Integer.valueOf(expires_in) > 0) {
					map.put("access_token", token_type +" "+access_token);
				}
			}
		}
		return map;
	}
}
