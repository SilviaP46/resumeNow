package com.app.demo.model;

import com.app.demo.dto.UserDTO;
import com.app.demo.model.Enums.UserStatus;
import lombok.*;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_gen")
    @SequenceGenerator(name = "users_gen", sequenceName = "users_seq", allocationSize = 1)
    private Long idUser;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    //@Pattern(regexp = "\\(?\\+\\(?49\\)?[ ()]?([- ()]?\\d[- ()]?){10}|\\(?\\+\\(?40\\)?[ ()]?([- ()]?\\d[- ()]?){9}")
    private String mobileNumber;

    @Column(nullable = false)
    //@Pattern(regexp="^[A-Za-z0-9.-]+@msg\\.group$")
    private String email;

    @Column(unique = true)
    private String username;

    private String password;
    private UserStatus status;

    @Column(columnDefinition = "integer default 0")
    private Short failed;


    public static UserDTO mapToDTO(User user){
        UserDTO dto=new UserDTO();
        dto.setIdUser(user.getIdUser());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setStatus(user.getStatus());

        return dto;
    }
}
