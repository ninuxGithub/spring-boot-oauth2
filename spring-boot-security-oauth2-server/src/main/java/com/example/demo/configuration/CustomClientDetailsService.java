package com.example.demo.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

//@Service
public class CustomClientDetailsService extends JdbcClientDetailsService {
	
	public CustomClientDetailsService(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = LoggerFactory.getLogger(CustomClientDetailsService.class);
	
//	@Autowired
//	@Qualifier(value="dataSource")
//	private DataSource dataSource;
//	
//	
//	
//	@ConstructorProperties(value= {"dataSource"})
//	public CustomClientDetailsService(DataSource dataSource) {
//		super(dataSource);
//		logger.info("init CustomClientDetailsService...");
//	}

	
//	@Autowired
//	private ClientRepository clientRepository;
//
//	@Override
//	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
//		if(StringUtils.isBlank(clientId)) {
//			logger.error("请求的clientId不合法：{}", clientId);
//		}
//		
//		ClientDetails client = clientRepository.loadClientByClientId(clientId);
//		
//		return null;
//	}

}
