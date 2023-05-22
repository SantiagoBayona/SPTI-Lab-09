package edu.eci.cvds.servlet.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User{
    @Id
    private String name;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="telephone", nullable = false)
    private String telephone;
    @Column(name="password", nullable = false)
    private String password;


    public User(){}

    public User(String name, String email, String telephone,String password){
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
    }
    
}