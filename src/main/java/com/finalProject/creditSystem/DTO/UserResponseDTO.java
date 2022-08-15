package com.finalProject.creditSystem.DTO;

import com.finalProject.creditSystem.Entities.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

    private Integer id;
    private String username;
    private String email;
    private List<Role> roles;

}
