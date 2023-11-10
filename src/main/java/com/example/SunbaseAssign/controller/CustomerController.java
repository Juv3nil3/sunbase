package com.example.SunbaseAssign.controller;

import com.example.SunbaseAssign.dto.request.CustomerRequest;
import com.example.SunbaseAssign.model.Customer;
import com.example.SunbaseAssign.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<String> createCustomer (@RequestBody CustomerRequest customerRequest,
                                                  @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String response = customerService.createCustomer(customerRequest, authorizationHeader);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create customer: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getList")
    public List<Customer> getCustomerList(@RequestHeader("Authorization")String accessToken) throws JsonProcessingException {

        // Call the service method to get the customer list
        return customerService.getCustomerList(accessToken);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@RequestHeader("Authorization") String authToken,
                                                 @PathVariable("id") String uuid) {
        ResponseEntity<String> response = customerService.deleteCustomer(authToken, uuid);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatusCodeValue());
        return new ResponseEntity<>(response.getBody(), httpStatus);
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<String> updateCustomer(@PathVariable("uuid") String uuid,
                                                 @RequestBody CustomerRequest customerRequest,
                                                 @RequestHeader("Authorization") String authToken) {
        ResponseEntity<String> response = customerService.updateCustomer(uuid, customerRequest, authToken);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatusCodeValue());
        return new ResponseEntity<>(response.getBody(), httpStatus);
    }

}
