package com.regalite.provinceservice;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FISES-HoangVH15
 */
@RestController
@CrossOrigin("/*")
@RequestMapping("/api/province")
public class ProvinceController {
    @GetMapping
    public String load(){
        return "Province Service is loading !!!!!!!!!!!";
    }
}
