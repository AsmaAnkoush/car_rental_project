package com.example.project;

import java.util.Date;

public class CarMaintenance {
    private int maintenanceId;
    private int carId;
    private Date maintenanceDate;
    private String details;
    private double cost;

    // Foreign key relationships
    private Car car;

    // Default constructor
    public CarMaintenance() {}

    // Parameterized constructor
    public CarMaintenance(int maintenanceId, int carId, Date maintenanceDate, String details, double cost) {
        this.maintenanceId = maintenanceId;
        this.carId = carId;
        this.maintenanceDate = maintenanceDate;
        this.details = details;
        this.cost = cost;
    }

    // Getters and setters
    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Date getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(Date maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "CarMaintenance{" +
                "maintenanceId=" + maintenanceId +
                ", carId=" + carId +
                ", maintenanceDate=" + maintenanceDate +
                ", details='" + details + '\'' +
                ", cost=" + cost +
                ", car=" + car +
                '}';
    }
}


