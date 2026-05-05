package com.universite.absences.exception;

public class SalleCapacityExceededException extends RuntimeException {
    public SalleCapacityExceededException(String message) {
        super(message);
    }
}
