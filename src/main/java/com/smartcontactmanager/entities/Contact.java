package com.smartcontactmanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;


@Entity
@Table(name = "CONTACT")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int cId;
    @NotBlank(message = "User name can't be blank.")
    @Size(min = 3,max = 12,message = "user name must be between 3-12 character")
    private String name;
    private String secondName;
    private String work;
    @Email(regexp = "[a-zA-Z][a-zA-Z0-9-.]*@[a-zA-Z0-9]+([.][a-zA-Z][a-zA-Z]+)+")
    private String email;
    @Size(min = 10,max = 10,message = "phone number length must be 10")
    private String phone;
    private String image;
    @Column(length = 1000)
    private String description;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        return  this.cId==((Contact)o).getcId();
    }

}
