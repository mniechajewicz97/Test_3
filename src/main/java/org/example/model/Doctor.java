package org.example.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Doctor {
    private long doctorId;
    private String lastName;
    private String firstName;
    private String specialty;
    private LocalDate dateOfBirth;
    private String nip;
    private String pesel;

}
