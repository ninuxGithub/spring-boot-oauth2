package com.example.demo.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomClientDetailsService extends JdbcClientDetailsService {
	

	private static final Logger logger = LoggerFactory.getLogger(CustomClientDetailsService.class);
	
	@Autowired
	public CustomClientDetailsService(DataSource dataSource) {
		super(dataSource);
//		super.setPasswordEncoder(new BCryptPasswordEncoder());
		logger.info("开始配置自定义 init CustomClientDetailsService...");
		logger.info(dataSource==null?"dataSource is null":"config success..");
	}


}
