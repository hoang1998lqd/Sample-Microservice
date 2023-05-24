package com.regalite.userservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author FISES-HoangVH15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String accountName;
    private String displayName;
    private String email;
    private String phoneNumber;
    private String password;
    private String avatar;
    private Date birthDay;
    private Byte gender;
}
