package com.app.demo.model;

import com.app.demo.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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
    //@Pattern(regexp="^[A-Za-z0-9.-]+@msg\\.group$")
    private String email;

    @Column(unique = true)
    private String username;

    private String password;


    public static UserDTO mapToDTO(User user){
        UserDTO dto=new UserDTO();
        dto.setIdUser(user.getIdUser());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        return dto;
    }
}
