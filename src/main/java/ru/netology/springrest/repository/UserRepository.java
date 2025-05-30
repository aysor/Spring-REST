package ru.netology.springrest.repository;

import org.springframework.stereotype.Component;
import ru.netology.springrest.model.Authorities;
import ru.netology.springrest.model.User;
import ru.netology.springrest.model.exceptions.InvalidCredentials;
import ru.netology.springrest.model.exceptions.UnauthorizedUser;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserRepository {
    ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public void register(User guest) {
        users.put(guest.getName(), guest);
    }

    public List<Authorities> getUserAuthorities(User user) {
        if (!users.containsKey(user.getName())) {
            return null;
        }
        User authorized = users.get(user.getName());
        if (!authorized.getPassword().equals(user.getPassword())) {
            throw new InvalidCredentials("Password is incorrect for user " + authorized.getName());
        } else if (authorized.getAuthorities() == null || authorized.getAuthorities().isEmpty()) {
            throw new UnauthorizedUser(String.format("User %s has no authorities", authorized.getName()));
        }

        return authorized.getAuthorities();
    }
}