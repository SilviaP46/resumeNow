package com.app.demo.service;

import com.app.demo.dto.UserDTO;
import com.app.demo.email.EmailSender;
import com.app.demo.model.User;
import com.app.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EmailSender emailSender;

    public List<User> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users;
    }

    public User findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new UsernameNotFoundException("User could not be found!");

        return user;
    }

    public User findUserByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null)
            throw new UsernameNotFoundException("User " + username + " could not be found!");

        return user;
    }


    private List<String> getUserNamesLike(String toString) {
        return userRepository.getUserNamesLike(toString);
    }


    @Transactional
    public Boolean removeUser(Long id) {
        if (findUserById(id) == null)
            throw new UsernameNotFoundException("User could not be found!");

        userRepository.deleteById(id);
        return true;
    }


    public User mapToUser(UserDTO userDTO) {
        User user=new User();
        user.setEmail(userDTO.getEmail());
        if (userDTO.getIdUser() != null) {
            user.setIdUser(userDTO.getIdUser());
        }
        if (userDTO.getUsername() != null && userDTO.getPassword() != null) {
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
        } else {
            user.setUsername(generateUserName(userDTO.getFirstName(), userDTO.getLastName()));
            user.setPassword(user.getUsername());
        }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());


        return user;
    }

    private String generateUserName(String firstName, String lastName) {
        firstName = firstName.toLowerCase(Locale.ROOT);
        lastName = lastName.toLowerCase(Locale.ROOT);
        StringBuilder stringBuilder = new StringBuilder();
        if (firstName.length() >= 5) {
            stringBuilder.append(firstName, 0, 5);
            stringBuilder.append(lastName.charAt(0));
        } else {
            stringBuilder.append(firstName);
            if (lastName.length() >= 6 - firstName.length())
                stringBuilder.append(lastName, 0, 6 - firstName.length());
            else {
                stringBuilder.append(lastName);
                if (stringBuilder.toString().length() < 6) {
                    stringBuilder.append("ABCD", 0, 6 - stringBuilder.toString().length());
                }
            }
        }
        List<String> usernamesLike = getUserNamesLike(stringBuilder.toString());
        if (usernamesLike.contains(stringBuilder.toString())) {
            stringBuilder.append(usernamesLike.size() + 1);
        }
        return stringBuilder.toString();
    }

}
