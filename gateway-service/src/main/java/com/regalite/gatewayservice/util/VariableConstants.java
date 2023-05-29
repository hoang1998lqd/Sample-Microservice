package com.regalite.gatewayservice.util;

/**
 * @author FISES-HoangVH15
 */
public interface VariableConstants {
    //OAuth2 and JWT
    String AUTHORIZATION_BASE_URL = "/oauth2/authorize";
    String CALLBACK_BASE_URL = "/oauth2/callback/*";
    String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    String TOKEN_COOKIE_NAME = "token";

    // Cookie
    int COOKIE_EXPIRE_SECONDS = 30 * 60;
    String PROTOCOL_HTTP = "http";
    String PROTOCOL_HTTPS = "https";
}
