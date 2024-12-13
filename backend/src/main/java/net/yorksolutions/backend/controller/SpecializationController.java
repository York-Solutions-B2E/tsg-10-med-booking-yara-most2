package net.yorksolutions.backend.controller;

import net.yorksolutions.backend.entity.Specialization;
import net.yorksolutions.backend.service.SpecializationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/specializations")
@CrossOrigin
public class SpecializationController {

    private SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @GetMapping()
    public List<Specialization> getSpecializations() {
        return this.specializationService.getAllSpecializations();
    }
}
