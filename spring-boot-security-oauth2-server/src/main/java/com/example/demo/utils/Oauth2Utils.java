package com.example.demo.utils;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author shenzm
 * @date 2019-3-25
 * @description 作用
 */
public class Oauth2Utils {


    /**
     * oauth2 认证服务器直接处理校验请求的逻辑
     * @param accessToken
     * @return
     */
    public static OAuth2AccessToken checkTokenInOauth2Server(String accessToken){
        TokenStore tokenStore = (TokenStore) ApplicationSupport.getBean("tokenStore");
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
        return oAuth2AccessToken;
    }

    /**
     * oauth2 认证服务器直接处理校验请求的逻辑
     * @param accessToken
     * @return
     */
    public static OAuth2Authentication getAuthenticationInOauth2Server(String accessToken){
        TokenStore tokenStore = (TokenStore) ApplicationSupport.getBean("tokenStore");
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
        return oAuth2Authentication;
    }
}
