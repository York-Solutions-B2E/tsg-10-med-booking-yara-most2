package net.yorksolutions.backend.controller;

import net.yorksolutions.backend.dto.DoctorDTO;
import net.yorksolutions.backend.entity.Doctor;
import net.yorksolutions.backend.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return this.doctorService.getAllDoctors();
    }

    //    @PermitAll
    @GetMapping("/s")
    public List<Doctor> getDoctorsBySpecializationId(@RequestParam Long id) {
        return this.doctorService.getDoctorsBySpecialization(id);
    }

    @PostMapping("/create")
    public Doctor addDoctor(@RequestBody DoctorDTO doctorDTO) {
        return this.doctorService.addDoctor(doctorDTO);
    }

    @PutMapping("edit/{id}")
    Doctor updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        return this.doctorService.updateDoctor(id, doctorDTO);
    }

    @DeleteMapping("/del/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        this.doctorService.deleteDoctor(id);
    }
}
