package com.regalite.userservice.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author FISES-HoangVH15
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
