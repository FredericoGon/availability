package com.example.assignment.people;

import com.example.assignment.utils.PersonNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<PersonDTO> candidates() {
        List<PersonEntity> candidates = repository.findByRole(ERole.Candidate);
        List<PersonDTO> candidatesDTOs = new ArrayList<>();
        for (PersonEntity entity: candidates) {
            candidatesDTOs.add(toDTO(entity));
        }
        return candidatesDTOs;
    }

    public List<PersonDTO> interviewers() {
        List<PersonEntity> interviewers = repository.findByRole(ERole.Interviewer);
        List<PersonDTO> interviewersDTOs = new ArrayList<>();
        for (PersonEntity entity: interviewers) {
            interviewersDTOs.add(toDTO(entity));
        }
        return interviewersDTOs;
    }

    public PersonDTO findPersonDTO(Long id) throws PersonNotFoundException {
        return toDTO(findPersonEntity(id));
    }

    public PersonEntity findPersonEntity(Long id) throws PersonNotFoundException {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException("No person found for id: " + id));
    }

    public PersonDTO newPerson(PersonDTO person) {
        PersonEntity personEntity = repository.save(toEntity(person));
        return toDTO(personEntity);
    }

    public PersonDTO editPerson(PersonDTO personDTO, Long id) {
        PersonEntity personEntity = repository.findById(id)
                .map(person -> {
                    person.setName(personDTO.getName());
                    person.setRole(personDTO.getRole());
                    return repository.save(person);
                })
                .orElseThrow(() -> new RuntimeException("No person found for id: " + id));
        return toDTO(personEntity);
    }

    public void deletePerson(Long id) {
        repository.deleteById(id);
    }

    private PersonDTO toDTO(PersonEntity entity) {
        return new PersonDTO(entity.getId(), entity.getName(), entity.getRole());
    }

    private PersonEntity toEntity(PersonDTO dto) {
        return new PersonEntity(dto.getId(), dto.getName(), dto.getRole());
    }
}
