package com.regalite.userservice.config;


import com.regalite.userservice.domain.entity.Role;
import com.regalite.userservice.domain.entity.User;
import com.regalite.userservice.domain.enums.SocialProvider;
import com.regalite.userservice.repository.RoleRepository;
import com.regalite.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        // Create initial roles
        createRoleIfNotFound(Role.ROLE_USER);
        createRoleIfNotFound(Role.ROLE_ADMIN);
        createRoleIfNotFound(Role.ROLE_MODERATOR);
        createUserIfNotFound("hoang1998lqd2333@gmail.com");
        alreadySetup = true;
    }
    @Transactional
    public User createUserIfNotFound(String email){
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Role.ROLE_ADMIN));
        if (optionalUser.isEmpty()){
            return userRepository.save(User.builder()
                            .accountName("hoang1998lqd")
                            .displayName("Regalite")
                            .phoneNumber("0344550558")
                            .email("hoang1998lqd2333@gmail.com")
                            .password(passwordEncoder.encode("hoang123"))
                            .avatar("https://noithatbinhminh.com.vn/wp-content/uploads/2022/08/anh-dep-40.jpg")
                            .provider(SocialProvider.LOCAL.getProviderType())
                            .emailVerified((byte)1)
                            .validFlg((byte)1)
                            .createdAt(LocalDateTime.now())
                            .birthDay(new Date(1998,12,15))
                            .gender((byte)1)
                            .bio("Road to Success !!!")
                            .roles(roles)
                    .build()
            );
        }
        return null;
    }
    @Transactional
    public void createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            roleRepository.save(new Role(name));
        }
    }
}