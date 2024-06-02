package com.example.demo.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Carer;
import com.example.demo.entity.Medicine;
import com.example.demo.entity.Patient;
import com.example.demo.model.MedicineModel;
import com.example.demo.service.CarerService;
import com.example.demo.service.MedicineService;
import com.example.demo.service.PatientService;

@RestController
@RequestMapping("/medicineapi")
public class MedicineController {

	@Autowired
	@Qualifier("patientService")
	private PatientService patientService;

	@Autowired
	@Qualifier("medicineService")
	private MedicineService medicineService;

	@Autowired
	@Qualifier("carerService")
	private CarerService carerService;

	@PostMapping("/add")
	public ResponseEntity<?> addMedicine(@RequestBody MedicineModel medicineModel, @RequestHeader("Authorization") String token) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    
	    Carer carer = carerService.findByUsername(username);
	    if (carer == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado o no autorizado.");
	    }

	    // Crear la lista de cuidadores y agregar el cuidador actual
	    List<Carer> carers = new ArrayList<>();
	    carers.add(carer);
	    medicineModel.setCarers(carers);

	    // Buscar el paciente por su ID
	    Patient patient = patientService.findPatientById(medicineModel.getPatients().get(0).getId());
	    if (patient == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found.");
	    }

	    // Asignar el paciente al medicamento
	    medicineModel.getPatients().clear(); // Limpiar la lista de pacientes para evitar la referencia circular
	    medicineModel.getPatients().add(patient);

	    // Guardar la medicina
	    Medicine savedMedicine = medicineService.createMedicine(medicineModel);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedMedicine);
	}


	@PutMapping("/update")
	public ResponseEntity<?> updateMedicine(@RequestBody MedicineModel medicineModel) {
		// Check if the medicine exists
		Medicine existingMedicine = medicineService.updateMedicine(medicineModel);
		if (existingMedicine == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medicine not found.");
		}
		// Update the existing medicine
		Medicine updatedMedicine = medicineService.updateMedicine(medicineModel);
		return ResponseEntity.status(HttpStatus.OK).body(updatedMedicine);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteMedicine(@PathVariable int id) {
		// Delete the medicine by ID
		int deletedId = medicineService.deleteMedicine(id);
		return ResponseEntity.status(HttpStatus.OK).body("Medicine with ID " + deletedId + " deleted successfully.");
	}

	@GetMapping("/list")
	public ResponseEntity<?> listAllMedicines() {
		// List all medicines
		List<MedicineModel> medicines = medicineService.listAllMedicines();
		return ResponseEntity.status(HttpStatus.OK).body(medicines);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getMedicineById(@PathVariable int id) {
		// Find the medicine by ID
		Medicine medicine = medicineService.transformMedicine(
				medicineService.listAllMedicines().stream().filter(m -> m.getId() == id).findFirst().orElse(null));
		if (medicine == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medicine not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(medicine);
	}
}