package com.finalProject.creditSystem.Service;

import com.finalProject.creditSystem.DTO.UserUpdateDTO;
import com.finalProject.creditSystem.Entities.Role;
import com.finalProject.creditSystem.Entities.User;
import com.finalProject.creditSystem.Exception.CustomJwtException;
import com.finalProject.creditSystem.Exception.EntityNotFoundException;
import com.finalProject.creditSystem.Repository.UserRepository;
import com.finalProject.creditSystem.Security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {


    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;



    public List<User> getAll() {
        return userRepository.findAll();

    }

    public void delete(String username) {
        User byUsername = userRepository.findByUsername(username);
        if (byUsername == null) {
            throw new EntityNotFoundException("User", "username : " + username);
        } else if (byUsername.getRoles().contains(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("No permission to delete user : " + username);
        }
        userRepository.deleteByUsername(username);
    }

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomJwtException("Invalid username/password supplied", HttpStatus.BAD_REQUEST);
        }
    }

    public String signup(User user, boolean isAdmin) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = isAdmin ? Role.ROLE_ADMIN : Role.ROLE_CLIENT;
            user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        } else {
            throw new CustomJwtException("Username is already in use", HttpStatus.BAD_REQUEST);
        }
    }

    public User updateUser(String username, UserUpdateDTO userUpdateDTO) {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        if (!userByUsername.isPresent()) {
            throw new EntityNotFoundException("User", "Not Found");
            }
            User updateUser = userByUsername.get();
            log.debug("Username : " + userUpdateDTO.getUsername());
            if (!StringUtils.isEmpty(userUpdateDTO.getEmail())) {
                updateUser.setEmail(userUpdateDTO.getEmail());
            }
            if (!StringUtils.isEmpty(userUpdateDTO.getIncome())) {
                updateUser.setIncome(userUpdateDTO.getIncome());
            }
            if (!StringUtils.isEmpty(userUpdateDTO.getUsername())) {
                updateUser.setUsername(userUpdateDTO.getUsername());
            }
            if (!StringUtils.isEmpty(userUpdateDTO.getPassword())) {
                updateUser.setPassword(userUpdateDTO.getPassword());
            }
            if (!StringUtils.isEmpty(userUpdateDTO.getMNumber())) {
                updateUser.setMNumber(userUpdateDTO.getMNumber());
            }
            return userRepository.save(updateUser);

    }

    public User getUserByUsername(String username){
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }else {
            throw new EntityNotFoundException("User", "Not found");
        }
    }
}

