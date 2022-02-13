package com.example.assignment;

import com.example.assignment.availability.AvailabilityDTO;
import com.example.assignment.availability.AvailabilityService;
import com.example.assignment.people.ERole;
import com.example.assignment.people.PersonDTO;
import com.example.assignment.people.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class AssignmentApplicationTests {

	@Autowired
	private AvailabilityService availabilityService;

	@Autowired
	private PersonService personService;

	@Test
	public void matchTestNoMatch() {
		PersonDTO fred = new PersonDTO("Frederico Gonçalves", ERole.Candidate);
		fred = personService.newPerson(fred);
		List<Integer> fredHours = new ArrayList<>();
		fredHours.add(1);
		fredHours.add(2);
		fredHours.add(3);
		Date date = new Date();
		AvailabilityDTO fredAvailability = new AvailabilityDTO(date, fredHours);
		List<AvailabilityDTO> fredAvailabilities = new ArrayList<>();
		fredAvailabilities.add(fredAvailability);
		availabilityService.registerAvailabilities(fred.getId(), fredAvailabilities);

		PersonDTO pippin = new PersonDTO("Peregrin Took", ERole.Candidate);
		pippin = personService.newPerson(pippin);
		List<Integer> pippinHours = new ArrayList<>();
		pippinHours.add(4);
		pippinHours.add(5);
		pippinHours.add(6);
		AvailabilityDTO pippinAvailability = new AvailabilityDTO(date, pippinHours);
		List<AvailabilityDTO> pippinAvailabilities = new ArrayList<>();
		pippinAvailabilities.add(pippinAvailability);
		availabilityService.registerAvailabilities(pippin.getId(), pippinAvailabilities);

		List<Long> ids = new ArrayList<>();
		ids.add(fred.getId());
		ids.add(pippin.getId());
		List<AvailabilityDTO> matches = availabilityService.getMatches(ids);
		assert CollectionUtils.isEmpty(matches);
	}

	@Test
	public void matchTestMatches() {
		PersonDTO fred = new PersonDTO("Frederico Gonçalves", ERole.Candidate);
		fred = personService.newPerson(fred);
		List<Integer> fredHours = new ArrayList<>();
		fredHours.add(1);
		fredHours.add(2);
		fredHours.add(3);
		Date date = new Date();
		AvailabilityDTO fredAvailability = new AvailabilityDTO(date, fredHours);
		List<AvailabilityDTO> fredAvailabilities = new ArrayList<>();
		fredAvailabilities.add(fredAvailability);
		availabilityService.registerAvailabilities(fred.getId(), fredAvailabilities);

		PersonDTO pippin = new PersonDTO("Peregrin Took", ERole.Candidate);
		pippin = personService.newPerson(pippin);
		List<Integer> pippinHours = new ArrayList<>();
		pippinHours.add(2);
		pippinHours.add(3);
		pippinHours.add(4);
		AvailabilityDTO pippinAvailability = new AvailabilityDTO(date, pippinHours);
		List<AvailabilityDTO> pippinAvailabilities = new ArrayList<>();
		pippinAvailabilities.add(pippinAvailability);
		availabilityService.registerAvailabilities(pippin.getId(), pippinAvailabilities);

		List<Long> ids = new ArrayList<>();
		ids.add(fred.getId());
		ids.add(pippin.getId());
		List<AvailabilityDTO> matches = availabilityService.getMatches(ids);
		assert matches.get(0).getHours().size() == 2;
	}

	@Test
	public void matchTestDifferentDay() {
		PersonDTO fred = new PersonDTO("Frederico Gonçalves", ERole.Candidate);
		fred = personService.newPerson(fred);
		List<Integer> fredHours = new ArrayList<>();
		fredHours.add(1);
		fredHours.add(2);
		fredHours.add(3);
		Date date = new Date();
		AvailabilityDTO fredAvailability = new AvailabilityDTO(date, fredHours);
		List<AvailabilityDTO> fredAvailabilities = new ArrayList<>();
		fredAvailabilities.add(fredAvailability);
		availabilityService.registerAvailabilities(fred.getId(), fredAvailabilities);

		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localDateTime = localDateTime.plusDays(10);
		Date datePlus10 = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		PersonDTO pippin = new PersonDTO("Peregrin Took", ERole.Candidate);
		pippin = personService.newPerson(pippin);
		List<Integer> pippinHours = new ArrayList<>();
		pippinHours.add(1);
		pippinHours.add(2);
		pippinHours.add(3);
		AvailabilityDTO pippinAvailability = new AvailabilityDTO(datePlus10, pippinHours);
		List<AvailabilityDTO> pippinAvailabilities = new ArrayList<>();
		pippinAvailabilities.add(pippinAvailability);
		availabilityService.registerAvailabilities(pippin.getId(), pippinAvailabilities);

		List<Long> ids = new ArrayList<>();
		ids.add(fred.getId());
		ids.add(pippin.getId());
		List<AvailabilityDTO> matches = availabilityService.getMatches(ids);
		assert CollectionUtils.isEmpty(matches);
	}

	@Test
	public void matchTestMultiplePeople() {
		PersonDTO fred = new PersonDTO("Frederico Gonçalves", ERole.Candidate);
		fred = personService.newPerson(fred);
		List<Integer> fredHours = new ArrayList<>();
		fredHours.add(1);
		fredHours.add(2);
		fredHours.add(3);
		Date date = new Date();
		AvailabilityDTO fredAvailability = new AvailabilityDTO(date, fredHours);
		List<AvailabilityDTO> fredAvailabilities = new ArrayList<>();
		fredAvailabilities.add(fredAvailability);
		availabilityService.registerAvailabilities(fred.getId(), fredAvailabilities);

		PersonDTO pippin = new PersonDTO("Peregrin Took", ERole.Candidate);
		pippin = personService.newPerson(pippin);
		List<Integer> pippinHours = new ArrayList<>();
		pippinHours.add(3);
		pippinHours.add(4);
		pippinHours.add(5);
		AvailabilityDTO pippinAvailability = new AvailabilityDTO(date, pippinHours);
		List<AvailabilityDTO> pippinAvailabilities = new ArrayList<>();
		pippinAvailabilities.add(pippinAvailability);
		availabilityService.registerAvailabilities(pippin.getId(), pippinAvailabilities);

		PersonDTO merry = new PersonDTO("Meriadoc Brandybuck", ERole.Interviewer);
		merry = personService.newPerson(merry);
		List<Integer> merryHours = new ArrayList<>();
		merryHours.add(3);
		merryHours.add(6);
		merryHours.add(9);
		AvailabilityDTO merryAvailability = new AvailabilityDTO(date, merryHours);
		List<AvailabilityDTO> merryAvailabilities = new ArrayList<>();
		merryAvailabilities.add(merryAvailability);
		availabilityService.registerAvailabilities(merry.getId(), merryAvailabilities);

		List<Long> ids = new ArrayList<>();
		ids.add(fred.getId());
		ids.add(pippin.getId());
		List<AvailabilityDTO> matches = availabilityService.getMatches(ids);
		assert matches.get(0).getHours().size() == 1;
	}

}
