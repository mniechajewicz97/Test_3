package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringContainer {
    private Pattern pattern;
    private boolean duplicatedNotAllowed;
    private StringElement firstElement;
    private int size;

    public StringContainer(String pattern, boolean duplicatedNotAllowed) {
        try {
            this.pattern = Pattern.compile(pattern);
        } catch (PatternSyntaxException e) {
            throw new org.example.InvalidStringContainerPatternException(pattern);
        }
        this.duplicatedNotAllowed = duplicatedNotAllowed;
    }

    public StringContainer(String pattern) {
        this(pattern, false);
    }

    public StringContainer getDataBetween(LocalDateTime dateFrom, LocalDateTime dateTo) {
        StringContainer result = new StringContainer(pattern.pattern(), duplicatedNotAllowed);
        StringElement actualElement = firstElement;

        while (actualElement != null) {
            LocalDateTime addedDate = actualElement.dateOfAdd;

            boolean afterFrom = dateFrom == null || !addedDate.isBefore(dateFrom);
            boolean beforeTo = dateTo == null || !addedDate.isAfter(dateTo);

            if (afterFrom && beforeTo) {
                result.addWithDate(actualElement.element, actualElement.dateOfAdd);
            }

            actualElement = actualElement.nextElement;
        }

        return result;
    }

    public void storeToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(pattern.pattern());
            writer.newLine();
            writer.write(String.valueOf(duplicatedNotAllowed));
            writer.newLine();
            StringElement actualElement = firstElement;

            while (actualElement != null) {
                writer.write(actualElement.element);
                writer.newLine();

                writer.write(actualElement.dateOfAdd.toString());
                writer.newLine();

                actualElement = actualElement.nextElement;
            }

        } catch (IOException e) {
            throw new RuntimeException("File read error", e);
        }
    }

    public static StringContainer fromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String patternFromFile = reader.readLine();
            String duplicatedLine = reader.readLine();

            boolean duplicatedFromFile = Boolean.parseBoolean(duplicatedLine);
            StringContainer sc = new StringContainer(patternFromFile, duplicatedFromFile);

            String valueLine;

            while ((valueLine = reader.readLine()) != null) {
                String dateLine = reader.readLine();
                LocalDateTime dateOfAdd = LocalDateTime.parse(dateLine);

                sc.addWithDate(valueLine, dateOfAdd);
            }

            return sc;

        } catch (IOException e) {
            throw new RuntimeException("File read error", e);
        }
    }

    public int size() {
        return size;
    }

    public void add(String value) {
        if (!pattern.matcher(value).matches()) {
            throw new org.example.InvalidStringContainerValueException(value);
        }

        if (duplicatedNotAllowed) {
            StringElement actualElement = firstElement;

            while (actualElement != null) {
                if (actualElement.element.equals(value)) {
                    throw new org.example.DuplicatedElementOnListException(value);
                }

                actualElement = actualElement.nextElement;
            }
        }

        if (firstElement == null) {
            firstElement = new StringElement(value, LocalDateTime.now(), null);
            size++;
        } else {
            StringElement actualElement = firstElement;

            while (actualElement.nextElement != null) {
                actualElement = actualElement.nextElement;
            }

            actualElement.nextElement = new StringElement(value, LocalDateTime.now(), null);
            size++;
        }
    }

    private void addWithDate(String value, LocalDateTime dateOfAdd) {
        if (firstElement == null) {
            firstElement = new StringElement(value, dateOfAdd, null);
            size++;
        } else {
            StringElement actualElement = firstElement;

            while (actualElement.nextElement != null) {
                actualElement = actualElement.nextElement;
            }

            actualElement.nextElement = new StringElement(value, dateOfAdd, null);
            size++;
        }
    }

    public String get(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        StringElement actualElement = firstElement;

        for (int i = 0; i < index; i++) {
            actualElement = actualElement.nextElement;

        }
        return actualElement.element;

    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (index == 0) {
            firstElement = firstElement.nextElement;
            size--;
        } else {
            StringElement actualElement = firstElement;
            for (int i = 0; i < index - 1; i++) {
                actualElement = actualElement.nextElement;

            }
            actualElement.nextElement = actualElement.nextElement.nextElement;
            size--;
        }

    }

    public void remove(String value) {
        if (!pattern.matcher(value).matches()) {
            throw new org.example.InvalidStringContainerValueException(value);
        }

        if (firstElement == null) {
            return;
        }
        if (firstElement.element.equals(value)) {
            firstElement = firstElement.nextElement;
            size--;
            return;
        }
        StringElement actualElement = firstElement;

        while (actualElement.nextElement != null) {
            if (actualElement.nextElement.element.equals(value)) {
                actualElement.nextElement = actualElement.nextElement.nextElement;
                size--;
                return;
            }
            actualElement = actualElement.nextElement;
        }
    }

    private class StringElement {
        private String element;
        private LocalDateTime dateOfAdd;
        private StringElement nextElement;

        public StringElement(String element, LocalDateTime dateOfAdd, StringElement nextElement) {
            this.element = element;
            this.dateOfAdd = dateOfAdd;
            this.nextElement = nextElement;
        }


    }

}

