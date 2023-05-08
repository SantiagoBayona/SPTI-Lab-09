package edu.eci.cvds.servlet.model;

import java.sql.Date;
import java.util.Arrays;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @Column(name = "terms_accepted", nullable = false)
    private boolean termsAccepted;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "signature", nullable = false)
    private byte[] signature;

    public Appointment(){}

    public Appointment(User user, Date startDate, Date endDate, boolean termsAccepted, String description, byte[] signature) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termsAccepted = termsAccepted;
        this.description = description;
        this.signature = signature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean getTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }

    public byte[] getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return "Appointment [id=" + id + ", user=" + user + ", startDate=" + startDate + ", endDate=" + endDate
                + ", termsAccepted=" + termsAccepted + ", description=" + description + ", signature="
                + Arrays.toString(signature) + "]";
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}
