package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class PrivateResourceController {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * hasRole 好像不管用？？？
	 * @return
	 */
	@PreAuthorize(value="hasRole('admin')")
	@RequestMapping(value="/api/users/", method = RequestMethod.GET)
	@ResponseBody
	public List<User> findAllUsers(){
		return userRepository.findAll();
	}

}
