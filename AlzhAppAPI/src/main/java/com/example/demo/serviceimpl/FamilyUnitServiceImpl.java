package com.example.demo.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Carer;
import com.example.demo.entity.FamilyUnit;
import com.example.demo.entity.Patient;
import com.example.demo.model.FamilyUnitModel;
import com.example.demo.model.PatientModel;
import com.example.demo.repository.FamilyUnitRepository;
import com.example.demo.service.FamilyUnitService;

@Service("familyUnitService")
public class FamilyUnitServiceImpl implements FamilyUnitService {

	@Autowired
	@Qualifier("familyUnitRepository")
	private FamilyUnitRepository familyUnitRepository;

	@Override
	public List<FamilyUnitModel> listAllFamilyUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FamilyUnit addFamilyUnit(FamilyUnitModel familyUnitModel) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
    public Patient getPatientByCode(String code) {
        Optional<FamilyUnit> optionalFamilyUnit = Optional.ofNullable(familyUnitRepository.findByCode(code));

        return optionalFamilyUnit.map(FamilyUnit::getPatient).orElse(null);
    }
	
	@Override
	public FamilyUnit checkCode(String code) {
		 Optional<FamilyUnit> optionalFamilyUnit = familyUnitRepository.findAll()
	                .stream()
	                .filter(familyUnit -> familyUnit.getCode().equals(code))
	                .findFirst();

	        return optionalFamilyUnit.orElse(null);
	}

	@Override
	public List<FamilyUnit> getAllFamilyUnit() {
		return familyUnitRepository.findAll().stream().collect(Collectors.toList());
	}

	@Override
	public List<FamilyUnit> getFamilyByCarer(Carer carer) {
		return familyUnitRepository.findAll().stream().collect(Collectors.toList());
	}
	@Override
	public FamilyUnit transform(FamilyUnitModel familyUnitModel) {
		if (familyUnitModel == null)
			return null;
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(familyUnitModel, FamilyUnit.class);
	}

	@Override
	public FamilyUnitModel transform(FamilyUnit familyUnit) {
		if (familyUnit == null)
			return null;
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(familyUnit, FamilyUnitModel.class);
	}
	
	@Override
	public FamilyUnit findFamilyById(int id) {

		return familyUnitRepository.findById(id);
	}

}
