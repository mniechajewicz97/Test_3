package org.example.model;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Appointment {
    private long doctorId;
    private long patientId;
    private LocalDate dateOfAppointment;
}
