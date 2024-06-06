package com.example.demo.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.hibernate.type.descriptor.jdbc.JdbcTypeFamilyInformation.Family;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Carer;
import com.example.demo.entity.FamilyUnit;
import com.example.demo.entity.Patient;
import com.example.demo.model.PatientModel;
import com.example.demo.repository.CarerRepository;
import com.example.demo.repository.FamilyUnitRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.service.PatientService;

@Service("patientService")
public class PatientServiceImpl implements PatientService {

	@Autowired
	@Qualifier("patientRepository")
	private PatientRepository patientRepository;

	@Autowired
	@Qualifier("familyUnitRepository")
	private FamilyUnitRepository familyUnitRepository;
	
	@Autowired
	@Qualifier("carerRepository")
	private CarerRepository carerRepository;

	
	@Override
	public List<PatientModel> listAllPatient() {
		ModelMapper modelMapper = new ModelMapper();

		List<Patient> patientList = patientRepository.findAll();
		return patientList.stream().map(patient -> modelMapper.map(patient, PatientModel.class))
				.collect(Collectors.toList());
	}

	@Override
	public Patient addPatient(PatientModel patientModel, Carer carer) {
	    // Transformar el modelo de paciente a una instancia de paciente
	    Patient patient = transformPatient(patientModel);
	    
	    // Generar un código aleatorio para FamilyUnit
	    String randomCode = generateRandomCode();

	    // Crear una nueva instancia de FamilyUnit con el código aleatorio y guardarla en la base de datos
	    FamilyUnit familyUnit = new FamilyUnit();
	    familyUnit.setCode(randomCode);
	    familyUnit = familyUnitRepository.save(familyUnit);
	    
	    // Guardar primero la instancia de Carer si es nueva
	    if (carer.getId() == 0) {
	    	carer.setFamilyUnit(Arrays.asList(familyUnit));
	        carer = carerRepository.save(carer);
	    }
	    else {
		    List<FamilyUnit> listfamilyUnit= new ArrayList<>();
		    listfamilyUnit.add(familyUnit);
		    carer.setFamilyUnit(listfamilyUnit);
		    carerRepository.save(carer);
	    }
	    
	    
	    // Agregar el nuevo paciente a la lista de pacientes del cuidador
	    List<Patient> listPatients = carer.getPatientsCare();
	    listPatients.add(patient);
	    carer.setPatientsCare(listPatients);

	    
	    // Establecer la FamilyUnit del paciente y el cuidador asociado
	    patient.setFamilyUnit(familyUnit);
	    List<Carer> listCarers = new ArrayList<>();
	    listCarers.add(carer);
	    patient.setCarersCare(listCarers);
	    
	    // Guardar el paciente en la base de datos
	    return patientRepository.save(patient);
	}

	
	
	private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	 public static String generateRandomCode() {
	        Random random = new Random();
	        StringBuilder code = new StringBuilder();

	        for (int i = 0; i < 9; i++) {
	            int randomIndex = random.nextInt(CHARACTERS.length());
	            code.append(CHARACTERS.charAt(randomIndex));
	        }

	        return code.toString();
	    }
	 
	@Override
	public int removePatient(int id) {
		patientRepository.deleteById(id);
		return id;
	}

	@Override
	public Patient updatePatient(PatientModel patientModel) {
		 // Buscar el paciente por su ID
	    Patient existingPatient = patientRepository.findById(patientModel.getId());
	    if (existingPatient == null) {
	        return null; // Devolver null si el paciente no existe
	    }

	    // Actualizar los campos del paciente con los valores del modelo
	    existingPatient.setName(patientModel.getName());
	    existingPatient.setLastname(patientModel.getLastname());
	    existingPatient.setBirthdate(patientModel.getBirthdate());
	    existingPatient.setHeight(patientModel.getHeight());
	    existingPatient.setWeight(patientModel.getWeight());
	    existingPatient.setDisorder(patientModel.getDisorder());
//	    existingPatient.setPassportid(patientModel.getPassportid());
	    existingPatient.setEnabled(patientModel.isEnabled());
	    existingPatient.setDeleted(patientModel.isDeleted());
	    // Agregar más campos según sea necesario

	    // Guardar y devolver el paciente actualizado
	    return patientRepository.save(existingPatient);
	}

	@Override
	public List<Patient> findPatientByCarer(Carer carer) {
		return carer.getPatientsCare();
	}

	
	@Override
	public Patient findPatientByFamily(Family family) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Patient findPatientByPassportId(String dni) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Patient findPatientById(int id) {
		
		return patientRepository.findById(id);
	}
	@Override
	public Patient transformPatient(PatientModel patientModel) {
		if (patientModel == null)
			return null;
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(patientModel, Patient.class);
	}

	@Override
	public PatientModel transformPatient(Patient patient) {
		if (patient == null)
			return null;
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(patient, PatientModel.class);
	}

	@Override
	public Patient checkPassportid(String passportid) {

		return patientRepository.findAll().stream().filter(patient -> patient.getPassportid().equals(passportid)).findFirst()
				.orElse(null);
	}

	@Override
	public Patient savePatientWithCarer(Patient patient, Carer carer) {
		List<Patient> listPatients = carer.getPatientsCare();
		listPatients.add(patient);
		carer.setPatientsCare(listPatients);
		
		List<Carer> listCarers = patient.getCarersCare();
	    listCarers.add(carer);
	    patient.setCarersCare(listCarers);
	    
	    carer = carerRepository.save(carer);
	    
		return patientRepository.save(patient);
	}


}
