package com.example.demo.configuration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.entity.Role;
import com.example.demo.repository.UserRepository;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	// 查询用户使用
	@Autowired
	UserRepository userRepository;
	
	
	
	@Autowired
    private CustomAuthenticatoinProvider authenticationProvider;

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
		//注入authenticationProvider 可以保证密码的方式登录授权成，但是问题是授权码的方式失败了
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().authenticated().and()
				.httpBasic().and().csrf().disable();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
				// 通过用户名获取用户信息
				com.example.demo.entity.User customUser = userRepository.findUserByUsername(name);
				if (customUser != null) {
					// 创建spring security安全用户
					Collection<SimpleGrantedAuthority> collection = new HashSet<>();
					for (Iterator<Role> iterator = customUser.getRoles().iterator(); iterator.hasNext();) {
						Role next = iterator.next();
						collection.add(new SimpleGrantedAuthority(next.getRoleName()));
					}
					User user = new User(customUser.getUsername(), customUser.getPassword(), collection);
					return user;
				} else {
					throw new UsernameNotFoundException("用户[" + name + "]不存在");
				}
			}
		};

	}
}
