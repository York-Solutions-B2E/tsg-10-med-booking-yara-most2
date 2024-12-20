package net.yorksolutions.backend.service;

import net.yorksolutions.backend.dto.AppointmentDTO;
import net.yorksolutions.backend.entity.Appointment;
import net.yorksolutions.backend.entity.Doctor;
import net.yorksolutions.backend.entity.Patient;
import net.yorksolutions.backend.repository.AppointmentRepository;
import net.yorksolutions.backend.repository.DoctorRepository;
import net.yorksolutions.backend.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService underTest;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    private Patient patient;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);


    }

    @Test
    void itShouldCreateAppointment() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDoctorId(1L);
        appointmentDTO.setPatientId(1L);
        appointmentDTO.setAppointmentDateTime(LocalDateTime.of(2025, 10, 10, 10, 10));
        appointmentDTO.setVisitType("TELEHEALTH");
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Patient patient = new Patient();
        patient.setId(1L);

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findByPatientIdAndDoctorIdOnDate(anyLong(), anyLong(), any(), isNull())).thenReturn(Collections.emptyList());

        Appointment expectedAppointment = new Appointment(patient, doctor, appointmentDTO.getAppointmentDateTime(), Appointment.VisitType.TELEHEALTH);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(expectedAppointment);

        // Act
        Appointment result = underTest.createAppointment(appointmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(Appointment.VisitType.TELEHEALTH, result.getVisitType());
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    void getAppointments() {
    }

    @Test
    void getAppointmentsByPatientId() {
    }

    @Test
    void getAppointmentsByDoctorId() {
    }

    @Test
    void updateAppointment() {
    }

    @Test
    void cancelAppointment() {
    }

    @Test
    void conflictingAppointment() {
    }
}