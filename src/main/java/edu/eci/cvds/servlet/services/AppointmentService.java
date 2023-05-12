package edu.eci.cvds.servlet.services;

import edu.eci.cvds.servlet.model.Appointment;
import edu.eci.cvds.servlet.model.User;
import edu.eci.cvds.servlet.repositories.AppointmentRepository;
import edu.eci.cvds.exception.Exception;

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
        return appointmentRepository.save(appointment);
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
}