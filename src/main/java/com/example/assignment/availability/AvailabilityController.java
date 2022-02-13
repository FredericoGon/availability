package com.example.assignment.availability;

import com.example.assignment.utils.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AvailabilityController {

    private final AvailabilityService service;

    public AvailabilityController(AvailabilityService service) {
        this.service = service;
    }

    @GetMapping("/availability/{personId}")
    public ResponseEntity<List<AvailabilityDTO>> availability(@PathVariable Long personId) throws PersonNotFoundException {
        return new ResponseEntity<>(service.findAvailabilityByPerson(personId), HttpStatus.OK);
    }

    @PostMapping("/availabilities/{personId}")
    public ResponseEntity<List<AvailabilityDTO>> registerAvailabilities(@PathVariable Long personId, @RequestBody List<AvailabilityDTO> availabilities) throws PersonNotFoundException {
        service.registerAvailabilities(personId, availabilities);
        return new ResponseEntity<>(service.findAvailabilityByPerson(personId), HttpStatus.OK);
    }

    @DeleteMapping("/availabilities/{personId}")
    public ResponseEntity<List<AvailabilityDTO>> deleteAvailabilities(@PathVariable Long personId, @RequestBody List<AvailabilityDTO> availabilities) throws PersonNotFoundException {
        service.deleteAvailabilities(personId, availabilities);
        return new ResponseEntity<>(service.findAvailabilityByPerson(personId), HttpStatus.OK);
    }

    @PostMapping("/availabilities-matches")
    public ResponseEntity<List<AvailabilityDTO>> getMatches(@RequestBody List<Long> peopleIds) throws PersonNotFoundException {
        return new ResponseEntity<>(service.getMatches(peopleIds), HttpStatus.OK);
    }
}
