package ru.netology.springrest.repository;

import org.springframework.stereotype.Component;
import ru.netology.springrest.model.Authorities;
import ru.netology.springrest.model.User;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserRepository {
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public void register(User guest) {
        users.put(guest.getName(), guest);
    }

    public List<Authorities> getUserAuthorities(String user, String password) {
        if(!users.containsKey(user)){
            return null;
        }
        User authorized = users.get(user);
        if(!authorized.getPassword().equals(password) || authorized.getAuthorities() == null || authorized.getAuthorities().isEmpty()){
            return null;
        }
        return authorized.getAuthorities();
    }
}