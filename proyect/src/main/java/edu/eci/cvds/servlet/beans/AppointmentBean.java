package edu.eci.cvds.servlet.beans;

import edu.eci.cvds.servlet.model.User;
import edu.eci.cvds.servlet.services.UserService;
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
@ManagedBean
@SessionScoped
public class AppointmentBean implements Serializable {

    @Autowired
    AppointmentService appointmentService;
    @Autowired
    UserService userService;
    private Long id;
    private String name;
    private int telephone;
    private String email;
    private Date startDate;
    private Date endDate;
    private boolean termsAccepted;
    private String description;
    private String signature;
    private ArrayList<Appointment> appointments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        User temp = new User(this.name, this.email, "no pass",this.description);
        this.userService.createUser(temp);
        this.appointmentService.createAppointment(new Appointment(temp, this.startDate, this.termsAccepted, this.description, this.signature));
        return "index.xhtml?faces-redirect=true";
    }

    public ArrayList<Appointment> getAppointments(){
        return this.appointments;
    }

    public void updateAppointments(){
        this.appointments= (ArrayList<Appointment>) this.appointmentService.findAllAppointments();
    }
}

