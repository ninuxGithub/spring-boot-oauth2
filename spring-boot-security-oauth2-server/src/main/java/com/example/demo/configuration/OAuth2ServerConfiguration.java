package com.example.demo.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.example.demo.utils.MD5PasswodEncoder;

@Configuration
public class OAuth2ServerConfiguration {

	/**
	 * 默认的resources_id
	 */
	private static final String RESOURCE_ID = "oauth2-resource";

	@Configuration
	@EnableResourceServer
	@EnableGlobalMethodSecurity(prePostEnabled = false)
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Autowired
		private TokenStore tokenStore;

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			// @formatter:off
			resources.resourceId(RESOURCE_ID).tokenStore(tokenStore);
			// @formatter:on
		}


		@Override
		public void configure(HttpSecurity http) throws Exception {
			
			//===================================================方法二
			http.csrf().csrfTokenRepository(csrfTokenRepository());
			http.authorizeRequests()
			.antMatchers("/springOauth/**","/oauth/**").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable().httpBasic();
			
			//===================================================方法三
//			http.csrf().csrfTokenRepository(csrfTokenRepository());
//			http.requestMatcher(new OAuth2RequestedMatcher()).authorizeRequests()
//			.antMatchers("/api/**").authenticated()
//			.antMatchers("/springOauth/**","/oauth/**","/login").permitAll()
//			.anyRequest().authenticated()
//			.and().csrf().disable().httpBasic();
		}

		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setSessionAttributeName("_csrf");
			return repository;
		}

		/**
		 * 定义一个oauth2的请求匹配器
		 */
		@SuppressWarnings("unused")
		private static class OAuth2RequestedMatcher implements RequestMatcher {
			@Override
			public boolean matches(HttpServletRequest request) {
				String auth = request.getHeader("Authorization");
				// 判断来源请求是否包含oauth2授权信息,这里授权信息来源可能是头部的Authorization值以Bearer开头
				// 或者是请求参数中包含access_token参数,满足其中一个则匹配成功
				boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
				boolean haveAccessToken = request.getParameter("access_token") != null;
				return haveOauth2Token || haveAccessToken;
			}
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
		private boolean useJwtTokenStore = false;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			// @formatter:off
			endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager)
					.userDetailsService(userDetailsService);
			if (useJwtTokenStore) {
				endpoints.accessTokenConverter(jwtAccessTokenConverter);
			}
			// @formatter:on
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			// @formatter:off

			clients.jdbc(dataSource);// 1
			/**
			 * 如果采用jdbc() 那么需要在数据库指定token的有效时间
			 * 
			 * 如果是inMemory() 采用属性来指定token的有效时间
			 */
			// customClientDetailsService.setPasswordEncoder(MD5PasswodEncoder.getInstance());
			// clients.withClientDetails(customClientDetailsService);//2
			// clients
			// .inMemory()
			// .withClient("clientapp")
			// .authorizedGrantTypes("password","refresh_token","client_credentials")
			// .authorities("USER")
			// .scopes("read", "write")
			// .resourceIds(RESOURCE_ID)
			// .secret("123456")
			// .accessTokenValiditySeconds(1800)
			// .refreshTokenValiditySeconds(600);//3

			// @formatter:on
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			/***
			 * 自定义客户端的加密方式：MD5
			 */
			security.allowFormAuthenticationForClients().passwordEncoder(MD5PasswodEncoder.getInstance());// 允许客户表单认证
			security.checkTokenAccess("permitAll()");// 对于CheckEndpoint控制器[框架自带的校验]的/oauth/check端点允许所有客户端发送器请求而不会被Spring-security拦截
		}

	}

}
