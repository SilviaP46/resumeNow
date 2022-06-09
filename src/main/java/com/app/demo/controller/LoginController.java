package com.app.demo.controller;

import com.app.demo.dto.UserDTO;
import com.app.demo.model.User;
import com.app.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", originPatterns = "*", allowedHeaders = "*")
public class LoginController {
    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> login(@RequestParam("user") String username, @RequestParam("password") String pwd) throws Exception {
        return new ResponseEntity<>(loginService.login(username, pwd), HttpStatus.OK);
    }
}
