package org.example.model;

import java.time.LocalDate;



public class Appointment {
    private long doctorId;
    private long patientId;
    private LocalDate dateOfAppointment;

    public Appointment(long doctorId, long patientId, LocalDate dateOfAppointment) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.dateOfAppointment = dateOfAppointment;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public long getPatientId() {
        return patientId;
    }

    public LocalDate getDateOfAppointment() {
        return dateOfAppointment;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", dateOfAppointment=" + dateOfAppointment +
                '}';
    }
}
