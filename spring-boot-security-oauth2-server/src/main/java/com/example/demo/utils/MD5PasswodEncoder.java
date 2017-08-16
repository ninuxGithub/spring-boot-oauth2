package com.example.demo.utils;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class MD5PasswodEncoder implements PasswordEncoder {

	public String encode(CharSequence rawPassword) {
		return new Md5PasswordEncoder().encodePassword(rawPassword.toString(), null);
	}

	public boolean matches(CharSequence rawPassword, String encdedPassword) {
		return new Md5PasswordEncoder().encodePassword(rawPassword.toString(), null).equals(encdedPassword);
	}

	/**
	 * Get the singleton {@link NoOpPasswordEncoder}.
	 */
	public static PasswordEncoder getInstance() {
		return INSTANCE;
	}

	private static final PasswordEncoder INSTANCE = new MD5PasswodEncoder();

	private MD5PasswodEncoder() {
	}
	

}
