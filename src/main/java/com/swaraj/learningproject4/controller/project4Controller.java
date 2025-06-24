package com.swaraj.learningproject4.controller;

import com.swaraj.learningproject4.entity.ParkingSpot;
import com.swaraj.learningproject4.entity.ParkingTicket;
import com.swaraj.learningproject4.entity.Vehicle;
import com.swaraj.learningproject4.entity.student;
import com.swaraj.learningproject4.enums.SpotType;
import com.swaraj.learningproject4.repository.StudentRepo;
import com.swaraj.learningproject4.service.ParkingService;
import com.swaraj.learningproject4.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking")
public class project4Controller {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private StudentService studentService;

    @GetMapping("users")
    public String Message2()
    {
        return "Swaraj 2nd API  Resource name users2";
    }

    @GetMapping("/students")
    public List<student> getAllStudents(){

        return studentService.getAllStudents() ;
    }
    // ------------------ CODE for parking -------------------------

    // Controller for ADDING Single Parking Spot
    @PostMapping("/addSpot")
    public ResponseEntity<ParkingSpot> addSpot(@RequestBody ParkingSpot spot){
        return ResponseEntity.ok(parkingService.addSpot(spot));
    }

    // Controller for ADDING Bulk Parking Spot
    @PostMapping("/addSpots")
    public ResponseEntity<List<ParkingSpot>> addSpots(@RequestBody List<ParkingSpot> spots){
        return ResponseEntity.ok(parkingService.addSpotsBulk(spots));
    }

    // Controller for Fetching all Available Parking Spots
    @GetMapping("/availableSpots")
    public ResponseEntity<Map<SpotType,Long>> availability(){
        return ResponseEntity.ok(parkingService.getAvailability());
    }

    // Controller for Vehicle Check In
    @PostMapping("/checkIn")
    public ResponseEntity<ParkingTicket> checkIn(@RequestBody Vehicle vehicle){
        return ResponseEntity.ok(parkingService.checkIn(vehicle));
    }

    // Controller for Vehicle Check Out
    @PostMapping("/checkOut/{ticketId}")
    public ResponseEntity<ParkingTicket> checkOut(@PathVariable String ticketId){
        return ResponseEntity.ok(parkingService.checkOut(ticketId));
    }













}
