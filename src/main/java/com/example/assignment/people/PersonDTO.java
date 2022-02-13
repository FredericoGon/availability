package com.example.assignment.people;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDTO {

    private Long id;
    private String name;
    private ERole role;

    public PersonDTO(String name, ERole role) {
        this.name = name;
        this.role  = role;
    }

    public PersonDTO(Long id, String name, ERole role) {
        this.id = id;
        this.name = name;
        this.role  = role;
    }

}
