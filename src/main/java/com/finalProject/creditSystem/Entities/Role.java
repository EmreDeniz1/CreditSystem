package com.finalProject.creditSystem.Entities;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


public enum Role implements GrantedAuthority {

    ROLE_ADMIN, ROLE_CLIENT;

    public String getAuthority() {
        return name();
    }

}

