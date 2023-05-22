package edu.eci.cvds.servlet.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "appointments")
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_name", nullable = false)
    private User user;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "terms_accepted", nullable = false)
    private boolean termsAccepted;
    @Column(name = "description", nullable = false)
    private String description;
    @Lob
    @Column(name = "signature", nullable = false)
    private String signature;
    @Column(name = "state", nullable = false )
    private String state;
    
    public Appointment(){
        
    }

    public Appointment(User user, Date startDate, boolean termsAccepted, String description, String signature, String state) {
        this.user = user;
        this.startDate = startDate;
        this.termsAccepted = termsAccepted;
        this.description = description;
        this.signature = signature;
        this.state = state;
    }
}