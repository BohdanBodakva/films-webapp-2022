package com.bohdan.films_webapp.authentication;

import com.bohdan.films_webapp.DAO.User;
import com.bohdan.films_webapp.DAO.enums.Role;
import com.bohdan.films_webapp.DAO.enums.UserStatus;
import com.bohdan.films_webapp.jwt.JwtService;
import com.bohdan.films_webapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        User user = new User();

        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

}
