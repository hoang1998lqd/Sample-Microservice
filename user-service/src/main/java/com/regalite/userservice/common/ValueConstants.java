package com.regalite.userservice.common;

/**
 * @author FISES-HoangVH15
 */
public interface ValueConstants {
    String AUTHORIZATION_BASE_URL = "/oauth2/authorize";
    String CALLBACK_BASE_URL = "/oauth2/callback/*";
    String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    int COOKIE_EXPIRE_SECONDS = 180;
}
