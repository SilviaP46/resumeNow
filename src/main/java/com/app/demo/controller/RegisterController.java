package com.app.demo.controller;
import com.app.demo.model.User;
import com.app.demo.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", originPatterns = "*", allowedHeaders = "*")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> register(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("username") String username, @RequestParam("password") String password,@RequestParam("email") String email) throws Exception {
        return new ResponseEntity<>(registerService.register(firstName, lastName,username, password,email), HttpStatus.OK);
    }
}
