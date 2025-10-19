package com.farmchain.farmchain.controller;


import com.farmchain.farmchain.dto.RegisterRequest;
import com.farmchain.farmchain.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService ;

    public AuthController(AuthService authService){
        this.authService = authService ;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody RegisterRequest request){
        String result = authService.register(request) ;
        if("Email already exists!" .equals(result)){
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result) ;
    }


}
