package com.bohdan.films_webapp.controllers;

import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.jwt.AuthRequestDto;
import com.bohdan.films_webapp.jwt.JwtTokenProvider;
import com.bohdan.films_webapp.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class JwtController {
//    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtController(/*AuthenticationManager authenticationManager,*/
                         UserRepository userRepository,
                         JwtTokenProvider jwtTokenProvider) {
//        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDto requestDto){
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword())
//            );
//            User user = userRepository.findUserByEmail(requestDto.getEmail())
//                    .orElseThrow(
//                            () -> new UsernameNotFoundException("User with email = " + requestDto.getEmail() + " wasn't found")
//                    );
//            String token = jwtTokenProvider.createToken(requestDto.getEmail(), user.getRole().name());
//            Map<Object, Object> response = new HashMap<>();
//            response.put("email", requestDto.getEmail());
//            response.put("token", token);
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (AuthenticationException e){
//            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
//        }
//    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
