package com.example.demo.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticatoinProvider extends AbstractUserDetailsAuthenticationProvider{

    /**
     * The Logger for this class.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * A Spring Security UserDetailsService implementation based upon the
     * Account entity model.
     */
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * A PasswordEncoder instance to hash clear test password values.
     */
    
    @Autowired
    private Md5PasswordEncoder passwordEncoder;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken token) throws AuthenticationException {
        logger.info("==>additionalAuthenticationChecks");

        if (token.getCredentials() == null || userDetails.getPassword() == null) {
            throw new BadCredentialsException("Credentials may not be null.");
        }
        
        String encoderPass = passwordEncoder.encodePassword((String) token.getCredentials(), null);
        logger.info("credential is {}", token.getCredentials());
        if(!encoderPass.equals(userDetails.getPassword())){
        	throw new BadCredentialsException("Invalid credentials.");
        }

//        if (!passwordEncoder.matches((String) token.getCredentials(), userDetails.getPassword())) {
//            throw new BadCredentialsException("Invalid credentials.");
//        }

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken token) throws AuthenticationException {
        logger.info("==>retrieveUser");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return userDetails;
    }



}




























