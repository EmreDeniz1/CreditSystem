package com.finalProject.creditSystem.Controller;


import com.finalProject.creditSystem.DTO.UserDataDTO;
import com.finalProject.creditSystem.DTO.UserLoginDTO;
import com.finalProject.creditSystem.DTO.UserUpdateDTO;
import com.finalProject.creditSystem.Entities.User;
import com.finalProject.creditSystem.Exception.CustomJwtException;
import com.finalProject.creditSystem.Service.UserService;
import com.finalProject.creditSystem.UserPrincipal;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;




    @PostMapping("/signin")
    public String login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return userService.signin(userLoginDTO.getUsername(), userLoginDTO.getPassword());
    }

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid UserDataDTO userDataDTO) {
        User user = new User();
        user.setUsername(userDataDTO.getUsername());
        user.setEmail(userDataDTO.getEmail());
        user.setPassword(userDataDTO.getPassword());
        user.setIncome(userDataDTO.getIncome());
        user.setIdentityNumber(userDataDTO.getIdentityNumber());
        user.setName_surName(userDataDTO.getName_surName());
        user.setMNumber(userDataDTO.getMNumber());
        return userService.signup(user, false);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_CLIENT')")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{username}")
    public String delete(@PathVariable String username) {
        userService.delete(username);
        return username;
    }

    @PreAuthorize("hasRole('ROLE_CLIENT') or hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{username}")
    public String update(@PathVariable String username, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        if (!username.equals(getPrincipal().getUsername())) {
            throw new CustomJwtException("Users can delete only their own accounts", HttpStatus.NOT_ACCEPTABLE);
        }
        userService.updateUser(username, userUpdateDTO);
        return username;
    }



    private UserPrincipal getPrincipal () {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserPrincipal) auth.getPrincipal();
    }
}
