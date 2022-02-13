package com.example.assignment.availability;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class AvailabilityDTO {

    private Date date;
    private List<Integer> hours;

    public AvailabilityDTO(Date date, List<Integer> hours) {
        this.date = date;
        this.hours = hours;
    }

}
