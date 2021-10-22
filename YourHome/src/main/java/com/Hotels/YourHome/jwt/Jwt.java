package com.Hotels.YourHome.jwt;

import com.Hotels.YourHome.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class Jwt {

    @Value("${app.secret}")
    private String secret;

    //Creating token
    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("Your Home")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
                .signWith(SignatureAlgorithm.HS256,secret.getBytes())
                .compact();
    }

    public String generate(String subject, UserDetails user)
    {
        return Jwts.builder()
                .setSubject(subject)
                .claim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuer("Your Home")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1)))
                .signWith(SignatureAlgorithm.HS256,secret.getBytes())
                .compact();

    }

    //Reading token
    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    //get Expiry Date
    public Date getExpDate(String token){
        return getClaims(token).getExpiration();
    }

    //Is Token Valid
    public boolean isexpired(String token){
        Date expDate = getExpDate(token);
        return expDate.before(new Date(System.currentTimeMillis()));
    }

    //check
    public boolean isValid(String token, String Username)
    {
        String user = getClaims(token).getSubject();
        return (user.equals(Username) && !isexpired(token));
    }
}
