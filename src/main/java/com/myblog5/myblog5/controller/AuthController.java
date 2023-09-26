package com.myblog5.myblog5.controller;


import com.myblog5.myblog5.entity.User;
import com.myblog5.myblog5.payload.LoginDto;
import com.myblog5.myblog5.payload.SignUpDto;
import com.myblog5.myblog5.repository.RoleRepository;
import com.myblog5.myblog5.repository.UserRepository;
import com.myblog5.myblog5.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    //http://localhost:8080/api/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    //http://localhost:8080/api/auth/signup

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        Boolean emailExist = userRepo.existsByEmail(signUpDto.getEmail());
        if(emailExist){
            return new ResponseEntity<>("Email Id exist is registered", HttpStatus.BAD_GATEWAY);
        }
        Boolean emailUserName = userRepo.existsByUsername(signUpDto.getUsername());
        if(emailUserName){
            return new ResponseEntity<>("Username exists", HttpStatus.BAD_GATEWAY);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        userRepo.save(user);
        return new ResponseEntity<>("User is registered", HttpStatus.CREATED);


    }
}
