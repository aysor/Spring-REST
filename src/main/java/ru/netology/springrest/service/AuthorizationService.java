package ru.netology.springrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.springrest.model.Authorities;
import ru.netology.springrest.model.User;
import ru.netology.springrest.model.exceptions.InvalidCredentials;
import ru.netology.springrest.model.exceptions.UnauthorizedUser;
import ru.netology.springrest.repository.UserRepository;

import java.util.List;

@Service
public class AuthorizationService {
    private final UserRepository userRepository;

    @Autowired
    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(User guest) {
        userRepository.register(guest);
    }

    public List<Authorities> getAuthorities(User user) {
        if (isEmpty(user.getName()) || isEmpty(user.getPassword())) {
            throw new InvalidCredentials("User name or password is empty");
        }
        List<Authorities> userAuthorities = userRepository.getUserAuthorities(user);
        if (isEmpty(userAuthorities)) {
            throw new UnauthorizedUser("Unknown user " + user);
        }
        return userAuthorities;
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isEmpty(List<?> str) {
        return str == null || str.isEmpty();
    }
}
