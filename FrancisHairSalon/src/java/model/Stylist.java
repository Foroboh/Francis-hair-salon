package com.francishairsalon.model;

public class Stylist {

    private int stylistId;
    private String firstName;
    private String lastName;
    private String specialty;
    private String phone;
    private boolean isActive;

    //No-argument constructor.
    public Stylist() {
    }

    // Getters
    public int getStylistId() {
        return stylistId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    //A helper method to glue both First and Last name together to display as Full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return isActive;
    }

    // Setters
    public void setStylistId(int stylistId) {
        this.stylistId = stylistId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    @Override
    public String toString() {
        return "Stylist{stylistId=" + stylistId
                + ", name='" + getFullName() + '\''
                + ", specialty='" + specialty + '\''
                + ", isActive=" + isActive
                + '}';
    }
}
