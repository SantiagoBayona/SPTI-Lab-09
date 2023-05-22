package edu.eci.cvds.servlet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name= "ADMINISTRATORS")
@Data
public class Administrator {
    
    @Id
    private String name;
    @Column(name="password", nullable = false)
    private String password;

    public Administrator(String name, String password){
        this.name = name;
        this.password = password;
    }

    public Administrator(){
        
    }

}