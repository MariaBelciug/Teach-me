package com.assist.internship.service;

import com.assist.internship.model.Role;
import com.assist.internship.model.User;
import com.assist.internship.repository.RoleRepository;
import com.assist.internship.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {


    private RestTemplate restTemplate;

//    public UserServiceImpl (RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(user.getPassword());
        Role userRole = roleRepository.findByName("ADMIN");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);
    }

    @Override
    public User findUserById(long id)
    {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() { return userRepository.findAll(); }
    @Override
    public User findUserByResetToken(String token) { return userRepository.findByResetToken(token); }

    @Override
    public User deleteUserByEmail(String email){
        return userRepository.deleteByEmail(email);
    }

}
