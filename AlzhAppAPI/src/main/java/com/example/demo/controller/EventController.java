package com.example.demo.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Carer;
import com.example.demo.entity.Event;
import com.example.demo.entity.Patient;
import com.example.demo.model.EventModel;
import com.example.demo.service.CarerService;
import com.example.demo.service.EventService;
import com.example.demo.service.PatientService;

import jakarta.validation.Valid;

@RestController
public class EventController {

	@Autowired
	@Qualifier("patientService")
	private PatientService patientService;

	@Autowired
	@Qualifier("eventService")
	private EventService eventService;

	@Autowired
	@Qualifier("carerService")
	private CarerService carerService;

	@PostMapping("/eventapi/add/{idPatient}")
	public ResponseEntity<?> addEvent(@Valid @RequestBody EventModel eventModel,
			@RequestHeader("Authorization") String token, @PathVariable int idPatient) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Carer carer = carerService.findByUsername(username);

		if (carer == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado o no autorizado.");
		}

		Patient patient = patientService.findPatientById(idPatient);

		if (patient == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
		}

		Event saveEvent = eventService.createEvent(eventModel, patient);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveEvent);

	}

	@GetMapping("/eventapi/type/{type}")
	public ResponseEntity<?> getEventsByType(@RequestHeader("Authorization") String token, @PathVariable String type) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Carer carer = carerService.findByUsername(username);

		if (carer == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado o no autorizado.");
		}
		List<Event> events = eventService.getEventsByType(type, carer);
		if(events==null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay ningun evento");
		}
		return ResponseEntity.ok(events);
	}

	@GetMapping("/eventapi/byCarer")
	public ResponseEntity<?> getEventsByCarer(@RequestHeader("Authorization") String token) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Carer carer = carerService.findByUsername(username);

		if (carer == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado o no autorizado.");
		}

		List<Event> eventsOnlyCarer = eventService.getEventsByCarer(carer);
		if(eventsOnlyCarer==null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay ningun evento");
		}
		return ResponseEntity.ok(eventsOnlyCarer);

	}

	@GetMapping("/eventapi/idPatient/{idPatient}")
	public ResponseEntity<?> getEventsByPatient(@RequestHeader("Authorization") String token,
			@PathVariable int idPatient) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Carer carer = carerService.findByUsername(username);

		if (carer == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado o no autorizado.");
		}

		Patient patient = patientService.findPatientById(idPatient);

		List<Event> events = patient.getEvents();
		
		if(events==null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay ningun evento");
		}
		events.sort(Comparator.comparing(Event::getInitialDate).reversed());
		return ResponseEntity.ok(events);
	}
	
	 @DeleteMapping("/eventapi/delete/{eventId}")
	    public ResponseEntity<String> deleteEvent(@PathVariable int eventId, @RequestHeader("Authorization") String token) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();

			Carer carer = carerService.findByUsername(username);

			if (carer == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado o no autorizado.");
			}
	        int isDeleted = eventService.deleteEvent(eventId);
	        if (isDeleted!=0) {
	            return ResponseEntity.ok("Event deleted successfully.");
	        } else {
	            return ResponseEntity.status(404).body("Event not found.");
	        }
	    }

}
