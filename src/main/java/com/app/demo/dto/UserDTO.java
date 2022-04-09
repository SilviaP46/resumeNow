package com.app.demo.dto;

import com.app.demo.model.Enums.UserStatus;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import java.util.List;

@Data
public class UserDTO extends RepresentationModel<UserDTO> {
    private Long idUser;
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    @Column(name = "mobile_number")
    private String mobileNumber;

    private String password;
    private String username;

    private String token;
    private UserStatus status;

}
