package net.yorksolutions.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.yorksolutions.backend.configuration.TestSecurityConfig;
import net.yorksolutions.backend.entity.Patient;
import net.yorksolutions.backend.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = PatientController.class)
@ExtendWith(MockitoExtension.class)
@Import(TestSecurityConfig.class)
class PatientControllerTest {

//    @Autowired
//    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

//    @InjectMocks
    private PatientController underTest;

    private Patient patient1;
    private Patient patient2;
//    @Autowired
//    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        underTest = new PatientController(patientService);

        patient1 = new Patient();
        patient1.setId(1L);
        patient1.setEmail("mdl@test.com");
        patient1.setFirstName("Luffy");
        patient1.setLastName("Monkey D.");
        patient1.setDateOfBirth(LocalDate.of(1990, 1, 1));

        patient2 = new Patient();
        patient2.setId(2L);
        patient2.setEmail("rz@test.com");
        patient2.setFirstName("Zoro");
        patient2.setLastName("Roronoa");
        patient2.setDateOfBirth(LocalDate.of(1995, 5, 5));
    }

    @Test
    void ShouldGetAllPatients() throws Exception {
        List<Patient> mockPatients = List.of(patient1, patient2);

        when(patientService.getAllPatients()).thenReturn(mockPatients);

//        String expectedResponseBody = objectMapper.writeValueAsString(mockPatients);

//        MockHttpServletResponse response = mockMvc.perform(
//                        get("/api/patients")
//                                .accept(MediaType.APPLICATION_JSON))
//                        .andReturn().getResponse();

        List<Patient> response = underTest.getAllPatients();

        assertThat(response.size()).isEqualTo(2);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentAsString()).isEqualTo(expectedResponseBody);
        verify(patientService).getAllPatients();
    }

    @Test
    @Disabled
    void findPatientByEmail() {
    }

    @Test
    @Disabled
    void searchPatients() {
    }

    @Test
    @Disabled
    void addPatient() {
    }
}