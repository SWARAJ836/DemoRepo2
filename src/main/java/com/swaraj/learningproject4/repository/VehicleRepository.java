package com.swaraj.learningproject4.repository;

import com.swaraj.learningproject4.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
