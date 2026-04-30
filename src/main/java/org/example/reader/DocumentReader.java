package org.example.reader;

import org.example.model.Appointment;
import org.example.model.Doctor;
import org.example.model.Patient;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class DocumentReader {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

    public List<Doctor> readDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        try (BufferedReader brDoctors = new BufferedReader(new FileReader("src/main/lekarze.txt")) ) {
        String line;
        brDoctors.readLine();
        while ((line = brDoctors.readLine()) != null) {
        String[] parts = line.trim().split("\t");
        long  doctorId = Long.parseLong(parts[0]);
        String doctorLastName = parts[1];
        String doctorFirstName = parts[2];
        String doctorSpeciality = parts[3];
        LocalDate doctorBirthDate = LocalDate.parse(parts[4]);
        String doctorNip =  parts[5];
        String doctorPesel = parts[6];
        Doctor doctor = new Doctor(doctorId, doctorLastName, doctorFirstName, doctorSpeciality, doctorBirthDate, doctorNip, doctorPesel);
        doctors.add(doctor);

        }


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Error");
        }
        return doctors;
    }
    public List<Patient> readPatients() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader brPatient = new BufferedReader(new FileReader("src/main/pacjenci.txt"))) {
        String line;
        brPatient.readLine();
        while ((line = brPatient.readLine()) != null) {
            String[] parts = line.trim().split("\t");
            long  patientId = Long.parseLong(parts[0]);
            String patientLastName = parts[1];
            String patientFirstName = parts[2];
            String patientPesel = parts[3];
            LocalDate patientBirthDate = LocalDate.parse(parts[4], formatter);
            Patient patient = new Patient(patientId, patientLastName, patientFirstName, patientPesel, patientBirthDate);
            patients.add(patient);
        }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Error");


        }
        return patients;
    }
    public List<Appointment> readAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try(BufferedReader brAppointment = new BufferedReader(new FileReader("src/main/wizyty.txt"))) {
            String line;
            brAppointment.readLine();
            while ((line = brAppointment.readLine()) != null) {
                String[] parts = line.trim().split("\t");
                long  doctorId = Long.parseLong(parts[0]);
                long patientId = Long.parseLong(parts[1]);
                LocalDate dateOfAppointment = LocalDate.parse(parts[2], formatter);
                Appointment appointment = new Appointment(doctorId, patientId, dateOfAppointment);
                appointments.add(appointment);
            }


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Error");
        }
        return appointments;
    }

}
