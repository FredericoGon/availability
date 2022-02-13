package com.example.assignment.people;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    List<PersonEntity> findByRole(ERole ERole);

}
