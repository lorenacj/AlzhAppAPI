package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Carer;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Symptom;
import com.example.demo.model.SymptomModel;
import com.example.demo.service.CarerService;
import com.example.demo.service.PatientService;
import com.example.demo.service.SymptomService;

@RestController
@RequestMapping("/symptomsapi")
public class SymptomController {

	@Autowired
	private SymptomService symptomService;

	@Autowired
	@Qualifier("patientService")
	private PatientService patientService;

	@Autowired
	@Qualifier("carerService")
	private CarerService carerService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addSymptom(@RequestBody SymptomModel symptomModel,
			@RequestParam(name = "patientId") int patientId, @RequestHeader("Authorization") String token) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    
	    Carer carer=carerService.findByUsername(username);

	    if (carer == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado o no autorizado.");
	    }
	    
		Patient patient = patientService.findPatientById(patientId);
		if (patient == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found.");
		}

		Symptom symptom = symptomService.createSymptom(symptomModel, patient);
		return ResponseEntity.status(HttpStatus.CREATED).body(symptom);
	}

	@GetMapping("/list/{patientId}")
	public ResponseEntity<?> listSymptomsByPatient(@PathVariable int patientId) {
		Patient patient = patientService.findPatientById(patientId);
		if (patient == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found.");
		}

		List<Symptom> symptoms = symptomService.listSymptomsByPatient(patient);
		return ResponseEntity.ok(symptoms);
	}

}
