package edu.eci.cvds.servlet.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.eci.cvds.servlet.model.Appointment;
import edu.eci.cvds.servlet.model.User;
import edu.eci.cvds.servlet.services.AppointmentService;
import edu.eci.cvds.servlet.services.UserService;
import lombok.Data;

@Component
@ManagedBean
@ViewScoped
@Data
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
    private Date minDate;
    private Date maxDate;
    private boolean termsAccepted;
    private String description;
    private String signature;
    private String state = "Agendada";
    private Appointment selectedAppointment;
    private Part pdfFile;
    private Part imageFile;
    private ArrayList<Appointment> appointments;
    private byte[] pdfBytes;
    private byte[] imageBytes;

    @PostConstruct
    public void init(){
        Date today = new Date();
        long day = 24 * 60 * 60 * 1000;
        minDate = new Date(today.getTime() - (7*day));
        maxDate = new Date(today.getTime() + (7*day));
    }

    public String logiregistern() throws IOException {
        pdfBytes = (pdfFile != null) ? pdfFile.getInputStream().readAllBytes() : null;
        imageBytes = (imageFile != null) ? imageFile.getInputStream().readAllBytes() : null;

        User temp = new User(this.name, this.email, this.telephone,"no pass");
        this.userService.createUser(temp);
        this.appointmentService.createAppointment(new Appointment(temp, this.startDate, this.termsAccepted, this.description, this.signature, this.state, this.pdfBytes, this.imageBytes));
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


    public void updateAppointments(){
        this.appointments= (ArrayList<Appointment>) this.appointmentService.findAllAppointments();
    }

    public void attended(){
        this.selectedAppointment.setState("Atendida");
        this.appointmentService.updateAppointment(this.selectedAppointment);
    }

    public void canceled(){
        this.selectedAppointment.setState("Cancelada");
        this.appointmentService.updateAppointment(this.selectedAppointment);
    }

    public void paid(){
        this.selectedAppointment.setState("Pagada");
        this.appointmentService.updateAppointment(this.selectedAppointment);
    }
}