package com.example.SunbaseAssign.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {
    @NotBlank
    String first_name;
    @NotBlank
    String last_name;

    String street;

    String address;

    String city;

    String state;

    String email;

    String phone;
}
