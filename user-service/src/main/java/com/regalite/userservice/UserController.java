package com.regalite.userservice;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    @GetMapping
    public String load(){
        return "User Service is loading !!!!!!!!!!!";
    }
}
