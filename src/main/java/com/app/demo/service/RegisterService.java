package com.app.demo.service;

import com.app.demo.email.EmailSender;
import com.app.demo.error.exception.UsernameAlreadyExistsException;
import com.app.demo.model.User;
import com.app.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailSender emailSender;

    public User register(String firstName, String lastName, String username, String password, String email){
        if(firstName==null || password==null || lastName==null || username==null || email==null)
            return null;

        if(userRepository.findByUserName(username)!=null)
            throw new UsernameAlreadyExistsException("Username already exists!");

        else {
            User user =new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(encoder.encode(password));

            emailSender.sendEmail(user.getEmail(), "Successful registration on resumeNow", "Welcome to resumeNow "+user.getFirstName() +"! You can now create your perfect resume. xoxo -resumeNow Team (Macesului 13-15)");
            return userRepository.save(user);
        }

    }
}
