package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		System.out.println("enter index controller");
		return "index";
	}
}
