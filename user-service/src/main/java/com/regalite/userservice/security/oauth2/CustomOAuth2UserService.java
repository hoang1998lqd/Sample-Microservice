package com.regalite.userservice.security.oauth2;


import com.regalite.userservice.domain.entity.Role;
import com.regalite.userservice.domain.entity.User;
import com.regalite.userservice.domain.enums.SocialProvider;
import com.regalite.userservice.exception.OAuth2AuthenticationProcessingException;
import com.regalite.userservice.repository.RoleRepository;
import com.regalite.userservice.repository.UserRepository;
import com.regalite.userservice.security.jwt.UserPrinciple;
import com.regalite.userservice.security.oauth2.model.OAuth2UserInfo;
import com.regalite.userservice.security.oauth2.model.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author FISES-HoangVH15
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findUserByEmail(oAuth2UserInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
//            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
            if (!user.getProvider().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrinciple.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Role.ROLE_USER));
//        user.setProvider(String.valueOf(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId())));
        return userRepository.save(User.builder()
                .accountName(oAuth2UserInfo.getEmail())
                .displayName(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .avatar(oAuth2UserInfo.getImageUrl())
                .provider(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase())
                .createdAt(LocalDateTime.now())
                .validFlg((byte) 1)
                .validFlg((byte) 1)
                .roles(roles)
                .build()
        );
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setDisplayName(oAuth2UserInfo.getName());
        existingUser.setAvatar(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}
