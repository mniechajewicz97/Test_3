package org.example.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Patient {
    private long patientId;
    private String lastName;
    private String firstName;
    private String pesel;
    private LocalDate dateOfBirth;
}
