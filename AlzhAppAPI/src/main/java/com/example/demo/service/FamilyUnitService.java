package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.FamilyUnit;
import com.example.demo.entity.Patient;
import com.example.demo.model.FamilyUnitModel;

public interface FamilyUnitService {
	
	public abstract List<FamilyUnitModel> listAllFamilyUnit();
	
	public abstract FamilyUnit addFamilyUnit(FamilyUnitModel familyUnitModel);

	public abstract FamilyUnit checkCode(String code);

	public abstract List<FamilyUnit> getAllFamilyUnit();

	public abstract Patient getPatientByCode(String code);
}
