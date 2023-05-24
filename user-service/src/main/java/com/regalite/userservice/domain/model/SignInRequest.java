package com.regalite.userservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FISES-HoangVH15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    private String login;

    private String password;
}
