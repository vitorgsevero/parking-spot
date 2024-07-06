package com.vitorgsevero.parkingspot.service;

import com.vitorgsevero.parkingspot.model.ParkingSpot;
import com.vitorgsevero.parkingspot.repository.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotService {

    final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional
    public ParkingSpot save(ParkingSpot parkingSpot) {
        return parkingSpotRepository.save(parkingSpot);
    }

    public boolean existsByLicensePlateCar(String vehicleLicensePlate) {
        return parkingSpotRepository.existsByVehicleLicensePlate(vehicleLicensePlate);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String apartmentBlock) {
        return parkingSpotRepository.existsByApartmentAndApartmentBlock(apartment, apartmentBlock);
    }

    public List<ParkingSpot> findAll(){
        return parkingSpotRepository.findAll();
    }

}