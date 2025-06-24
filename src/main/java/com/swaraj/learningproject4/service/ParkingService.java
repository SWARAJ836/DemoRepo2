package com.swaraj.learningproject4.service;

import com.swaraj.learningproject4.entity.ParkingSpot;
import com.swaraj.learningproject4.entity.ParkingTicket;
import com.swaraj.learningproject4.entity.Vehicle;
import com.swaraj.learningproject4.enums.SpotType;
import com.swaraj.learningproject4.enums.VehicleType;
import com.swaraj.learningproject4.repository.ParkingSpotRepository;
import com.swaraj.learningproject4.repository.ParkingTicketRepository;
import com.swaraj.learningproject4.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    @Autowired
    private ParkingSpotRepository pSpotRepo;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingTicketRepository pTicketRepo;

    private Map<VehicleType, SpotType> mapTypeToSpot = Map.of(
                                        VehicleType.MOTORCYCLE, SpotType.SMALL,
                                        VehicleType.CAR,        SpotType.MEDIUM,
                                        VehicleType.BUS,        SpotType.LARGE
            );

    private Map<VehicleType, BigDecimal> mapRate = Map.of(
                VehicleType.MOTORCYCLE, BigDecimal.valueOf(10),
                VehicleType.CAR,        BigDecimal.valueOf(20),
                VehicleType.BUS,        BigDecimal.valueOf(50)
            );

    // Service for ADDING Single Parking Spot
    @Transactional
    public ParkingSpot addSpot(ParkingSpot spot){
        return pSpotRepo.save(spot);
    }

    // Service for ADDING Bulk Parking Spot
    @Transactional
    public List<ParkingSpot> addSpotsBulk(List<ParkingSpot> spots){
        return pSpotRepo.saveAll(spots);
    }

    public Map<SpotType, Long> getAvailability() {

        return pSpotRepo.findAll().stream()
                .filter(s-> !s.isOccupied())
                .collect(Collectors.groupingBy(ParkingSpot::getSpotType, Collectors.counting()));
    }

    // Service for Vehicle Check In
    @Transactional
    public ParkingTicket checkIn(Vehicle vehicle) {

        vehicleRepository.save(vehicle);
        // To find the required spot type for the type of vehicle
        SpotType required = mapTypeToSpot.get(vehicle.getType());

        //To find the available spots
        List<ParkingSpot> spots = pSpotRepo.findAvailableSpots(required);
        // exception handling for no empty spots
        if (spots.isEmpty()) throw new RuntimeException("NO SPOTS AVAILABLE CURRENTLY");


        ParkingSpot spot = spots.get(0);//selecting the first spot from the List of available spots
        spot.setOccupied(true);         //setting the occupied field as true
        pSpotRepo.save(spot);           //saving the spot to repo

        // Assigning Parking Ticket
        ParkingTicket ticket = new ParkingTicket();
            ticket.setTicketId(UUID.randomUUID().toString());
            ticket.setEntryTime(LocalDateTime.now());
            ticket.setVehicle(vehicle);
            ticket.setSpot(spot);

        return pTicketRepo.save(ticket);

    }

    // Service for Vehicle Check Out
    @Transactional
    public ParkingTicket checkOut(String ticketId) {

        ParkingTicket ticket = pTicketRepo.findById(Long.valueOf(ticketId)).orElseThrow();
        ticket.setExitTime(LocalDateTime.now());

        long hours = Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toHours();
        hours = (hours ==0) ? 1 : hours;

        BigDecimal rate = mapRate.get(ticket.getVehicle().getType());

        ticket.setFee(rate.multiply(BigDecimal.valueOf(hours)));

        ParkingSpot spot = ticket.getSpot();
        spot.setOccupied(false);
        pSpotRepo.save(spot);

        return pTicketRepo.save(ticket);

    }
}






















