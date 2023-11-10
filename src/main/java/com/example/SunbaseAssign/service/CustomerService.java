package com.example.SunbaseAssign.service;

import com.example.SunbaseAssign.dto.request.CustomerRequest;
import com.example.SunbaseAssign.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class CustomerService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";

    public String createCustomer(CustomerRequest customerRequest, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);


        // Build URL with query parameter cmd=create
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("cmd", "create");

        HttpEntity<CustomerRequest> entity = new HttpEntity<>(customerRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return "Customer created successfully";
        } else {
            throw new RuntimeException("Failed to create customer: " + response.getBody());
        }
    }

    public List<Customer> getCustomerList(String authToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);

        // Build URL with query parameter cmd=get_customer_list
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("cmd", "get_customer_list");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        // Parse the JSON response to a list of Customer objects
        return objectMapper.readValue(response.getBody(), new TypeReference<List<Customer>>() {});

    }

    public ResponseEntity<String> deleteCustomer(String authToken, String uuid) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);

        // Build URL with query parameters cmd=delete and uuid
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("cmd", "delete")
                .queryParam("uuid", uuid);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                String.class);

        return response;
    }


    public ResponseEntity<String> updateCustomer(String uuid, CustomerRequest customerRequest, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("cmd", "update")
                .queryParam("uuid", uuid);

        HttpEntity<?> entity = new HttpEntity<>(customerRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                String.class);

        return response;
    }
}
