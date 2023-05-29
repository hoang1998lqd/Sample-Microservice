package com.regalite.gatewayservice.util;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SignatureException;


@Component
public class JwtUtil {


    public static final String SECRET = "04ca023b39512e46d0c2cf4b48d5aac61d34302994c87ed4eff225dcf3b0a218739f3897051a057f9b846a69ea2927a587044164b7bae5e1306219d50b588cb1";


//    public void validateToken(final String token) {
//        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
//    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token);
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }


}
