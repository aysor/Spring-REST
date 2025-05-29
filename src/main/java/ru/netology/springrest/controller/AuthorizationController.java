package ru.netology.springrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.springrest.model.Authorities;
import ru.netology.springrest.model.User;
import ru.netology.springrest.model.exceptions.InvalidCredentials;
import ru.netology.springrest.model.exceptions.UnauthorizedUser;
import ru.netology.springrest.service.AuthorizationService;

import java.util.List;

@RestController
public class AuthorizationController {
    AuthorizationService service;

    @Autowired
    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public void register(@RequestBody User guest) {
        service.register(guest);
    }

    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@RequestParam("user") String user, @RequestParam("password") String password) {
        List<Authorities> authorities = service.getAuthorities(user, password);
        return authorities;
    }

    @ExceptionHandler(UnauthorizedUser.class)
    public ResponseEntity<String> unauthorizedHandler(UnauthorizedUser e) {
        System.out.println(e);
        return new ResponseEntity<String>(e.toString(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidCredentials.class)
    public ResponseEntity<String> invalidCredentialsHandler(InvalidCredentials e) {
        System.out.println(e);
        return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
    }
}