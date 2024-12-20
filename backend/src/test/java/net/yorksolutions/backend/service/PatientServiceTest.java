package net.yorksolutions.backend.service;

import net.yorksolutions.backend.entity.Patient;
import net.yorksolutions.backend.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;
    private PatientService underTest;

    private Patient patient1;

    @BeforeEach
    void setUp() {
        underTest = new PatientService(patientRepository);

        patient1 = new Patient();
        patient1.setEmail("mdl@test.com");
        patient1.setFirstName("Luffy");
        patient1.setLastName("Monkey D.");
        patient1.setDateOfBirth(LocalDate.of(1990, 1, 1));
    }

    @Test
    void itShouldGetAllPatients() {
        underTest.getAllPatients();
        verify(patientRepository).findAll();
    }

    @Test
    void itShouldAddPatient() {
        underTest.addPatient(patient1);

        ArgumentCaptor<Patient> patientCaptor = ArgumentCaptor.forClass(Patient.class);
        // capturing the patient that was passed inside the save method in the repo
        verify(patientRepository).save(patientCaptor.capture());

        Patient capturedPatient = patientCaptor.getValue();

        assertThat(capturedPatient).isEqualTo(patient1);
    }

    @Test
    void itShouldFindPatientByEmail() {
        String email = patient1.getEmail();

        when(patientRepository.findPatientByEmail(email)).thenReturn(patient1);

        Patient foundPatient = underTest.findPatientByEmail(email);

        assertThat(foundPatient).isEqualTo(patient1);
        verify(patientRepository).findPatientByEmail(email);
    }

    @Test
    void itShouldNotFindPatientByEmail() {
        String email = "doesntExist@test,com";
        when(patientRepository.findPatientByEmail(email)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> underTest.findPatientByEmail(email));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getReason()).isEqualTo("Patient not found");
        verify(patientRepository).findPatientByEmail(email);
    }

    @Test
    void itShouldFindPatientsByFirstNameLastNameDateOfBirth() {
        String firstName = patient1.getFirstName();
        String lastName = patient1.getLastName();
        LocalDate dob = patient1.getDateOfBirth();

        when(patientRepository.findPatientsByFirstNameAndLastNameAndDateOfBirth(firstName, lastName, dob)).thenReturn(List.of(patient1));

        List<Patient> result = underTest.searchPatients(firstName, lastName, dob);
        assertThat(result.size()).isGreaterThan(0);

        Patient foundPatient = result.get(0);
        assertThat(foundPatient).isEqualTo(patient1);
        verify(patientRepository).findPatientsByFirstNameAndLastNameAndDateOfBirth(firstName, lastName, dob);
    }

    @Test
    void itShouldNotFindPatientsByFirstNameLastNameDateOfBirth() {
        String firstName = "Doesnt";
        String lastName = "Exist";
        LocalDate dob = LocalDate.of(2000, 1, 1);

        when(patientRepository.findPatientsByFirstNameAndLastNameAndDateOfBirth(firstName, lastName, dob)).thenReturn(List.of());

        List<Patient> result = underTest.searchPatients(firstName, lastName, dob);
        assertThat(result.size()).isEqualTo(0);
        verify(patientRepository).findPatientsByFirstNameAndLastNameAndDateOfBirth(firstName, lastName, dob);
    }

    @Test
    @Disabled
    void findByEmail() {
    }
}