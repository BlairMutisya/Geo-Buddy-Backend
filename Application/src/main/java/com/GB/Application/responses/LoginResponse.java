package com.GB.Application.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String response;
    private String expiresIn;

    public LoginResponse(String response, String expiresIn) {
        this.response = response;
        this.expiresIn = expiresIn;
    }
}