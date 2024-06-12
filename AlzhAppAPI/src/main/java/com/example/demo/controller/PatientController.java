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

		if (!carer.isEnabled()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User isn't ENABLE");
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
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The patient code does not exist.");
		}

		if (carer.getFamilyUnit().contains(familyUnit)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("The Carer is already related to the Family Unit.");
		}

		Patient savedPatient = patientService.savePatientWithCarer(patient, carer);

		// Retornar una respuesta con el nuevo paciente agregado
		return ResponseEntity.status(HttpStatus.CREATED).body("Carer has been added successfully.");

	}

	@DeleteMapping("/patientapi/delete/{patientId}")
	public ResponseEntity<?> deletePatient(@RequestHeader("Authorization") String token, @PathVariable int patientId) {
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
			return ResponseEntity.status(HttpStatus.OK).body("This Carer don't have any patient.");
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
	
	@PostMapping("/patientapi/update")
	public ResponseEntity<?> updatePatient(@RequestBody PatientModel patientModel, @RequestHeader("Authorization") String token) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();

	    Carer carer = carerService.findByUsername(username);

	    if (carer == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado o no autorizado.");
	    }

	    Patient existingPatient = patientService.findPatientById(patientModel.getId());

	    if (existingPatient == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado.");
	    }

	    // Verificar si el Passport ID del modelo coincide con el del paciente existente
	    if (!existingPatient.getPassportid().equals(patientModel.getPassportId())) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede modificar el Passport ID.");
	    }

	    Patient updatedPatient = patientService.updatePatient(patientModel);
	    return ResponseEntity.ok(updatedPatient);
	}
	
	@PostMapping("/patientapi/exit/{patientId}")
	public ResponseEntity<?> exitPatient(@RequestHeader("Authorization") String token, @PathVariable int patientId) {
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

	    FamilyUnit familyUnit = patient.getFamilyUnit();

	    // Remover el carer de la lista de carers del paciente
	    patient.getCarersCare().remove(carer);

	    // Remover la unidad familiar del carer
	    carer.getFamilyUnit().remove(familyUnit);

	    // Guardar los cambios en el carer y paciente antes de continuar
	    carerService.saveCarer(carer);
	    patientService.savePatient(patient);

	    String message;

	    // Si el paciente no tiene más carers, eliminar el paciente y su unidad familiar
	    if (patient.getCarersCare().isEmpty()) {
	        // Eliminar todas las referencias del paciente en carer_patient
	        carerService.removePatientReferences(patient);

	        // Eliminar el paciente y la unidad familiar
	        patientService.deletePatientAndFamilyUnit(patient);
	        message = "El paciente ha sido eliminado ya que no tenía más cuidadores.";
	        return ResponseEntity.ok(message);
	    } else {
	        // Guardar los cambios finales
	        patientService.savePatient(patient);
	        carerService.saveCarer(carer);
	        message = "El cuidador ha salido de la unidad familiar y el paciente ha sido actualizado.";
	    }

	    return ResponseEntity.ok(message);
	}

}
