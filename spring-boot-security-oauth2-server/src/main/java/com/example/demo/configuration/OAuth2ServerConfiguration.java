package com.example.demo.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.example.demo.utils.MD5PasswodEncoder;

@Configuration
public class OAuth2ServerConfiguration {

    private static final String RESOURCE_ID = "restservice";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

//        @Autowired
//        private JwtAccessTokenConverter jwtAccessTokenConverter;

        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            // @formatter:off
//            resources.resourceId(RESOURCE_ID).tokenStore(tokenStore);
        	resources.tokenStore(tokenStore);
            // @formatter:on
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/api/**").authenticated();
            // @formatter:on
        }

    }



    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private JwtAccessTokenConverter jwtAccessTokenConverter;

        @Autowired
        private TokenStore tokenStore;
        
        @Autowired
        private DataSource dataSource;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        CustomUserDetailsService userDetailsService;
        
        @Value("${useJwtTokenStore}")
        private boolean useJwtTokenStore= false;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            // @formatter:off
            endpoints
                    .tokenStore(tokenStore)
                    .authenticationManager(authenticationManager)
                    //.accessTokenConverter(jwtAccessTokenConverter)
                    .userDetailsService(userDetailsService);
            if(useJwtTokenStore) {
            	endpoints.accessTokenConverter(jwtAccessTokenConverter);
            }
            // @formatter:on
        }
        
        
        
        /**
         * 如何采用本地数据库的ClientDetails呢？
         * 
         * 
         * 
         * 目前是采用的内置的ClientDetails
         */
        
//        @Autowired
//        private CustomClientDetailsService customClientDetailsService;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // @formatter:off
        	
        	clients.jdbc(dataSource);//1
        	/**
        	 * 如果采用jdbc() 那么需要在数据库指定token的有效时间
        	 * 
        	 * 如果是inMemory() 采用属性来指定token的有效时间
        	 */
//        	customClientDetailsService.setPasswordEncoder(MD5PasswodEncoder.getInstance());
//        	clients.withClientDetails(customClientDetailsService);//2
//            clients
//                    .inMemory()
//                    .withClient("clientapp")
//                    .authorizedGrantTypes("password","refresh_token","client_credentials")
//                    .authorities("USER")
//                    .scopes("read", "write")
//                    .resourceIds(RESOURCE_ID)
//                    .secret("123456")
//                    .accessTokenValiditySeconds(1800)
//                    .refreshTokenValiditySeconds(600);//3
            
            // @formatter:on
        }



		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			security.allowFormAuthenticationForClients();//允许客户表单认证
//			security.passwordEncoder(new BCryptPasswordEncoder());//设置oauth_client_details中的密码编码器
			security.checkTokenAccess("permitAll()");//对于CheckEndpoint控制器[框架自带的校验]的/oauth/check端点允许所有客户端发送器请求而不会被Spring-security拦截
		}
        
        

    }

}
