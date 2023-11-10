package com.example.SunbaseAssign.model;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    String uuid;

    String first_name;

    String last_name;

    String street;

    String address;

    String city;

    String state;

    String email;

    String phone;

}
