package com.sqada.task_management_system_backend.controller.auth;

import com.sqada.task_management_system_backend.dto.LoginDTO;
import com.sqada.task_management_system_backend.dto.LoginResponse;
import com.sqada.task_management_system_backend.dto.RegisterDTO;
import com.sqada.task_management_system_backend.dto.UserDTO;
import com.sqada.task_management_system_backend.entities.User;
import com.sqada.task_management_system_backend.repositories.UserRepository;
import com.sqada.task_management_system_backend.services.auth.IAuthService;
import com.sqada.task_management_system_backend.services.jwt.UserService;
import com.sqada.task_management_system_backend.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final IAuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(IAuthService authService, UserRepository userRepository, JwtUtil jwtUtil, UserService userService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
        if(authService.hasUserWithEmail(registerDTO.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists with this email");
        UserDTO createdUserDTO = authService.registerUser(registerDTO);
        if(createdUserDTO == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginDTO loginDTO){
       try{
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
       } catch(BadCredentialsException e){
           throw new BadCredentialsException("Incorrect username or password");
       }
       final UserDetails userDetails = userService.userDetailService().loadUserByUsername(loginDTO.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(loginDTO.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        LoginResponse loginResponse = new LoginResponse();
        if(optionalUser.isPresent()){
            loginResponse.setJwt(jwtToken);
            loginResponse.setUserId(optionalUser.get().getId());
            loginResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return loginResponse;
    }
}
