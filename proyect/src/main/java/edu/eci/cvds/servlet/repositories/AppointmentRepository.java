package edu.eci.cvds.servlet.repositories;

import edu.eci.cvds.servlet.model.Appointment;
import edu.eci.cvds.servlet.model.User;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUser(User user);
    List<Appointment> findByStartDateBetween(Date startDate, Date endDate);
}
