package com.example.demo.security.jwt;

import com.example.demo.security.userprincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.net.Authenticator;
import java.util.Date;


@Component
public class JwtProvider {

    //ghi log de xem token co hieu luc k?
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    private String jwtSecret = "anh94701@gmail.com";
    private int jwtExpiration = 86400; //thoi gian song cua no tren he thong _ 1 ngay

    //tao token
    public String createToken(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal(); //lay user hien tai tren he thong
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date()) //set tai thoi diem hien tai
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration * 1000)) //set time song
                .signWith(SignatureAlgorithm.HS512, jwtSecret) //dang ky tieu chuan ma hoa HS512, va truyen key secret vao
                .compact(); //dong lai
    }

    //check xem token co hop le hay k?
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }

    //khi co token, tim username o trong token nay
    public String getUserNameFromJwtToken(String token) {

        String userName = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject(); //lay ra username
        return userName;
    }
}