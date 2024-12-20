package net.yorksolutions.backend.repository;

import net.yorksolutions.backend.entity.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository underTest;

    private Patient patient1;

    @BeforeEach
    void setUp() {
        // Create Patient
        patient1 = new Patient();
        patient1.setEmail("mdl@test.com");
        patient1.setFirstName("Luffy");
        patient1.setLastName("Monkey D.");
        patient1.setDateOfBirth(LocalDate.of(1990, 1, 1));
        underTest.save(patient1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findPatientsByFirstNameAndLastNameAndDateOfBirth() {
        List<Patient> result = underTest.findPatientsByFirstNameAndLastNameAndDateOfBirth(
                patient1.getFirstName(),
                patient1.getLastName(),
                patient1.getDateOfBirth()
        );

        assertNotNull(result);
        assertEquals(1, result.size());

        Patient found = result.get(0);

        assertEquals(patient1.getFirstName(), found.getFirstName());
        assertEquals(patient1.getLastName(), found.getLastName());
        assertEquals(patient1.getDateOfBirth(), found.getDateOfBirth());
    }

    @Test
    void findPatientByEmail() {
        Patient result = underTest.findPatientByEmail(patient1.getEmail());

        assertEquals(patient1.getEmail(), result.getEmail());
    }
}