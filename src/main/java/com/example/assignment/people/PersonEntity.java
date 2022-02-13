package com.example.assignment.people;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PersonEntity {

    private @Id @GeneratedValue Long id;
    private String name;
    private ERole role;

    public PersonEntity(String name, ERole role) {
        this.name = name;
        this.role = role;
    }

    public PersonEntity(Long id, String name, ERole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PersonEntity personEntity = (PersonEntity) o;
        return id != null && Objects.equals(id, personEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
