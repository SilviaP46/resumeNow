package com.app.demo.service;

import com.app.demo.dto.UserDTO;
import com.app.demo.email.EmailSender;
import com.app.demo.model.Enums.UserStatus;
import com.app.demo.model.User;
import com.app.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.app.demo.model.User.mapToDTO;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EmailSender emailSender;

    public List<UserDTO> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        System.out.println("stefana face yoga cu knoll"+ encoder.encode(users.get(0).getPassword()));
        return users.stream().map(User::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public UserDTO addUser(UserDTO user) throws ValidationException {

        User userSimple = mapToUser(user);

        if (!userSimple.getFirstName().equals("") && !userSimple.getLastName().equals("")
                && !userSimple.getUsername().equals("") && !userSimple.getPassword().equals("")
                && !userSimple.getMobileNumber().equals("") )
        //&& validateEmailAndMobile(userSimple.getEmail(), userSimple.getMobileNumber()))
        {

            String passNotEncrypted = userSimple.getPassword();
            userSimple.setStatus(UserStatus.INACTIVE);
            userSimple.setPassword(encoder.encode(userSimple.getPassword()));
            userRepository.save(userSimple);

            String text = "Name: " + userSimple.getFirstName() + " " + userSimple.getLastName() + "\n" + "Username: " + userSimple.getUsername() + "\n" + "Password: " + passNotEncrypted;


            emailSender.sendEmail(userSimple.getEmail(), "Successful registration", text);
            return mapToDTO(userSimple);
        } else {
            throw new ValidationException("One of the fields was not valid!");

        }
    }


    public UserDTO findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new UsernameNotFoundException("User could not be found!");

        return mapToDTO(user);
    }

    public UserDTO findUserByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null)
            throw new UsernameNotFoundException("User " + username + " could not be found!");

        return mapToDTO(user);
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

    @Transactional
    public UserDTO activateUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new UsernameNotFoundException("User could not be found!");
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        return mapToDTO(user);
    }

    @Transactional
    public UserDTO deactivateUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new UsernameNotFoundException("User could not be found!");

        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
        //notificationService.addUserNotificationUserDeactivated(user);
        return mapToDTO(user);
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
        user.setStatus(userDTO.getStatus());
        user.setMobileNumber(userDTO.getMobileNumber());

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
