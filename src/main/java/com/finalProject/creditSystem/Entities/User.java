package com.finalProject.creditSystem.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Table(name = "user_pg")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name_surName;

    @NotBlank
    @Column(unique = true, nullable = false)
    //@Pattern(regexp = "/^(05)([0-9]{2})\\s?([0-9]{3})\\s?([0-9]{2})\\s?([0-9]{2})$/",
      //     message = "Cep telefonunuzu giriniz")
    private Long mNumber;

    @NotBlank
    @Column(unique = true, nullable = false)
    //@Pattern(regexp = "^[a-zA-Z0-9]{6,12}$",
     //       message = "11 Hnaneli Kimlik Numaranız")
    private Long identityNumber;

    @NotNull
    private Double income;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Size(max = 12)
    @Pattern(regexp="^((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])){4,12}$", message = "Şifreniz en az 1 sayı ve büyük karakter içermelidir. ")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated
    private List<Role> roles;








}
