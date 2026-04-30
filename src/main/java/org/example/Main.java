package org.example;

import org.example.model.Appointment;
import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.reader.DocumentReader;
import org.example.service.Service;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Zadanie 1\n");

        DocumentReader documentReader = new DocumentReader();

        List<Doctor> doctors = documentReader.readDoctors();
        List<Patient> patients = documentReader.readPatients();
        List<Appointment> appointments = documentReader.readAppointments();

        Service service = new Service(doctors, patients, appointments);

        System.out.println("1: znajdź lekarza ktory miał najwięcej wizyt");
        System.out.println(service.findDoctorWithMostAppointments());

        System.out.println("2: znajdź pacjenta który miał najwięcej wizyt");
        System.out.println(service.findPatientWithMostAppointments());

        System.out.println("3: która specalizacja cieszy się największym powodzeniem?");
        System.out.println(service.findMostPopularSpecialty());

        System.out.println("4: którego roku było najwięcej wizyt?");
        System.out.println(service.findYearWithMostAppointments());

        System.out.println("5: wypisz top 5 najstarszych lekarzy");
        System.out.println(service.theOldestDoctors());

        System.out.println("6: zwroc pacientow ktorzy byli u minumum 5ciu roznych lekarzy");
        System.out.println(service.findPatientsVisitedByAtLeast5Doctors());

        System.out.println("7: zwroc lekarzy exclusive - czyli takich ktorzy przyjmowali tylko jednego pacjenta");
        System.out.println(service.findExclusiveDoctors());


        System.out.println("Zadanie 2\n");

        System.out.println("Test 1: add / size / get / remove");

        StringContainer stringContainer = new StringContainer("\\d{2}[-]\\d{3}", true);

        stringContainer.add("11-222");
        stringContainer.add("11-223");
        stringContainer.add("11-243");

        System.out.println("Size after add:");
        System.out.println(stringContainer.size());

        System.out.println("Element index 1:");
        System.out.println(stringContainer.get(1));

        stringContainer.remove("11-222");

        System.out.println("Size after remove by value:");
        System.out.println(stringContainer.size());

        stringContainer.add("11-222");

// Oczekiwany wyjątek: DuplicatedElementOnListException
// stringContainer.add("11-243");

// Oczekiwany wyjątek: InvalidStringContainerValueException
// stringContainer.add("test byl trudny");

        stringContainer.remove(0);

        System.out.println("Size after remove by index:");
        System.out.println(stringContainer.size());


        System.out.println("\nTest 2: duplicates allowed");

        StringContainer containerWithDuplicates = new StringContainer("\\d{2}[-]\\d{3}");

        containerWithDuplicates.add("11-222");
        containerWithDuplicates.add("11-222");

        System.out.println("Size with duplicates:");
        System.out.println(containerWithDuplicates.size());


        System.out.println("\nTest 3: getDataBetween");

        StringContainer dateTestContainer = new StringContainer("\\d{2}[-]\\d{3}");

        dateTestContainer.add("10-000");

        Thread.sleep(10);
        LocalDateTime dateFrom = LocalDateTime.now();

        Thread.sleep(10);
        dateTestContainer.add("20-000");

        Thread.sleep(10);
        dateTestContainer.add("30-000");

        Thread.sleep(10);
        LocalDateTime dateTo = LocalDateTime.now();

        Thread.sleep(10);
        dateTestContainer.add("40-000");

        StringContainer result = dateTestContainer.getDataBetween(dateFrom, dateTo);

        System.out.println("Result size:");
        System.out.println(result.size());

        System.out.println("Result values:");

        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }


        System.out.println("\nTest 4: storeToFile / fromFile");

        StringContainer originalContainer = new StringContainer("\\d{2}[-]\\d{3}", true);

        originalContainer.add("11-111");
        originalContainer.add("22-222");
        originalContainer.add("33-333");

        System.out.println("Original container size:");
        System.out.println(originalContainer.size());

        System.out.println("Original container values:");

        for (int i = 0; i < originalContainer.size(); i++) {
            System.out.println(originalContainer.get(i));
        }

        originalContainer.storeToFile("postalCodes.txt");

        StringContainer loadedContainer = StringContainer.fromFile("postalCodes.txt");

        System.out.println("\nLoaded container size:");
        System.out.println(loadedContainer.size());

        System.out.println("Loaded container values:");

        for (int i = 0; i < loadedContainer.size(); i++) {
            System.out.println(loadedContainer.get(i));
        }


        System.out.println("\n Zadanie 3 \n");

        System.out.println("Dana jest tablica intow o rozmiarze 200 mln elementow, elementy maja losowe wartosci z pelnego zakresu inta");
        System.out.println("\n - policz ile czasu potrwa liczenie sumy wszystkich elementow uzywajac 1 watku");

        int[] array = ArraySumThreads.createArray();

        long startTime1 = System.nanoTime();
        long total1 = ArraySumThreads.sumArrayElements(array);
        long endTime1 = System.nanoTime();
        long time1 = endTime1 - startTime1;


        System.out.println("Sum array elements: " + total1);
        System.out.println("Time for one thread: " + time1 / 1_000_000 + " ms");

        System.out.println("\n - policz ile czasu potrwa liczenie sumy wszystkich elementow 2 watkow ");

        int half = array.length / 2;

        ArraySumWorker sum1 = new ArraySumWorker(array, 0, half);
        ArraySumWorker sum2 = new ArraySumWorker(array, half, array.length);

        Thread td1 = new Thread(sum1);
        Thread td2 = new Thread(sum2);

        long startTime2 = System.nanoTime();
        td1.start();
        td2.start();
        td1.join();
        td2.join();

        long total2 = sum1.getPartialSum() + sum2.getPartialSum();
        long endTime2 = System.nanoTime();
        long time2 = endTime2 - startTime2;
        System.out.println("Sum array elements: " + total2);
        System.out.println("Time for two threads: " + time2 / 1_000_000 + " ms");

        System.out.println("\n- policz ile czasu potrwa liczenie sumy wszystkich elementow 4 watkow");
        int quarter = array.length / 4;
        int half2 = quarter * 2;
        int threeQuarter = quarter * 3;

        ArraySumWorker s1 = new ArraySumWorker(array, 0, quarter);
        ArraySumWorker s2 = new ArraySumWorker(array, quarter, half2);
        ArraySumWorker s3 = new ArraySumWorker(array, half2, threeQuarter);
        ArraySumWorker s4 = new ArraySumWorker(array, threeQuarter, array.length);

        Thread t1 = new Thread(s1);
        Thread t2 = new Thread(s2);
        Thread t3 = new Thread(s3);
        Thread t4 = new Thread(s4);
        long startTime3 = System.nanoTime();
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        long endTime3 = System.nanoTime();
        long total3 = s1.getPartialSum() + s2.getPartialSum() + s3.getPartialSum() + s4.getPartialSum();
        long time3 = endTime3 - startTime3;
        System.out.println("Sum array elements: " + total3);
        System.out.println("Time for four threads: " + time3 / 1_000_000 + " ms");

        System.out.println("\n- policz ile czasu potrwa liczenie sumy wszystkich elementow 8 watkow");

        int oneEighth = array.length / 8;
        int twoEighths = oneEighth * 2;
        int threeEighths = oneEighth * 3;
        int fourEighths = oneEighth * 4;
        int fiveEighths = oneEighth * 5;
        int sixEighths = oneEighth * 6;
        int sevenEighths = oneEighth * 7;

        ArraySumWorker w1 = new ArraySumWorker(array, 0, oneEighth);
        ArraySumWorker w2 = new ArraySumWorker(array, oneEighth, twoEighths);
        ArraySumWorker w3 = new ArraySumWorker(array, twoEighths, threeEighths);
        ArraySumWorker w4 = new ArraySumWorker(array, threeEighths, fourEighths);
        ArraySumWorker w5 = new ArraySumWorker(array, fourEighths, fiveEighths);
        ArraySumWorker w6 = new ArraySumWorker(array, fiveEighths, sixEighths);
        ArraySumWorker w7 = new ArraySumWorker(array, sixEighths, sevenEighths);
        ArraySumWorker w8 = new ArraySumWorker(array, sevenEighths, array.length);

        Thread tr1 = new Thread(w1);
        Thread tr2 = new Thread(w2);
        Thread tr3 = new Thread(w3);
        Thread tr4 = new Thread(w4);
        Thread tr5 = new Thread(w5);
        Thread tr6 = new Thread(w6);
        Thread tr7 = new Thread(w7);
        Thread tr8 = new Thread(w8);

        long startTime4 = System.nanoTime();

        tr1.start();
        tr2.start();
        tr3.start();
        tr4.start();
        tr5.start();
        tr6.start();
        tr7.start();
        tr8.start();

        tr1.join();
        tr2.join();
        tr3.join();
        tr4.join();
        tr5.join();
        tr6.join();
        tr7.join();
        tr8.join();

        long endTime4 = System.nanoTime();
        long total4 = w1.getPartialSum() + w2.getPartialSum() + w3.getPartialSum() + w4.getPartialSum() + w5.getPartialSum() + w6.getPartialSum() + w7.getPartialSum() + w8.getPartialSum();
        long time4 = endTime4 - startTime4;
        System.out.println("Sum array elements: " + total4);
        System.out.println("Time for eight threads: " + time4 / 1_000_000 + " ms");


    }
}