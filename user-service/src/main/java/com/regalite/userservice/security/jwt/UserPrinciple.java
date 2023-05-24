package com.regalite.userservice.security.jwt;

import com.regalite.userservice.domain.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author FISES-HoangVH15
 */
@Data
public class UserPrinciple implements OAuth2User, UserDetails {
    private Long id;
    private String email;
    private String password;
    private String accountName;
    private String displayName;
    private String phoneNumber;
    private String avatar;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrinciple(Long id, String email, String password, String accountName, String displayName
            , String phoneNumber, String avatar, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.accountName = accountName;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.authorities = authorities;
    }
    public static UserPrinciple create(User user) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserPrinciple(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getAccountName(),
                user.getDisplayName(),
                user.getPhoneNumber(),
                user.getAvatar(),
                authorities
        );
    }
    public static UserPrinciple create(User user, Map<String, Object> attributes) {
        UserPrinciple userPrincipal = UserPrinciple.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
