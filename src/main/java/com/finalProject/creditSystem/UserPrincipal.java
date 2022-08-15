package com.finalProject.creditSystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalProject.creditSystem.Entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String name_surName;
    private String mNumber;
    private Double income;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public Long getId() {
        return id;
    }

    public Double getIncome() {
        return income;
    }

    public String getName_surName(){
        return name_surName;
    }
    public String getmNumber() {
        return mNumber;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
