package com.francishairsalon.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Appointment {

    //Private fields used to hold the data retrieved from the Appointments table.
    private int appointmentId;
    private int userId;
    private int stylistId;
    private int serviceId;
    private Date appointmentDate;
    private Time appointmentTime;
    private String status;          //"confirmed", "cancelled", "completed"
    private String notes;
    private Timestamp createdAt;

    //Additional fields used to hold names and details pulled from other tables during the SQL JOIN
    private String customerName;
    private String stylistName;
    private String serviceName;
    private double servicePrice;

    //No-argument constructor.
    public Appointment() {
    }

    // Getters
    public int getAppointmentId() {
        return appointmentId;
    }

    public int getUserId() {
        return userId;
    }

    public int getStylistId() {
        return stylistId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public Time getAppointmentTime() {
        return appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getStylistName() {
        return stylistName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    // Setters
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStylistId(int stylistId) {
        this.stylistId = stylistId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    @Override
    public String toString() {
        return "Appointment{appointmentId=" + appointmentId
                + ", userId=" + userId
                + ", stylistId=" + stylistId
                + ", serviceId=" + serviceId
                + ", appointmentDate=" + appointmentDate
                + ", appointmentTime=" + appointmentTime
                + ", status='" + status + '\''
                + '}';
    }
}
