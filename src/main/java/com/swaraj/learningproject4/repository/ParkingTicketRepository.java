package com.swaraj.learningproject4.repository;

import com.swaraj.learningproject4.entity.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, String> {
}
