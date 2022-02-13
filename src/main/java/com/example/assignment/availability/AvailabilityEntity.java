package com.example.assignment.availability;

import com.example.assignment.people.PersonEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AvailabilityEntity {

    private @Id @GeneratedValue Long id;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;
    private Date date;
    private int hour;

    public AvailabilityEntity(PersonEntity person, Date date, int hour) {
        this.person = person;
        this.date = date;
        this.hour = hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailabilityEntity that = (AvailabilityEntity) o;
        return hour == that.hour && Objects.equals(id, that.id) && Objects.equals(person, that.person) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
