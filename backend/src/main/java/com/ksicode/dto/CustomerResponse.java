package com.ksicode.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private Integer age;
}
