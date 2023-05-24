package com.regalite.userservice.domain.model;

/**
 * @author Chinna
 * @since 26/3/18
 */
public enum SocialProvider {

	FACEBOOK("facebook"), TWITTER("twitter"), LINKEDIN("linkedin"), GOOGLE("google"), GITHUB("github"), LOCAL("local");

	private String providerType;

	public String getProviderType() {
		return providerType;
	}

	SocialProvider(final String providerType) {
		this.providerType = providerType;
	}
	public static final String AUTHORIZATION_BASE_URL = "/oauth2/authorization";

	public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";

	public static final String CALLBACK_BASE_URL = "/oauth2/callback/*";

}
