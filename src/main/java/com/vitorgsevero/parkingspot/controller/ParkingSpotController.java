package com.vitorgsevero.parkingspot.controller;

import com.vitorgsevero.parkingspot.dto.ParkingSpotDTO;
import com.vitorgsevero.parkingspot.model.ParkingSpot;
import com.vitorgsevero.parkingspot.service.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSpotService service;

    public ParkingSpotController(ParkingSpotService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO){

        if(service.existsByLicensePlateCar(parkingSpotDTO.getVehicleLicensePlate())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use! Car parked.");
        }

        if(service.existsByParkingSpotNumber(parkingSpotDTO.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot is already in use! There is a car parked.");
        }

        if(service.existsByApartmentAndBlock(parkingSpotDTO.getApartment(), parkingSpotDTO.getApartmentBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot already registered for this apartment and block! You or someone parked using your permission.");
        }

        var parkingSpot = new ParkingSpot();
        BeanUtils.copyProperties(parkingSpotDTO, parkingSpot);
        parkingSpot.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(parkingSpot));
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpot>> getAllParkingSpots(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getParkingSpotById(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpot> parkingSpotOptional = service.findById(id);
        if(!parkingSpotOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ParkingSpot not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value="id") UUID id){
        Optional<ParkingSpot> parkingSpotOptional = service.findById(id);
        if(parkingSpotOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error while deleting. ParkingSpot not found.");
        }
        service.deleteParkingSpot(parkingSpotOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("ParkingSpot deleted successfully.");
    }

    @PutMapping("/{id}/deprecated")
    @Deprecated(since = "Deprecated method since July 6", forRemoval = true)
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value="id") UUID id,
                                               @RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        Optional<ParkingSpot> parkingSpotOptional = service.findById(id);
        if(parkingSpotOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ParkingSpot not found");
        }

        var parkingSpot = parkingSpotOptional.get();
        parkingSpot.setParkingSpotNumber(parkingSpotDTO.getParkingSpotNumber());
        parkingSpot.setVehicleLicensePlate(parkingSpotDTO.getVehicleLicensePlate());
        parkingSpot.setCarModel(parkingSpotDTO.getCarModel());
        parkingSpot.setCarBrand(parkingSpotDTO.getCarBrand());
        parkingSpot.setCarColor(parkingSpotDTO.getCarColor());
        parkingSpot.setResponsibleName(parkingSpotDTO.getResponsibleName());
        parkingSpot.setApartment(parkingSpotDTO.getApartment());
        parkingSpot.setApartmentBlock(parkingSpotDTO.getApartmentBlock());

        return ResponseEntity.status(HttpStatus.OK).body(service.save(parkingSpot));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpotV2(@PathVariable(value="id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDTO parkingSpotDTO) {
        Optional<ParkingSpot> parkingSpotOptional = service.findById(id);
        if(parkingSpotOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ParkingSpot not found!");
        }

        var parkingSpot = new ParkingSpot();
        BeanUtils.copyProperties(parkingSpotDTO, parkingSpot);
        //Set added in order to keep the original id and registration date
        parkingSpot.setId(parkingSpotOptional.get().getId());
        parkingSpot.setRegistrationDate(parkingSpotOptional.get().getRegistrationDate());

        return ResponseEntity.status(HttpStatus.OK).body(service.save(parkingSpot));

    }

}
