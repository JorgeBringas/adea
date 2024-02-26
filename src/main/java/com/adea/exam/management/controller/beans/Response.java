package com.adea.exam.management.controller.beans;

import lombok.Getter;

@Getter
public class Response {
    private int error;
    private String message;

    public void setError() {
        this.error = 1;
    }

    public void setErrorMessage(String message) {
        this.error = 2;
        this.message = message;
    }

    public boolean isError() {
        return error != 0;
    }
}
