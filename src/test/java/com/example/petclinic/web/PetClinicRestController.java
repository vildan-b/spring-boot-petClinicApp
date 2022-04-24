package com.example.petclinic.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.petclinic.model.Owner;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PetClinicRestController {
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testGetOwnerById() {
        ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/2", Owner.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Vildan"));
    }

    @Test
    @SuppressWarnings("rawtypes")

    public void testGetOwnersByLastName(){
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owner?ln=Bay", List.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
         List<Map<String,String>> body =response.getBody();
        List<String> firstNames= body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
        MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Vildan", "Azra","Hilmi"));

    }
    

    @Test
    @SuppressWarnings("rawtypes")
    public void testGetOwners(){
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        List<Map<String,String>> body =response.getBody();
        List<String> firstNames= body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
        MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Vildan", "Azra","Hilmi","Gulush"));

    }
}
