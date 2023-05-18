package edu.eci.cvds.servlet.services;

import edu.eci.cvds.servlet.model.Appointment;
import edu.eci.cvds.servlet.model.User;
import edu.eci.cvds.servlet.repositories.AppointmentRepository;
import edu.eci.cvds.exception.Exception;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(Appointment appointment) {
        validateStartDate(appointment.getStartDate());
        isValidTermsAccepted(appointment.getTermsAccepted());
        isValidSignature(appointment.getSignature());
        Appointment savedAppointment = appointmentRepository.save(appointment);
        sendConfirmationEmail(savedAppointment.getUser(), savedAppointment);
        return savedAppointment;
    }

    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }

    public Appointment findAppointmentById(Long id) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isPresent()) {
            return optionalAppointment.get();
        } else {
            throw new Exception("No encontramos el cita con el id: " + id);
        }
    }

    public List<Appointment> findAppointmentsByUser(User user) {
        return appointmentRepository.findByUser(user);
    }

    public List<Appointment> findAppointmentsBetweenDates(Date startDate, Date endDate) {
        return appointmentRepository.findByStartDateBetween(startDate, endDate);
    }

    public List<Appointment> findAllAppointments(){
        return appointmentRepository.findAll();
    }

    public void sendConfirmationEmail(User user, Appointment appointment) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("airetupalCVDS@gmail.com", "mizyyfjtlahjotik");
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("airetupalCVDS@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("Confirmación de cita");
            message.setText("Hola " + user.getName() + ",\n\n"
                + "Te confirmamos que tu cita para el día " + appointment.getStartDate()
                + " ha sido agendada correctamente.\n\n"
                + "¡Gracias por confiar en nosotros!");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void validateStartDate(Date startDate) {
        Date currentDate = new Date(System.currentTimeMillis());
        if (startDate.before(currentDate)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser anterior a la fecha actual");
        }
    }

    private void isValidTermsAccepted(Boolean termsAccepted) {
        if (termsAccepted == null){
            throw new IllegalArgumentException("Confirme los terminos y condiciones");
        }
    }

    private void isValidSignature(String signature) {
        if (signature == null){
            throw new IllegalArgumentException("No a escrito du firma esta vacio");
        }
    }
}