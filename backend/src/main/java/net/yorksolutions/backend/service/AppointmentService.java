package net.yorksolutions.backend.service;

import net.yorksolutions.backend.dto.AppointmentDTO;
import net.yorksolutions.backend.entity.Appointment;
import net.yorksolutions.backend.entity.Doctor;
import net.yorksolutions.backend.entity.Patient;
import net.yorksolutions.backend.repository.AppointmentRepository;
import net.yorksolutions.backend.repository.DoctorRepository;
import net.yorksolutions.backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public Appointment createAppointment(AppointmentDTO appointmentDTO) {

        Optional<Doctor> doctorOptional = this.doctorRepository.findById(appointmentDTO.getDoctorId());
        if(doctorOptional.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //find patient obj  by id
        Optional<Patient> patientOptional = this.patientRepository.findById(appointmentDTO.getPatientId());
        if(patientOptional.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND); // 404
        }

        if (conflictingAppointment(appointmentDTO, null)) {
            // 409
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An appointment with this doctor already exists for the given patient on the same day.");
        }

        //create appointment object with doc obj & patient obj
        Appointment appointment = new Appointment(
                patientOptional.get(),
                doctorOptional.get(),
                appointmentDTO.getAppointmentDateTime(),
                Appointment.VisitType.valueOf(appointmentDTO.getVisitType()));
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public Appointment updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        Optional<Appointment> appointmentOptional = this.appointmentRepository.findById(id);
        if(appointmentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found");
        }
        Appointment existingAppointment = appointmentOptional.get();

        Optional<Doctor> doctorOptional = this.doctorRepository.findById(appointmentDTO.getDoctorId());
        if(doctorOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (conflictingAppointment(appointmentDTO, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An appointment with this doctor already exists for the given patient on the same day.");
        }

        existingAppointment.setDoctor(doctorOptional.get());
        existingAppointment.setAppointmentDateTime(appointmentDTO.getAppointmentDateTime());
        existingAppointment.setVisitType(Appointment.VisitType.valueOf(appointmentDTO.getVisitType()));

        return appointmentRepository.save(existingAppointment);
    }

    public Appointment cancelAppointment(Long id) {
        Optional<Appointment> appointmentOptional = this.appointmentRepository.findById(id);
        if(appointmentOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Appointment existingAppointment = appointmentOptional.get();
        existingAppointment.setConfirmed(false);

        return appointmentRepository.save(existingAppointment);
    }

    // checks for conflicting appointments for when creating and updating appointments
    public boolean conflictingAppointment(AppointmentDTO appointmentDTO, Long appointmentId) {
        LocalDate appointmentDate = appointmentDTO.getAppointmentDateTime().toLocalDate();
        System.out.println(appointmentDTO.getAppointmentDateTime());
        List<Appointment> existingAppointments = appointmentRepository.findByPatientIdAndDoctorIdOnDate(
                appointmentDTO.getPatientId(),
                appointmentDTO.getDoctorId(),
                appointmentDate,
                appointmentId);

        for (Appointment existingAppointment : existingAppointments) {
            System.out.println(existingAppointment.getId());
        }

        return !existingAppointments.isEmpty();
    }
}
