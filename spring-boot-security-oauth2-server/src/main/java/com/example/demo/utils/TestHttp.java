package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;

public class TestHttp {

	public static void main(String[] args) {
		Map<String, String> params = new HashMap<>();
		params.put("wd", "java");
		String sendPost = HttpClientUtil.sendPost("https://www.baidu.com", params);
		System.err.println(sendPost);
	}

}
