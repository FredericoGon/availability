package com.example.assignment.utils;

import com.example.assignment.availability.AvailabilityEntity;
import com.example.assignment.availability.AvailabilityRepository;
import com.example.assignment.people.PersonEntity;
import com.example.assignment.people.PersonRepository;
import com.example.assignment.people.ERole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.Date;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PersonRepository personRepository, AvailabilityRepository availabilityRepository) {
        PersonEntity ines = personRepository.save(new PersonEntity("Ines", ERole.Interviewer));
        PersonEntity ingrid = personRepository.save(new PersonEntity("Ingrid", ERole.Interviewer));
        PersonEntity carl = personRepository.save(new PersonEntity("Carl", ERole.Candidate));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.FEBRUARY, 7, 0, 0, 0);
        for (int i = 0; i < 5; i++) {
            Date date = calendar.getTime();
            for (int j = 9; j < 16; j++) {
                availabilityRepository.save(new AvailabilityEntity(ines, date, j));
            }
            calendar.add(Calendar.DATE, 1);
        }
        calendar.set(2022, Calendar.FEBRUARY, 7, 0, 0, 0);
        for (int i = 1; i < 5; i = i + 1) {
            Date date = calendar.getTime();
            if (i%2 != 0) {
                for (int j = 12; j < 18; j++) {
                    availabilityRepository.save(new AvailabilityEntity(ingrid, date, j));
                }
            } else {
                for (int j = 9; j < 12; j++) {
                    availabilityRepository.save(new AvailabilityEntity(ingrid, date, j));
                }
            }
            calendar.add(Calendar.DATE, 1);
        }
        calendar.set(2022, Calendar.FEBRUARY, 7, 0, 0, 0);
        for (int i = 0; i < 5; i++) {
            Date date = calendar.getTime();
            for (int j = 9; j < 10; j++) {
                availabilityRepository.save(new AvailabilityEntity(carl, date, j));
            }
            if (i == 2) {
                for (int j = 10; j < 12; j++) {
                    availabilityRepository.save(new AvailabilityEntity(carl, date, j));
                }
            }
            calendar.add(Calendar.DATE, 1);
        }
        return args -> log.info("Initial load successful.");
    }
}
