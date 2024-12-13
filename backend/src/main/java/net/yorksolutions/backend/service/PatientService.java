package net.yorksolutions.backend.service;

import net.yorksolutions.backend.entity.Patient;
import net.yorksolutions.backend.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient findPatientByEmail(String email) {
        Optional<Patient> patientOptional = Optional.ofNullable(patientRepository.findPatientByEmail(email));
        System.out.println(patientOptional.isPresent());
//        return patientOptional.orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found with email: " + email));
        if (patientOptional.isEmpty()) {
            System.out.println("wtf");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");

        }
        System.out.println("wtf2");
        return patientOptional.get();
    }

    public List<Patient> searchPatients(String firstName, String lastName, LocalDate dateOfBirth) {
        return patientRepository.findPatientsByFirstNameAndLastNameAndDateOfBirth(firstName, lastName, dateOfBirth);
    }

    public Optional<Patient> findByEmail(String email) {
        return Optional.ofNullable(this.patientRepository.findPatientByEmail(email));
    }
}
