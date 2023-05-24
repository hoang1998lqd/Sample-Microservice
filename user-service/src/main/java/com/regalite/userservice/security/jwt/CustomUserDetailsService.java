package com.regalite.userservice.security.jwt;

import com.regalite.userservice.config.AppProperties;
import com.regalite.userservice.domain.entity.User;
import com.regalite.userservice.exception.ResourceNotFoundException;
import com.regalite.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author FISES-HoangVH15
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppProperties appProperties;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser;
        if (isEmail(username)) {
            optionalUser = userRepository.findUserByEmail(username);
        } else if (isPhoneNumber(username)) {
            optionalUser = userRepository.findUserByPhoneNumber(username);
        } else {
            optionalUser = userRepository.findUserByAccountName(username);
        }
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return UserPrinciple.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrinciple.create(user);
    }

    private boolean isEmail(String email) {
        String regexEmail = appProperties.getRegex().getRegexEmail();
        return email.matches(regexEmail);
    }

    private boolean isPhoneNumber(String email) {
        String regexPhone = appProperties.getRegex().getRegexPhone();
        return email.matches(regexPhone);
    }
}
