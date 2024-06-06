package com.example.demo.controller;

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
import com.example.demo.entity.FamilyUnit;
import com.example.demo.entity.Patient;
import com.example.demo.model.PatientModel;
import com.example.demo.service.CarerService;
import com.example.demo.service.FamilyUnitService;
import com.example.demo.service.PatientService;

@RestController
public class PatientController {

	@Autowired
	@Qualifier("patientService")
	private PatientService patientService;

	@Autowired
	@Qualifier("familyUnitService")
	private FamilyUnitService familyUnitService;

	@Autowired
	@Qualifier("carerService")
	private CarerService carerService;

	@PostMapping("/patientapi/add")
	public ResponseEntity<?> addPatient(@RequestBody PatientModel patient,
			@RequestHeader("Authorization") String token) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Carer carer = carerService.findByUsername(username);

		if (carer == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado o no autorizado.");
		}

		Patient existingPatient = patientService.checkPassportid(patient.getPassportId());

		if (existingPatient != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("The provided Passport ID is already associated with another patient.");
		}

		// Si el DNI no existe, agregar el nuevo paciente
		Patient savedPatient = patientService.addPatient(patient, carer);

		// Retornar una respuesta con el nuevo paciente agregado
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);

	}

	@PostMapping("/patientapi/addCarer/{code}")
	public ResponseEntity<?> addCarerPatient(@RequestHeader("Authorization") String token, @PathVariable String code) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Carer carer = carerService.findByUsername(username);

		if (carer == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado o no autorizado.");
		}

		FamilyUnit familyUnit = familyUnitService.checkCode(code);
		Patient patient = familyUnit.getPatient();
		if (patient == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("The patient this code is null");
		}

		Patient savedPatient = patientService.savePatientWithCarer(patient, carer);

		// Retornar una respuesta con el nuevo paciente agregado
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);

	}
	
    @DeleteMapping("/patientapi/delete/{patientId}")
    public ResponseEntity<?> deletePatient(
            @RequestHeader("Authorization") String token,
            @PathVariable int patientId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Carer carer = carerService.findByUsername(username);

        if (carer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado o no autorizado.");
        }

        Patient patient = patientService.findPatientById(patientId);

        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado.");
        }

        patientService.deletePatientAndFamilyUnit(patient);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

	@GetMapping("/patientapi/getpatients/carer")
	public ResponseEntity<?> getPatientsByCarer(@RequestHeader("Authorization") String token) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Carer carer = carerService.findByUsername(username);

		if (carer == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found or not authorized.");
		}

		List<Patient> listPatients = patientService.findPatientByCarer(carer);
		if (listPatients == null || listPatients.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No patients selected.");
		}

		return ResponseEntity.ok(listPatients);
	}


	@GetMapping("/patientapi/getuf/carer")
	public ResponseEntity<?> getFamilyUnitByCarer(@RequestHeader("Authorization") String token) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Carer carer = carerService.findByUsername(username);
System.out.println(carer);
		if (carer == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found or not authorized.");
		}
	    // Inicializa listfamily
	    List<FamilyUnit> listfamily = carer.getFamilyUnit();

	    if (listfamily.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.OK).body("No family selected.");
	    }

	    return ResponseEntity.ok(listfamily);
	}
}
