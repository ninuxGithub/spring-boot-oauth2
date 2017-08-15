package com.example.demo.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;


@Service
public class UserDetailService implements UserDetailsService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailService.class);
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(StringUtils.isBlank(username) || StringUtils.isEmpty(username)) {
			logger.error("username is empty {} ", username);
		}
		User repositoryUser = userRepository.findUserByUsername(username);
		
		Collection<SimpleGrantedAuthority> collection = new HashSet<>();
		for(Iterator<Role> iterator = repositoryUser.getRoles().iterator(); iterator.hasNext();) {
			Role next = iterator.next();
			collection.add(new SimpleGrantedAuthority(next.getRoleName()));
		}
		
		return new org.springframework.security.core.userdetails.User(username, repositoryUser.getPassword(), collection);
	}

}
