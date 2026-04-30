package org.example;
public class InvalidStringContainerPatternException extends RuntimeException {

    public InvalidStringContainerPatternException(String communicate) {
        super(communicate);
    }
}