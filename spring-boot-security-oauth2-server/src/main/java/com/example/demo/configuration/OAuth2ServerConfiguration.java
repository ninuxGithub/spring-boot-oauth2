package com.example.demo.configuration;

import com.example.demo.utils.MD5PasswodEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configuration
public class OAuth2ServerConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2ServerConfiguration.class);


    private static final String RESOURCE_ID = "oauth2-resource";

    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }

    @Autowired
    private RedisConnectionFactory redisConnection;


    @Bean
    public TokenStore tokenStore() {
        logger.info("===>init tokenStore");
        return new RedisTokenStore(redisConnection);
    }

    @Configuration
    @EnableWebSecurity
    protected static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private CustomAuthenticatoinProvider authenticatoinProvider;

        @Autowired
        CustomUserDetailsService userDetailsService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
            auth.authenticationProvider(authenticatoinProvider);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/static/**");
            web.ignoring().antMatchers("/WEB-INF/**");
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/auth/login", "/auth/authorize", "/oauth/authorize","/oauth/check_token").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/auth/login").loginProcessingUrl("/auth/authorize")
                    .and()
                    .httpBasic();
        }
    }


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
            http.csrf().csrfTokenRepository(csrfTokenRepository());
            http.requestMatcher(new OAuth2RequestedMatcher()).authorizeRequests()
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/index/**", "/springOauth/**", "/oauth/**", "/login").permitAll()
                    .anyRequest().authenticated()
                    .and().csrf().disable().httpBasic();
        }

        private CsrfTokenRepository csrfTokenRepository() {
            HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
            repository.setSessionAttributeName("_csrf");
            return repository;
        }

        /**
         * 定义一个oauth2的请求匹配器
         */
        private static class OAuth2RequestedMatcher implements RequestMatcher {
            @Override
            public boolean matches(HttpServletRequest request) {
                String requestURI = request.getRequestURI();
                if (requestURI.equals("/index")) {
                    return true;
                }
                String auth = request.getHeader("Authorization");
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
            endpoints.tokenStore(tokenStore)
                    .authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService);
            // @formatter:on
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.jdbc(dataSource);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            /***
             * 自定义客户端的加密方式：MD5
             */
            security.allowFormAuthenticationForClients().passwordEncoder(MD5PasswodEncoder.getInstance());// 允许客户表单认证
            // 对于CheckEndpoint控制器[框架自带的校验]的/oauth/check端点允许所有客户端发送器请求而不会被Spring-security拦截
            //security.checkTokenAccess("permitAll()");

//            security.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
//                    .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
            security.tokenKeyAccess("permitAll()")
                    .checkTokenAccess("permitAll()"); //允许接口/oauth/check_token 被调用
        }

    }

}
