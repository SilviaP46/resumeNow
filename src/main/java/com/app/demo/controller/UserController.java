package com.app.demo.controller;

import com.app.demo.dto.UserDTO;
import com.app.demo.email.EmailSender;
import com.app.demo.model.User;
import com.app.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", originPatterns = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailSender emailSender;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/users/delete/{id}")
    public Boolean deleteUserById(@PathVariable Long id) {
        return userService.removeUser(id);
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @GetMapping("/users/find/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.findUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/users/email")
    public Boolean sendEmail(@RequestParam String email, @RequestParam String subject,
                             @RequestParam String text) {
        return emailSender.sendEmail(email, subject, text);
    }

}
