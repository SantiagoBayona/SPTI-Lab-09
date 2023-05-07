package edu.eci.cvds.services;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.rules.ExpectedException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Rule;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.sql.Date;

import edu.eci.cvds.servlet.repositories.AppointmentRepository;
import edu.eci.cvds.servlet.services.AppointmentService; 
import edu.eci.cvds.servlet.model.Appointment;
import edu.eci.cvds.servlet.model.User;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
    
    @Mock
    private AppointmentRepository appointmentRepository;
    
    @InjectMocks
    private AppointmentService appointmentService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    
    @Test
    public void testCreateAppointment() {
        Appointment appointment = new Appointment();
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        Appointment createdAppointment = appointmentService.createAppointment(appointment);
        assertEquals(appointment, createdAppointment);
        verify(appointmentRepository, times(1)).save(appointment);
    }
    
    @Test
    public void testUpdateAppointment() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        Appointment updatedAppointment = appointmentService.updateAppointment(appointment);
        assertEquals(appointment, updatedAppointment);
        verify(appointmentRepository, times(1)).save(appointment);
    }
    
    @Test
    public void testDeleteAppointment() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        doNothing().when(appointmentRepository).delete(appointment);
        appointmentService.deleteAppointment(appointment);
        verify(appointmentRepository, times(1)).delete(appointment);
    }
    
    @Test
    public void testFindAppointmentById() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        Appointment foundAppointment = appointmentService.findAppointmentById(1L);
        assertEquals(appointment, foundAppointment);
        verify(appointmentRepository, times(1)).findById(1L);
    }
    
    public void testFindAppointmentByIdNotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());
        exceptionRule.expect(Exception.class);
        appointmentService.findAppointmentById(1L);
    }

    @Test
    public void testFindAppointmentsByUser() {
        User user = new User();
        user.setId(1L);
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        appointment1.setUser(user);
        appointments.add(appointment1);
        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        appointment2.setUser(user);
        appointments.add(appointment2);
        when(appointmentRepository.findByUser(user)).thenReturn(appointments);
        List<Appointment> foundAppointments = appointmentService.findAppointmentsByUser(user);
        assertEquals(appointments, foundAppointments);
        verify(appointmentRepository, times(1)).findByUser(user);
    }
    
    @Test
    public void testFindAppointmentsBetweenDates() {
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(startDate.getTime() + 86400000L);
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        appointment1.setStartDate(startDate);
        appointment1.setEndDate(endDate);
        appointments.add(appointment1);
        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        appointment2.setStartDate(startDate);
        appointment2.setEndDate(endDate);
        appointments.add(appointment2);
        when(appointmentRepository.findByStartDateBetween(startDate, endDate)).thenReturn(appointments);
        List<Appointment> foundAppointments = appointmentService.findAppointmentsBetweenDates(startDate, endDate);
        assertEquals(appointments, foundAppointments);
        verify(appointmentRepository, times(1)).findByStartDateBetween(startDate, endDate);
    }
}
