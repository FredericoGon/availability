package com.example.assignment.people;

import com.example.assignment.utils.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping("/candidates")
    public ResponseEntity<List<PersonDTO>> candidates() {
        return new ResponseEntity<>(service.candidates(), HttpStatus.OK);
    }

    @GetMapping("/interviewers")
    public ResponseEntity<List<PersonDTO>> interviewers() {
        return new ResponseEntity<>(service.interviewers(), HttpStatus.OK);
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<PersonDTO> person(@PathVariable Long id) throws PersonNotFoundException {
        return new ResponseEntity<>(service.findPersonDTO(id), HttpStatus.OK);
    }

    @PostMapping("/person")
    public ResponseEntity<PersonDTO> newPerson(@RequestBody PersonDTO person) {
        return new ResponseEntity<>(service.newPerson(person), HttpStatus.OK);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<PersonDTO> editPerson(@RequestBody PersonDTO person, @PathVariable Long id) {
        return new ResponseEntity<>(service.editPerson(person, id), HttpStatus.OK);
    }

    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable Long id) {
        service.deletePerson(id);
    }

}
