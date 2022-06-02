package com.app.demo.service;

import com.app.demo.dto.UserDTO;
import com.app.demo.error.exception.TooManyFailedAttemptException;
import com.app.demo.error.exception.UserPasswordIncorrectException;
import com.app.demo.model.User;
import com.app.demo.repository.UserRepository;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private WebScrapingService webScrapingService;

    public UserDTO login(String username, String pwd) throws Exception {

        //webScrapingService.scanItems("medic cluj");

        User user = validateAndGet(username, pwd);
        if (user == null)
            throw new UserPasswordIncorrectException("User or password incorrect!");
        String token = getJWTToken(username, user.getIdUser());
        UserDTO dto = User.mapToDTO(user);
        dto.setToken(token);

        return dto;
    }

    public User validateAndGet(String username, String pwd) {
        User user = userRepository.findByUserName(username);
        if (user == null)
            return null;
        if (encoder.matches(pwd, user.getPassword())){
            return user;
        }
        return null;
    }

    /*public User validateAndGet(String username, String pwd) {
        System.out.println(encoder.encode("ok"));
        User user = userRepository.findByUserName(username);
        System.out.println(encoder.matches(pwd, user.getPassword()));
        System.out.println(pwd+""+ user.getPassword());
        if (user == null){
            System.out.println("NULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
            return null;}
        if (encoder.matches(pwd, user.getPassword())){
            System.out.println("MATCHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            userRepository.resetFailed(username);
            return user;
        }
        userRepository.increaseFailed(username);
        if(userRepository.getFailed(username)==5){
            userRepository.resetFailed(username);
            userRepository.forcedDeactivate(username);
            throw new TooManyFailedAttemptException("User deactivated for too many failed attempts!");
        }
        return null;
    }*/

    private String getJWTToken(String username, Long id) {
        String secretKey = "mySecretKey";
        User user = userRepository.findById(id).get();
        Set<String> permissions = new HashSet<>();

        String auth = String.join(",", permissions);
        List<GrantedAuthority> grantedAuthorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(auth);
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorityList.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        return token;
    }



}
