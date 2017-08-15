package com.example.demo.security;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

//@Component
public class OauthProvider implements AuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(OauthProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("自定义的authenticationProvider");
		return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), Collections.<GrantedAuthority>emptyList());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
