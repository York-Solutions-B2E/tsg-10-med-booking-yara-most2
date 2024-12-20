package net.yorksolutions.backend.controller;

import net.yorksolutions.backend.entity.Patient;
import net.yorksolutions.backend.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return this.patientService.getAllPatients();
    }

    @GetMapping("/e") // api/patients/e?email=blabla@ksdf.hgt
    public Patient findPatientByEmail(@RequestParam String email) {
        return this.patientService.findPatientByEmail(email);
    }

    @GetMapping("/search")
    public List<Patient> searchPatients(@RequestParam String firstName,
                                        @RequestParam String lastName,
                                        @RequestParam LocalDate dateOfBirth) {
        return this.patientService.searchPatients(firstName, lastName, dateOfBirth);
    }


    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
//        System.out.println("request received: " + patient);
        return patientService.addPatient(patient);
    }

}
