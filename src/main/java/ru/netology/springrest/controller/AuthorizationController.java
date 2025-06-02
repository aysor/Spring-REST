package ru.netology.springrest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.netology.springrest.model.Authorities;
import ru.netology.springrest.model.User;
import ru.netology.springrest.service.AuthorizationService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorizationController {
    private final AuthorizationService service;

    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid User guest) {
        service.register(guest);
    }

    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@Valid User user) {
        return service.getAuthorities(user);
    }

    @PostMapping("/handle-form")
    public ResponseEntity<?> handleForm(@RequestParam("targetController") String targetController,
                                        @RequestBody MultiValueMap<String, String> formData) {
        if("authorize".equals(targetController)) {
            String name = formData.get("name").get(0);
            String pwd = formData.get("password").get(0);
            List<Authorities> authorities = getAuthorities(new User(name, pwd));
            return ResponseEntity.ok("authorize");
        } else if ("register".equals(targetController)) {
            String name = formData.get("name").get(0);
            String pwd = formData.get("password").get(0);
            List<Authorities> authorities = Arrays.stream(formData.get("authorities").get(0).split(" "))
                                                        .map(Authorities::valueOf)
                                                        .collect(Collectors.toList());
            register(new User(name, pwd, authorities));
            return ResponseEntity.ok("register");
        } else {
            return ResponseEntity.badRequest().body("Invalid Controller");
        }
    }
}