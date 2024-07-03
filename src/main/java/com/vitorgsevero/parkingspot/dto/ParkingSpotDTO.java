package com.vitorgsevero.parkingspot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ParkingSpotDTO {

    @NotBlank
    private String parkingSpotNumber;

    @NotBlank
    @Size(max = 7)
    private String vehicleLicensePlate;

    @NotBlank
    private String carBrand;

    @NotBlank
    private String carModel;

    @NotBlank
    private String carColor;

    @NotBlank
    private String responsibleName;

    @NotBlank
    private String apartment;

    @NotBlank
    private String apartmentBlock;

    public @NotBlank String getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(@NotBlank String parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public @NotBlank @Size(max = 7) String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    public void setVehicleLicensePlate(@NotBlank @Size(max = 7) String vehicleLicensePlate) {
        this.vehicleLicensePlate = vehicleLicensePlate;
    }

    public @NotBlank String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(@NotBlank String carBrand) {
        this.carBrand = carBrand;
    }

    public @NotBlank String getCarModel() {
        return carModel;
    }

    public void setCarModel(@NotBlank String carModel) {
        this.carModel = carModel;
    }

    public @NotBlank String getCarColor() {
        return carColor;
    }

    public void setCarColor(@NotBlank String carColor) {
        this.carColor = carColor;
    }

    public @NotBlank String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(@NotBlank String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public @NotBlank String getApartment() {
        return apartment;
    }

    public void setApartment(@NotBlank String apartment) {
        this.apartment = apartment;
    }

    public @NotBlank String getApartmentBlock() {
        return apartmentBlock;
    }

    public void setApartmentBlock(@NotBlank String apartmentBlock) {
        this.apartmentBlock = apartmentBlock;
    }
}
