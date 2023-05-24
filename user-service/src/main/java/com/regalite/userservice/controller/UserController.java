package com.regalite.userservice.controller;

import com.regalite.userservice.service.client.ProvinceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private ProvinceClient provinceClient;
    @GetMapping
    public String load(){
        return "Get data from Province Service to User Service" + provinceClient.getValue();
    }
}
