package com.jacopocarlini.arco.controller;

import com.jacopocarlini.arco.model.UserAuth;
import com.jacopocarlini.arco.model.UserDetails;
import com.jacopocarlini.arco.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<UserDetails> signup(@RequestBody @Valid UserDetails userDetails) {
        return ResponseEntity.ok(userService.signup(userDetails));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserAuth userAuth) {
        return ResponseEntity.ok(userService.login(userAuth));
    }

    @PostMapping("/validate_jwt")
    public ResponseEntity<Boolean> validateJwt(@RequestBody @NotBlank String token) {
        return ResponseEntity.ok(userService.validateJwt(token));
    }

}
