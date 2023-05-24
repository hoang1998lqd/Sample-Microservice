package com.regalite.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "province-service")
public interface ProvinceClient {
    @GetMapping("/api/province")
     String getValue();
}
