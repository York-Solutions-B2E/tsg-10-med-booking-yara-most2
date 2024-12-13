package net.yorksolutions.backend.repository;

import net.yorksolutions.backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecializationId(Long specializationId);
}
