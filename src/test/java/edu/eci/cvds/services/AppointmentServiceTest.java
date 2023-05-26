package edu.eci.cvds.services;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.rules.ExpectedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Rule;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.Date;

import edu.eci.cvds.servlet.repositories.AppointmentRepository;
import edu.eci.cvds.servlet.services.AppointmentService; 
import edu.eci.cvds.servlet.model.Appointment;
import edu.eci.cvds.servlet.model.User;

import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.Transport;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
    
    @Mock
    private AppointmentRepository appointmentRepository;
    
    @InjectMocks
    private AppointmentService appointmentService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
   
    @Test
    public void shouldCreateAppointment() {
        Date startDate = new Date(System.currentTimeMillis() + 86400000L);
        Appointment appointment = new Appointment();
        appointment.setUser(new User());
        appointment.getUser().setEmail("user@example.com");
        appointment.setStartDate(startDate);
        appointment.setSignature("test");
        appointment.setTermsAccepted(true);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        appointmentService.createAppointment(appointment);
        verify(appointmentRepository).save(appointment);
    }
    
    @Test
    public void shouldCreateAppointment_ValidEmail_Success() {
        Date startDate = new Date(System.currentTimeMillis() + 86400000L);
        Appointment appointment = new Appointment();
        User user = new User();
        user.setEmail("example@example.com");
        appointment.setUser(user);
        appointment.setStartDate(startDate);
        appointment.setSignature("test");
        appointment.setTermsAccepted(true);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        Appointment savedAppointment = appointmentService.createAppointment(appointment);
        assertNotNull(savedAppointment);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    public void shouldCreateAppointment_InvalidEmail_ExceptionThrown() {
        Date startDate = new Date(System.currentTimeMillis() + 86400000L);
        Appointment appointment = new Appointment();
        User user = new User();
        user.setEmail("exampleexamplecom");
        appointment.setUser(user);
        appointment.setStartDate(startDate);
        appointment.setSignature("test");
        appointment.setTermsAccepted(true);
        assertThrows(java.lang.NullPointerException.class, () -> {
            appointmentService.createAppointment(appointment);
        });
    }

    @Test
    public void shouldUpdateAppointment() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        Appointment updatedAppointment = appointmentService.updateAppointment(appointment);
        assertEquals(appointment, updatedAppointment);
        verify(appointmentRepository, times(1)).save(appointment);
    }
    
    @Test
    public void shouldDeleteAppointment() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        doNothing().when(appointmentRepository).delete(appointment);
        appointmentService.deleteAppointment(appointment);
        verify(appointmentRepository, times(1)).delete(appointment);
    }
    
    @Test
    public void shouldFindAppointmentById() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        Appointment foundAppointment = appointmentService.findAppointmentById(1L);
        assertEquals(appointment, foundAppointment);
        verify(appointmentRepository, times(1)).findById(1L);
    }
    
    public void shouldFindAppointmentByIdNotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());
        exceptionRule.expect(Exception.class);
        appointmentService.findAppointmentById(1L);
    }

    @Test
    public void shouldFindAppointmentsByUser() {
        User user = new User();
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
    public void shouldFindAppointmentsBetweenDates() {
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(startDate.getTime() + 86400000L);
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        appointment1.setStartDate(startDate);
        appointments.add(appointment1);
        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        appointment2.setStartDate(startDate);
        appointments.add(appointment2);
        when(appointmentRepository.findByStartDateBetween(startDate, endDate)).thenReturn(appointments);
        List<Appointment> foundAppointments = appointmentService.findAppointmentsBetweenDates(startDate, endDate);
        assertEquals(appointments, foundAppointments);
        verify(appointmentRepository, times(1)).findByStartDateBetween(startDate, endDate);
    }

    public void shouldSendConfirmationEmail_ValidInputs_EmailSent() throws Exception {
        User user = new User();
        user.setEmail("example@example.com");
        Appointment appointment = new Appointment();
        appointment.setUser(user);
        Session session = mock(Session.class);
        MimeMessage message = mock(MimeMessage.class);
        Transport transport = mock(Transport.class);
        appointmentService.sendConfirmationEmail(user, appointment);
        verify(session, times(1)).getTransport();
        verify(transport, times(1)).connect();
        verify(transport, times(1)).sendMessage(message, message.getAllRecipients());
        verify(transport, times(1)).close();
    }

    @Test
    public void validateStartDate_StartDateBeforeCurrentDate_ThrowsException() {
        Date startDate = new Date(122, 0, 1); // Year is represented as year - 1900, so 2022 becomes 122
        AppointmentService appointmentService = new AppointmentService();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.validateStartDate(startDate);
        });
    }
    
    @Test
    public void validateStartDate_StartDateAfterCurrentDate_NoExceptionThrown() {
        Date startDate = new Date(123, 11, 12); // Year is represented as year - 1900, so 2023 becomes 123
        AppointmentService appointmentService = new AppointmentService();
        Assertions.assertDoesNotThrow(() -> {
            appointmentService.validateStartDate(startDate);
        });
    }

}


