package net.yorksolutions.backend.repository;

import net.yorksolutions.backend.entity.Appointment;
import net.yorksolutions.backend.entity.Doctor;
import net.yorksolutions.backend.entity.Patient;
import net.yorksolutions.backend.entity.Specialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository underTest;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    private Patient patient1;
    private Specialization specialization1;
    private Doctor doctor1;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        // Create Patient
        patient1 = new Patient();
        patient1.setEmail("mdl@test.com");
        patient1.setFirstName("Luffy");
        patient1.setLastName("Monkey D.");
        patient1.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patientRepository.save(patient1);

        // Create Specialization
        specialization1 = new Specialization();
        specialization1.setName("Pediatric");
        specializationRepository.save(specialization1);

        // Create Doctor
        doctor1 = new Doctor();
        doctor1.setFirstName("Doctor");
        doctor1.setLastName("Chopper");
        doctor1.setSpecialization(specialization1);
        doctorRepository.save(doctor1);

        // Create Appointment
        appointment = new Appointment();
        appointment.setPatient(patient1);
        appointment.setDoctor(doctor1);
        appointment.setAppointmentDateTime(LocalDateTime.of(2025, 1, 25, 10, 0));
        appointment.setVisitType(Appointment.VisitType.IN_PERSON);
        appointment.setConfirmed(true);
        underTest.save(appointment);
    }

    @Test
    void findByPatientId() {
        List<Appointment> result = underTest.findByPatientId(patient1.getId());

        // then
        assertNotNull(result);
        assertEquals(1, result.size());

        Appointment found = result.get(0);

        assertEquals(patient1, found.getPatient());
    }

    @Test
    void findByDoctorId() {
        List<Appointment> result = underTest.findByPatientId(doctor1.getId());

        // then
        assertNotNull(result);
        assertEquals(1, result.size());

        Appointment found = result.get(0);

        assertEquals(doctor1, found.getDoctor());

    }

    @Test
    void findByPatientIdAndDoctorIdOnDate() {
        // this one doesn't pass because H2 db doesnt recognize DATE()
        // needs to adjust the query to AND CAST(a.appointmentDateTime AS DATE) = :appointmentDate
        // and it passes
        List<Appointment> result = underTest.findByPatientIdAndDoctorIdOnDate(
                patient1.getId(),
                doctor1.getId(),
                appointment.getAppointmentDateTime().toLocalDate(),
                null
        );

        assertNotNull(result);
        assertEquals(1, result.size());

        Appointment found = result.get(0);

        assertEquals(patient1, found.getPatient());
        assertEquals(doctor1, found.getDoctor());
        assertEquals(LocalDateTime.of(2025, 1, 25, 10, 0), found.getAppointmentDateTime());

    }
}