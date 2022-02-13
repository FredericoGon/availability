package com.example.assignment.availability;

import com.example.assignment.people.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, Long> {

    List<AvailabilityEntity> findByPerson(PersonEntity personEntity);
    AvailabilityEntity findByPersonAndDateAndHour(PersonEntity personEntity, Date date, int hour);

}
