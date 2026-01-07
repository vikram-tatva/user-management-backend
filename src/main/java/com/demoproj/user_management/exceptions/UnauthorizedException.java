package com.demoproj.user_management.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(){
        super("Unauthorized");
    }
}
