package com.act.school_xx.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {



    private  AuthenticationService authenticationService;
//    private final AuthenticationService authenticationService;


    private static final String SECRET_KEY ="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60* 24 * 5; // 5 days :/ TODO
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days



    // Method to extract username from token
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Method to extract claims from token
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Method to generate refresh token
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Method to check if refresh token is valid
    public boolean isRefreshTokenValid(String refreshToken, UserDetails userDetails) {
        final String username = extractUsername(refreshToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(refreshToken);
    }

    // Method to generate access token
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    // Method to generate access token with extra claims
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Method to check if access token is valid
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Method to parse and extract claims from token
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // Handle expired token here
            System.out.println("Error with extracting all Claims");
            throw e; // Rethrow the exception if you want to propagate it to the calling method
        }
    }

    // Method to check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Method to extract expiration date from token
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    // Method to get signing key
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Method to retrieve UserDetails from refresh token
    public UserDetails getUserDetailsFromToken(String refreshToken) {
        String email;
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();
            email = claims.getSubject();
        } catch (ExpiredJwtException e) {
            // Handle expired token here
            System.out.println("Refresh token has expired.");
            return null;
        } catch (Exception e) {
            // Handle any other exception while parsing the token
            System.out.println("Error parsing refresh token: " + e.getMessage());
            return null;
        }

        // Ensure username is not null
        if (email == null) {
            System.out.println("======Username extracted from refresh token is null.=======");
            return null;
        }

        // Retrieve UserDetails object from user service
        try {
            return authenticationService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            // Handle user not found exception
            System.out.println("User not found for username: " + email);
            return null;
        }
    }


//    // Method to retrieve UserDetails from refresh token
//    public UserDetails getUserDetailsFromToken(String refreshToken) {
//        String username;
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(getSignInKey())
//                    .build()
//                    .parseClaimsJws(refreshToken)
//                    .getBody();
//            username = claims.getSubject();
//        } catch (Exception e) {
//            // Handle any exception while parsing the token, such as expired token
//            return null;
//        }
//
//        // Here, you would typically retrieve the UserDetails object from your user service
//        // For demonstration purposes, let's assume you have a user service called userService
//        // that retrieves UserDetails by username
//        return authenticationService.loadUserByUsername(username);
//    }
}
