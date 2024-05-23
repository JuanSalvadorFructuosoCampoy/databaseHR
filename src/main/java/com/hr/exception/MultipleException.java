package com.hr.exception;

import java.util.ArrayList;
import java.util.List;

public class MultipleException extends Exception {
    private final List<Exception> exceptions = new ArrayList<>();

    public void addException(Exception e) {
        exceptions.add(e);
    }

    public boolean hasExceptions() {
        return !exceptions.isEmpty();
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }
}
