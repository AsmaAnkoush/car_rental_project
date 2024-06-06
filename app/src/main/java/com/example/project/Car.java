package com.example.project;

public class Car {
    private int carId;
    private int carNumber;
    private String make;
    private String model;
    private int year;
    private String color;
    private String description;
    private double rentalPricePerDay;
    private String availabilityStatus;
    private String imageUrl;



    // جير ظ اوتومات او كهرباء
    private String transmissionTypes;
    private String usingTypes;
    private int numOfPassengers;

    public Car() {}


    public Car(int carId, int carNumber, String make, String model, int year, String color, String description,
               double rentalPricePerDay, String availabilityStatus, String imageUrl, String transmissionTypes,
               int numOfPassengers ,String usingTypes) {
        this.carId = carId;
        this.carNumber = carNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.description = description;
        this.rentalPricePerDay = rentalPricePerDay;
        this.availabilityStatus = availabilityStatus;
        this.imageUrl = imageUrl;
        this.transmissionTypes = transmissionTypes;
        this.numOfPassengers = numOfPassengers;
        this.usingTypes = usingTypes;
    }
//    public static CarsCards[] carsArray = {
//            new CarsCards(1, 1001, "Tesla", "Model S", 2022, "Red", "Electric sedan", 199.99, "Available", "R.drawable.download.jpg", "Automatic", 5, "Personal"),
//            new CarsCards(2, 1002, "BMW", "X5", 2021, "Black", "Luxury SUV", 149.99, "Available", "R.drawable.download.jpg", "Automatic", 5, "Personal"),
//            new CarsCards(3, 1003, "Audi", "A4", 2020, "White", "Compact executive car", 129.99, "Rented", "R.drawable.download.jpg", "Automatic", 5, "Business"),
//            new CarsCards(4, 1004, "Ford", "Mustang", 2019, "Blue", "Sports car", 159.99, "Available", "R.drawable.download.jpg", "Manual", 4, "Personal")
//    };


    // Getters and setters
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public void setRentalPricePerDay(double rentalPricePerDay) {
        this.rentalPricePerDay = rentalPricePerDay;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTransmissionTypes() {
        return transmissionTypes;
    }

    public void setTransmissionTypes(String transmissionTypes) {
        this.transmissionTypes = transmissionTypes;
    }

    public int getNumOfPassengers() {
        return numOfPassengers;
    }

    public void setNumOfPassengers(int numOfPassengers) {
        this.numOfPassengers = numOfPassengers;
    }



    public String getUsingTypes() {
        return usingTypes;
    }

    public void setUsingTypes(String usingTypes) {
        this.usingTypes = usingTypes;
    }

}
