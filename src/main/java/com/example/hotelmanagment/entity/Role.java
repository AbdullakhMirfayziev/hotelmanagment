package com.example.hotelmanagment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {
    @EmbeddedId
    private RoleKey id;

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User user;

    public RoleKey getId() {
        return id;
    }

    public void setId(RoleKey id) {
        this.id = id;
    }

    public String getUsername() {
        return user.getEmail();
    }


    public String getRole() {
        return id.getRole();
    }

    public void setRole(String role) {
        if (this.id == null) {
            this.id = new RoleKey();
        }
        this.id.setRole(role);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                '}';
    }
}
