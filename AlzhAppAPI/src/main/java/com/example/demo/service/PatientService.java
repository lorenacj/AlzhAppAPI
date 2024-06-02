package com.example.demo.service;

import java.util.List;

import org.hibernate.type.descriptor.jdbc.JdbcTypeFamilyInformation.Family;

import com.example.demo.entity.Carer;
import com.example.demo.entity.Patient;
import com.example.demo.model.PatientModel;

public interface PatientService {

	public abstract List<PatientModel> listAllPatient();
	
	public abstract Patient addPatient(PatientModel patientModel,Carer carer);
	
	public abstract int removePatient(int id);
	
	public abstract Patient updatePatient(PatientModel patientModel);
	
	public abstract List<Patient> findPatientByCarer(Carer carer);
	
	public abstract Patient findPatientByFamily(Family family);
	
	public abstract Patient findPatientByPassportId(String dni);
	
	public abstract Patient transformPatient(PatientModel patientModel);
	
	public abstract PatientModel transformPatient(Patient patient);
	
	public abstract Patient checkPassportid(String passportid);

	public abstract Patient savePatientWithCarer(Patient patient, Carer carer);

	public abstract Patient findPatientById(int id);
}
