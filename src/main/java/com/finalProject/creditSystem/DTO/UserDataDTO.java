package com.finalProject.creditSystem.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class UserDataDTO implements Serializable {

    @Size(min = 5, max = 15)
    private String username;

    @NotBlank
    @Email(message = "Email not valid")
    private String email;

    @Size(min = 5, max = 20)
    private String password;

    private List<String> role;

    @NotNull
    private Double income;


    @NotBlank
    private Long identityNumber;

    @NotBlank
    private String name_surName;

    @NotBlank
    private Long mNumber;


}
