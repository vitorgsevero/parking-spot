package com.vitorgsevero.parkingspot.repository;

import com.vitorgsevero.parkingspot.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, UUID> {

}
