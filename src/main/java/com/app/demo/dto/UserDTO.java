package com.app.demo.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;

@Data
public class UserDTO extends RepresentationModel<UserDTO> {
    private Long idUser;
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String password;
    private String username;

    private String token;

}
