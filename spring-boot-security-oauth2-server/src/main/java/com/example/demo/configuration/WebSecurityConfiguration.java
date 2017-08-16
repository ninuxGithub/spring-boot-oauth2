package com.example.demo.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);
	
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticatoinProvider accountAuthenticationProvider;
    
    @Value("${useJwtTokenStore}")
    private boolean useJwtTokenStore= false; 

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(accountAuthenticationProvider);
    }

    /**
     * 用户采用md5加密用户的密码
     * @return
     */
    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }
    
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("ASDFASFsdfsdfsdfsfadsf234asdfasfdas");
        return jwtAccessTokenConverter;
    }

//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }
    
    
    //目前不知道有什么区别
    @Autowired
	private RedisConnectionFactory redisConnection;
    
    
    @Bean
	public TokenStore tokenStore() {
    	logger.info("WebSecurityConfiguration 是否采用  useJwtTokenStore: {}", useJwtTokenStore);
    	if(useJwtTokenStore) {
    		return new JwtTokenStore(jwtAccessTokenConverter());
    	}else {
    		return new RedisTokenStore(redisConnection);
    	}
	}

}
