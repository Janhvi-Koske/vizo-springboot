package com.vizo.vizo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserServiceDetails customUserDetails;

    // ------------------ SIGNUP ------------------
    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) {

        // Check if email already exists
        User isExist = userRepository.findByEmail(user.getEmail());
        if (isExist != null) {
            throw new RuntimeException("This email is already used with another account");
        }

        // Create new user and encode password
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(newUser);

        // Create authentication object
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(),
                savedUser.getPassword()
        );

        // Generate JWT token
        String token = JwtProvider.generateToken(authentication);

        return new AuthResponse(token, "Register Success");
    }

    // ------------------ SIGNIN ------------------
    @PostMapping("/signin")
    public AuthResponse signin(@RequestBody LoginRequest loginRequest) {

        // Load user details by email
        UserDetails userDetails = customUserDetails.loadUserByUsername(loginRequest.getEmail());
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }

        // Verify password
        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // Create authentication token
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        // Generate JWT token
        String token = JwtProvider.generateToken(authentication);

        return new AuthResponse(token, "Login Success");
    }
}
