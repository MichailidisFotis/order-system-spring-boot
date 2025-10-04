package com.example.order_system_spring_boot.components.authentication;


import com.example.order_system_spring_boot.components.users.User;
import com.example.order_system_spring_boot.helpers.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationRepository authenticationRepository;


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid Login loginBody , HttpServletRequest request) {


        return authenticationRepository.login(loginBody, request);

    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid User user) throws Exception {

        return authenticationRepository.register(user);
    }


    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request){

        return authenticationRepository.logout(request);
    }

}
