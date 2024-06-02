package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Carer;
import com.example.demo.entity.Patient;
import com.example.demo.model.CarerModel;

public interface CarerService {

	public abstract List<CarerModel> listAllCarer();

	public abstract Carer addCarer(CarerModel carerModel);

	public abstract Carer register(CarerModel CarerModel);

	public abstract int removeCarer(int id);

	public abstract Carer updateCarer(CarerModel carerModel);

//	public abstract Carer findByPID(int passportID);

	public abstract boolean checkPassword(String rawPassword, String encodedPassword);

	public abstract CarerModel findCarerByPatient(Patient patient);

	// to do with passport id
	public abstract CarerModel findCarerByPatientPID(String patientPassportId);

	public abstract Carer transformCarer(CarerModel carerModel);

	public abstract CarerModel transformCarer(Carer carer);

	public abstract Carer findByUsername(String passportID);

}
