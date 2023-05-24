package com.regalite.userservice.controller;

import com.regalite.userservice.client.ProvinceClient;
import com.regalite.userservice.domain.entity.Role;
import com.regalite.userservice.domain.entity.User;
import com.regalite.userservice.domain.enums.SocialProvider;
import com.regalite.userservice.domain.model.SignInRequest;
import com.regalite.userservice.repository.RoleRepository;
import com.regalite.userservice.repository.UserRepository;
import com.regalite.userservice.security.jwt.TokenProvider;
import com.regalite.userservice.security.jwt.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;
    //    @Autowired
//    private ProvinceClient provinceClient;
//    @GetMapping
//    public String load(){
//        return "Get data from Province Service to User Service" + provinceClient.getValue();
//    }
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @GetMapping
    public String publicData() {
        return "Dữ liệu được public";
    }

    @GetMapping("/private")
    public String PrivateData() {
        return "Dữ liệu được Private";
    }

    @PostMapping("/authen")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getLogin(), signInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> authenticateUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Role.ROLE_ADMIN));
       User user = userRepository.save(User.builder()
                .accountName("hoang1998")
                .displayName("Regalite")
                .phoneNumber("0344550559")
                .email("hoang1998lqd2@gmail.com")
                .password(passwordEncoder.encode("hoang123"))
                .avatar("https://noithatbinhminh.com.vn/wp-content/uploads/2022/08/anh-dep-40.jpg")
                .provider(SocialProvider.LOCAL.getProviderType())
                .emailVerified((byte) 1)
                .validFlg((byte) 1)
                .createdAt(LocalDateTime.now())
                .birthDay(new Date(1998, 12, 15))
                .gender((byte) 1)
                .bio("Road to Success !!!")
                .roles(roles)
                .build()
        );
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


}
