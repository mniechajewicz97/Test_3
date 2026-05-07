package org.example.model;

import java.time.LocalDate;


public class Doctor {
    private long doctorId;
    private String lastName;
    private String firstName;
    private String specialty;
    private LocalDate dateOfBirth;
    private String nip;
    private String pesel;

    public Doctor(long doctorId, String lastName, String firstName, String specialty, LocalDate dateOfBirth, String nip, String pesel) {
        this.doctorId = doctorId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.specialty = specialty;
        this.dateOfBirth = dateOfBirth;
        this.nip = nip;
        this.pesel = pesel;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return "(ID:" + doctorId + ") Doctor "  + firstName + " " + lastName + ", speciality: " + specialty;

    }
}
