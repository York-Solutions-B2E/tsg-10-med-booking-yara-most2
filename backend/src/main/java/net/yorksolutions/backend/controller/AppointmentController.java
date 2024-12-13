package net.yorksolutions.backend.controller;

import net.yorksolutions.backend.dto.AppointmentDTO;
import net.yorksolutions.backend.entity.Appointment;
import net.yorksolutions.backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    @GetMapping
    public List<Appointment> getAppointments() {

        return this.appointmentService.getAppointments();
    }

    @GetMapping("/patient") //api/appointments/patient?id=5
    public List<Appointment> getAppointmentsByPatientId(@RequestParam Long id) {
        return this.appointmentService.getAppointmentsByPatientId(id);
    }

    @GetMapping("/doctor")
    public List<Appointment> getAppointmentsByDoctorId(@RequestParam Long id) {
        return this.appointmentService.getAppointmentsByDoctorId(id);
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return this.appointmentService.createAppointment(appointmentDTO);
    }

    @PutMapping("/edit/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO) {
        return this.appointmentService.updateAppointment(id, appointmentDTO);
    }

    @PutMapping("/cancel/{id}")
    public Appointment cancelAppointment(@PathVariable Long id) {
        return this.appointmentService.cancelAppointment(id);
    }
}
