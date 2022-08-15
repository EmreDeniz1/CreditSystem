package com.finalProject.creditSystem.Entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Entity
public class Credit {

    @Id
    private UUID id;
    private String username;
    private Integer creditScore;
    private Double creditLimit;
    private Boolean creditApproved;

    public Credit(String username, Integer creditScore, Double creditLimit, Boolean creditApproved) {
        this.creditApproved = creditApproved;
        this.id = UUID.randomUUID();
        this.username = username;
        this.creditScore = creditScore;
        this.creditLimit = creditLimit;
    }
}
