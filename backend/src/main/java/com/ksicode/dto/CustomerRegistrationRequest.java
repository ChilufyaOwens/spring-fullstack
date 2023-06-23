package com.ksicode.dto;

import lombok.*;


@Builder
public record CustomerRegistrationRequest(String name, String email, Integer age) {

}
