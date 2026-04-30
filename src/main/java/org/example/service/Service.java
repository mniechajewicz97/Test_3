package org.example.service;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.example.model.Appointment;
import org.example.model.Doctor;
import org.example.model.Patient;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@ToString

public class Service {
    private final List<Doctor> doctors;
    private final List<Patient> patients;
    private final List<Appointment> appointments;

    public Map<Long, Doctor> doctorsData() {
        return doctors.stream()
                .collect(Collectors.toMap(
                        Doctor::getDoctorId,
                        doctor -> doctor
                ));
    }

    public Map<Long, Patient> patientsData() {
        return patients.stream()
                .collect(Collectors.toMap(
                        Patient::getPatientId,
                        patient -> patient
                ));
    }

    public Map<Long, Long> countAppointmentsByDoctor() {
        return appointments.stream()
                .collect(Collectors.groupingBy(Appointment::getDoctorId, Collectors.counting()));

    }

    public Doctor findDoctorWithMostAppointments() {
        Map<Long, Long> visitsByDoctor = countAppointmentsByDoctor();

        Map.Entry<Long, Long> maxEntry = visitsByDoctor.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalStateException("Cannot find doctor with most appointments because appointments list is empty"));

        Long doctorId = maxEntry.getKey();
        Doctor doctor = doctorsData().get(doctorId);

        if (doctor == null) {
            throw new IllegalStateException(
                    "Cannot find doctor with most appointments because doctor ID " + doctorId + " does not exist"
            );
        }

        return doctor;
    }

    public Map<Long, Long> countAppointmentsByPatient() {
        return appointments.stream()
                .collect(Collectors.groupingBy(Appointment::getPatientId, Collectors.counting()));
    }

    public Patient findPatientWithMostAppointments() {
        Map<Long, Long> visitsByPatient = countAppointmentsByPatient();

        Map.Entry<Long, Long> maxEntry = visitsByPatient.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new IllegalStateException("Cannot find patient with most appointments because appointments list is empty"));
        Long patientId = maxEntry.getKey();
        Patient patient = patientsData().get(patientId);

        if (patient == null) {
            throw new IllegalStateException(
                    "Cannot find patient with most appointments because patient ID " + patientId + " does not exist"
            );
        }
        return patientsData().get(patientId);
    }

    public String findMostPopularSpecialty() {
        Map<Long, Doctor> doctorsById = doctorsData();

        Map<String, Long> appointmentsBySpeciality = appointments.stream()
                .map(a->doctorsById.get(a.getDoctorId()))
                .filter(doctor -> doctor != null)
                .collect(Collectors.groupingBy(a -> doctorsById.get(a.getDoctorId()).getSpecialty(), Collectors.counting()));

        Map.Entry<String, Long> maxEntry = appointmentsBySpeciality.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(()-> new IllegalStateException("Cannot find most popular specialty because appointments list is empty"));

        return maxEntry.getKey();

    }

    public Map<Integer, Long> countAppointmentsByYear() {
        return appointments.stream()
                .filter(a -> a.getDateOfAppointment() != null)
                .collect(Collectors.groupingBy(a -> a.getDateOfAppointment().getYear(), Collectors.counting()));
    }

    public Integer findYearWithMostAppointments() {
        Map<Integer, Long> findYearWithMostAppointments = countAppointmentsByYear();

        Map.Entry<Integer, Long> maxEntry = findYearWithMostAppointments.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(()-> new IllegalStateException("Cannot find year with most appointments because no valid appointment dates were found"));
        return maxEntry.getKey();

    }

    public List<Doctor> theOldestDoctors() {
        return doctors.stream()
                .sorted(Comparator.comparing(Doctor::getDateOfBirth))
                .limit(5)
                .collect(Collectors.toList());

    }

    public Map<Long, Set<Long>> mapPatientToDistinctDoctors() {
        return appointments.stream()
                .collect(Collectors.groupingBy(Appointment::getPatientId, Collectors.mapping(Appointment::getDoctorId, Collectors.toSet())));
    }

    public List<Patient> findPatientsVisitedByAtLeast5Doctors() {
        return mapPatientToDistinctDoctors().entrySet().stream()
                .filter(v -> v.getValue().size() >= 5)
                .map(k -> patientsData().get(k.getKey()))
                .filter(patient -> patient != null)
                .collect(Collectors.toList());
    }

    public Map<Long, Set<Long>> mapDoctorToDistinctPatients() {
        return appointments.stream()
                .collect(Collectors.groupingBy(Appointment::getDoctorId, Collectors.mapping(Appointment::getPatientId, Collectors.toSet())));
    }

    public List<Doctor> findExclusiveDoctors() {
        return mapDoctorToDistinctPatients().entrySet().stream()
                .filter(v -> v.getValue().size() == 1)
                .map(k -> doctorsData().get(k.getKey()))
                .filter(doctor -> doctor != null)
                .collect(Collectors.toList());

    }
}

