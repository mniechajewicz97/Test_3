package org.example.model;

import java.time.LocalDate;


public class Patient {
    private long patientId;
    private String lastName;
    private String firstName;
    private String pesel;
    private LocalDate dateOfBirth;

    public Patient(long patientId, String lastName, String firstName, String pesel, LocalDate dateOfBirth) {
        this.patientId = patientId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.pesel = pesel;
        this.dateOfBirth = dateOfBirth;
    }

    public long getPatientId() {
        return patientId;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
