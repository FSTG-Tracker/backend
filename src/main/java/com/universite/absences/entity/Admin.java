package com.universite.absences.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admins")
@Getter
@Setter
public class Admin extends User {
    @Override
    public String getRole() {
        return "ADMIN";
    }
}
