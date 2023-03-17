//package com.example.demo.jwt;
//
//import com.example.demo.security.UserDetail;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//@Slf4j
//public class JwtTokenProvider {
//    @Value("${demo.jwt.secret}")
//    private String JWT_secret;
//    @Value("${demo.jwt.expiration}")
//    private int JWT_expiration;
//    public String generateToken(UserDetail userDetail){
//        Date now = new Date();
//        Date expiration = new Date(now.getTime() + JWT_expiration);
//        return Jwts.builder().setSubject(userDetail.getUsername()).setIssuedAt(now).setExpiration(expiration).signWith(SignatureAlgorithm.HS512,JWT_secret).compact();
//    }
//    public String getUserNameFromToken(String token){
//        Claims claims = Jwts.parser().setSigningKey(JWT_secret).parseClaimsJws(token).getBody();
//        return claims.getSubject();
//    }
//
//    public boolean validateToken(String authToken){
//        try{
//            Jwts.parser().setSigningKey(JWT_secret).parseClaimsJws(authToken);
//            return true;
//        }catch (Exception exception){
//            return false;
//        }
//    }
//}
