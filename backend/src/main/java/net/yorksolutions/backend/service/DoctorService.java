package net.yorksolutions.backend.service;

import net.yorksolutions.backend.dto.DoctorDTO;
import net.yorksolutions.backend.entity.Appointment;
import net.yorksolutions.backend.entity.Doctor;
import net.yorksolutions.backend.entity.Specialization;
import net.yorksolutions.backend.repository.AppointmentRepository;
import net.yorksolutions.backend.repository.DoctorRepository;
import net.yorksolutions.backend.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository,
                         SpecializationRepository specializationRepository,
                         AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.specializationRepository = specializationRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<Doctor> getAllDoctors() {
        return this.doctorRepository.findAll();
    }

    public List<Doctor> getDoctorsBySpecialization(Long specializationId) {
        return this.doctorRepository.findBySpecializationId(specializationId);
    }

    public Doctor addDoctor(DoctorDTO doctorDTO) {
        Optional<Specialization> specializationOptional = specializationRepository.findById(doctorDTO.getSpecializationId());
        if (specializationOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialization Not Found");
        }

        Doctor doctor = new Doctor(
                doctorDTO.getFirstName(),
                doctorDTO.getLastName(),
                specializationOptional.get()
        );

        return this.doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(id);
        this.appointmentRepository.deleteAll(appointments);
        this.doctorRepository.deleteById(id);
    }

    public Doctor updateDoctor(Long id, DoctorDTO doctorDTO) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        if (doctorOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor Not Found");
        }

        Optional<Specialization> specializationOptional = specializationRepository.findById(doctorDTO.getSpecializationId());
        if (specializationOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialization Not Found");
        }

        Doctor existingDoctor = doctorOptional.get();
        existingDoctor.setFirstName(doctorDTO.getFirstName());
        existingDoctor.setLastName(doctorDTO.getLastName());
        existingDoctor.setSpecialization(specializationOptional.get());
        return this.doctorRepository.save(existingDoctor);
    }
}
