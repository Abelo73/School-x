package com.act.school_xx.services;

import com.act.school_xx.controllers.AuthenticationRequest;
import com.act.school_xx.dto.AuthenticationResponse;
import com.act.school_xx.dto.RegisterRequest;
import com.act.school_xx.exceptions.UserNotFoundException;
import com.act.school_xx.models.User;
import com.act.school_xx.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class AuthenticationService {



    private final UserRepository repository;


    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;




    public AuthenticationResponse register(RegisterRequest request) {

        // Set the default registration date to the current date
        LocalDateTime defaultRegistrationDate = LocalDateTime.now();

        // Check if the email already exists
        if (repository.existsByEmail(request.getEmail())) {
            // Email is already in use
            return AuthenticationResponse.builder()
                    .status("error")
                    .message("Email is already in use. Please choose a different email.")
                    .build();
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .age(request.getAge())
                .mobile(request.getMobile())
                .gender(request.getGender())
                .password(passwordEncoder.encode(request.getPassword()))
                .registrationDate(defaultRegistrationDate)
                .role(request.getRole())
                .build();

        // Save the user object to the database
        repository.save(user);

        // Generate access and refresh tokens after saving the user
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .status("success")
                .message("User registered successfully.")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Retrieve user from the database
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        // Generate access token
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);


        return AuthenticationResponse.builder()
                .token(jwtToken)
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .message("login successfully!")
                .status("success")
                .build();
    }

    public AuthenticationResponse refreshToken(String refreshToken) {

        try {

            UserDetails userDetails = jwtService.getUserDetailsFromToken(refreshToken);
            if (userDetails != null) {
                String newAccessToken = jwtService.generateToken(userDetails);
                return AuthenticationResponse.builder()
                        .token(newAccessToken)
                        .accessToken(newAccessToken)
                        .message("Access token refreshed successfully!")
                        .status("success")
                        .build();
            } else {
                // Handle invalid or expired refresh token
                return AuthenticationResponse.builder()
                        .status("error")
                        .message("Invalid or expired refresh token.")
                        .build();
            }
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            // Return an error response
            return AuthenticationResponse.builder()
                    .status("error")
                    .message("An error occurred while refreshing token.")
                    .build();
        }
    }

//    public AuthenticationResponse refreshToken(String refreshToken) {
//        UserDetails userDetails = jwtService.getUserDetailsFromToken(refreshToken);
//
//        if (userDetails != null) {
//            String newAccessToken = jwtService.generateToken(userDetails);
//            return AuthenticationResponse.builder()
//                    .token(newAccessToken)
//                    .message("Access token refreshed successfully!")
//                    .status("success")
//                    .build();
//        } else {
//            // Handle invalid or expired refresh token
//            return AuthenticationResponse.builder()
//                    .status("error")
//                    .message("Invalid or expired refresh token.")
//                    .build();
//        }
//    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

        try {
            // Retrieve user from the database based on the username
            User user = repository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

            // Return a UserDetails object representing the user
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(String.valueOf(user.getRole()))
                    .build();
        } catch (UsernameNotFoundException ex) {
            // Log the error message
            logger.error("User not found: {}", email);
            // Rethrow the exception to propagate it further
            throw ex;
        }
    }



    public void deleteUser(Long id) throws UserNotFoundException {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            repository.delete(userOptional.get());
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }


//        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//            // Retrieve user from the database based on the username
//            User user = repository.findByEmail(email)
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));
//
//            // Return a UserDetails object representing the user
//            return org.springframework.security.core.userdetails.User.builder()
//                    .username(user.getEmail())
//                    .password(user.getPassword())
//                    .roles(String.valueOf(user.getRole()))
//                    .build();
//        }


}
