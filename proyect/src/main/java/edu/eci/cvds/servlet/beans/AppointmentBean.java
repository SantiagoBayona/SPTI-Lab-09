package edu.eci.cvds.servlet.beans;

import edu.eci.cvds.servlet.model.User;
import edu.eci.cvds.servlet.services.AppointmentService;
import edu.eci.cvds.servlet.model.Appointment;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ManagedBean(name = "userBean")
@SessionScoped
public class AppointmentBean implements Serializable {

    @Autowired
    AppointmentService appointmentService;
    private Long id;
    private User user;
    private Date startDate;
    private Date endDate;
    private boolean termsAccepted;
    private String description;
    private byte[] signature;
    private ArrayList<Appointment> appointments;
    
    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
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

    public String logiregistern (){
        return "nextPage.xhtml";
    }

    public ArrayList<Appointment> getAppointments(){
        return this.appointments;
    }

    public void updateAppointments(){
        this.appointments= (ArrayList<Appointment>) this.appointmentService.findAllAppointments();
    }
}

