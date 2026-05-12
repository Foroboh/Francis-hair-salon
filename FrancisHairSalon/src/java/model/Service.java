package com.francishairsalon.model;

public class Service {

    private int serviceId;
    private String serviceName;
    private String description;
    private int durationMin;   // duration in minutes
    private double price;
    private boolean isActive;

    //No-argument constructor.
    public Service() {
    }

    // Getters
    public int getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDescription() {
        return description;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public double getPrice() {
        return price;
    }

    public boolean isActive() {
        return isActive;
    }

    // Setters
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    @Override
    public String toString() {
        return "Service{serviceId=" + serviceId
                + ", serviceName='" + serviceName + '\''
                + ", price=" + price
                + ", durationMin=" + durationMin
                + ", isActive=" + isActive
                + '}';
    }
}
