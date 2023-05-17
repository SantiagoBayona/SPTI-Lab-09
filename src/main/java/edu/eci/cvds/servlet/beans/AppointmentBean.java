package edu.eci.cvds.servlet.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.eci.cvds.servlet.model.Appointment;
import edu.eci.cvds.servlet.model.User;
import edu.eci.cvds.servlet.services.AppointmentService;
import edu.eci.cvds.servlet.services.UserService;

@Component
@ManagedBean
@ViewScoped
public class AppointmentBean implements Serializable {

    @Autowired
    AppointmentService appointmentService;
    @Autowired
    UserService userService;
    private Long id;
    private String name;
    private String telephone;
    private String email;
    private Date startDate;
    private boolean termsAccepted;
    private String description;
    private String signature;
    private String state = "Agendada";
    private Appointment selectedAppointment;
    private ArrayList<Appointment> appointments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }

    public String logiregistern (){
        User temp = new User(this.name, this.email, this.telephone,"no pass");
        this.userService.createUser(temp);
        this.appointmentService.createAppointment(new Appointment(temp, this.startDate, this.termsAccepted, this.description, this.signature, this.state));
        this.reset();
        return "index.xhtml?faces-redirect=true";
    }

    private void reset(){
        this.name = "";
        this.email = "";
        this.telephone = "";
        this.description = "";
        this.signature = "";
        this.state = "Agendada";
    }

    public ArrayList<Appointment> getAppointments(){
        return this.appointments;
    }

    public void updateAppointments(){
        this.appointments= (ArrayList<Appointment>) this.appointmentService.findAllAppointments();
    }

    public void modifyState(String state){
        this.selectedAppointment.setState(state);
        this.appointmentService.updateAppointment(this.selectedAppointment);
    }
}

