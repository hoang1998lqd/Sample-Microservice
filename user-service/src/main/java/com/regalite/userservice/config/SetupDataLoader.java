package com.regalite.userservice.config;


import com.regalite.userservice.domain.entity.Role;
import com.regalite.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;


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
        alreadySetup = true;
    }


    @Transactional
    public void createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = roleRepository.save(new Role(name));
        }
        else {
            System.out.println("Success");
        }
    }
}