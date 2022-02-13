package com.example.assignment.availability;

import com.example.assignment.people.PersonEntity;
import com.example.assignment.people.PersonService;
import com.example.assignment.utils.PersonNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AvailabilityService {

    private final AvailabilityRepository repository;

    public AvailabilityService(AvailabilityRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private PersonService personService;

    private static final Logger log = LoggerFactory.getLogger(AvailabilityService.class);

    public List<AvailabilityDTO> findAvailabilityByPerson(Long personId) throws PersonNotFoundException {
        PersonEntity personEntity = personService.findPersonEntity(personId);
        log.info("findAvailabilityByPerson - personEntity: " + personEntity);
        List<AvailabilityEntity> availabilities = repository.findByPerson(personEntity);
        return getAvailabilityDTOS(availabilities);
    }

    public void registerAvailabilities(Long personId, List<AvailabilityDTO> availabilities) throws PersonNotFoundException {
        PersonEntity personEntity = personService.findPersonEntity(personId);
        log.info("registerAvailabilities - personEntity: " + personEntity);
        for (AvailabilityDTO newAvailability: availabilities) {
            for (int hour: newAvailability.getHours()) {
                AvailabilityEntity availabilityEntity = repository.findByPersonAndDateAndHour(personEntity, newAvailability.getDate(), hour);
                if (Objects.isNull(availabilityEntity)) {
                    repository.save(new AvailabilityEntity(personEntity, newAvailability.getDate(), hour));
                }
            }
        }
    }

    public void deleteAvailabilities(Long personId, List<AvailabilityDTO> availabilities) throws PersonNotFoundException {
        PersonEntity personEntity = personService.findPersonEntity(personId);
        log.info("deleteAvailabilities - personEntity: " + personEntity);
        for (AvailabilityDTO newAvailability: availabilities) {
            for (int hour: newAvailability.getHours()) {
                AvailabilityEntity availabilityEntity = repository.findByPersonAndDateAndHour(personEntity, newAvailability.getDate(), hour);
                if (!Objects.isNull(availabilityEntity)) {
                    repository.deleteById(availabilityEntity.getId());
                }
            }
        }
    }

    public List<AvailabilityDTO> getMatches(List<Long> peopleIds) throws PersonNotFoundException {
        List<AvailabilityDTO> matches = new ArrayList<>();
        for (int i = 0; i < peopleIds.size(); i++) {
            PersonEntity personEntity = personService.findPersonEntity(peopleIds.get(i));
            log.info("getMatches - personEntity: " + personEntity);
            List<AvailabilityEntity> availabilities = repository.findByPerson(personEntity);
            List<AvailabilityDTO> availabilityDTOS = getAvailabilityDTOS(availabilities);
            if (i == 0) {
                matches.addAll(availabilityDTOS);
            } else if (matches.size() == 0) {
                return matches;
            } else {
                matches = findMatches(matches, availabilityDTOS);
            }
        }
        return matches;
    }

    private List<AvailabilityDTO> findMatches(List<AvailabilityDTO> oldMatches, List<AvailabilityDTO> availabilityDTOS) {
        List<AvailabilityDTO> newMatches = new ArrayList<>();
        for (AvailabilityDTO oldMatch: oldMatches) {
            for (AvailabilityDTO availabilityDTO: availabilityDTOS) {
                if (oldMatch.getDate().equals(availabilityDTO.getDate())) {
                    List<Integer> hours = new ArrayList<>();
                    for (Integer matchHour: oldMatch.getHours()) {
                        for (Integer availabilityHour: availabilityDTO.getHours()) {
                            if (matchHour == availabilityHour) {
                                hours.add(matchHour);
                            }
                        }
                    }
                    if (hours.size() > 0) {
                        AvailabilityDTO newMatch = new AvailabilityDTO(oldMatch.getDate(), hours);
                        newMatches.add(newMatch);
                    }
                }
            }
        }
        return newMatches;
    }

    private List<AvailabilityDTO> getAvailabilityDTOS(List<AvailabilityEntity> availabilities) {
        List<AvailabilityDTO> outputList = new ArrayList<>();
        for (AvailabilityEntity availabilityEntity : availabilities) {
            if (outputList.stream().anyMatch(availabilityDTO -> availabilityDTO.getDate().equals(availabilityEntity.getDate()))) {
                List<Integer> hours = outputList.stream().filter(availabilityDTO -> availabilityDTO.getDate().equals(availabilityEntity.getDate())).findFirst().get().getHours();
                hours.add(availabilityEntity.getHour());
                outputList.stream().filter(availabilityDTO -> availabilityDTO.getDate().equals(availabilityEntity.getDate())).findFirst().get().setHours(hours);
            } else {
                List<Integer> hours = new ArrayList<>();
                hours.add(availabilityEntity.getHour());
                outputList.add(new AvailabilityDTO(availabilityEntity.getDate(), hours));
            }
        }
        return outputList;
    }
}
