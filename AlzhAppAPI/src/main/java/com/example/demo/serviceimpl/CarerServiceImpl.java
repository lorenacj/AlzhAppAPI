package com.example.demo.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Carer;
import com.example.demo.entity.FamilyUnit;
import com.example.demo.entity.Patient;
import com.example.demo.model.CarerModel;
import com.example.demo.repository.CarerRepository;
import com.example.demo.service.CarerService;

@Service("carerService")
public class CarerServiceImpl implements CarerService {
	private PasswordEncoder carerpasswordEncoder;

	@Autowired
	@Qualifier("carerRepository")
	private CarerRepository carerRepository;

	public CarerServiceImpl() {
		this.carerpasswordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public List<CarerModel> listAllCarer() {
		
			ModelMapper modelMapper = new ModelMapper();

			List<Carer> carerList = carerRepository.findAll();
			return carerList.stream().map(carer -> modelMapper.map(carer, CarerModel.class))
					.collect(Collectors.toList());

	}

	// puedes seleccionar role
	  public Carer addCarer(CarerModel carerModel) {
	        // Implementa la lógica para añadir un cuidador
	        Carer carer = transformCarer(carerModel);
	        carer.setPassword(carerpasswordEncoder.encode(carer.getPassword()));
	        carer.setRole("ROLE_CARER");
	        
	        return carerRepository.save(carer);
	    }

	@Override
	public int removeCarer(int id) {
		carerRepository.deleteById(id);
		return id;
	}

	@Override
	public Carer updateCarer(CarerModel carerModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Carer findByUsername(String passportID) {
		return carerRepository.findByUsername(passportID);
	}

	@Override
	public CarerModel findCarerByPatient(Patient patient) {
		
		return null;
	}

	@Override
	public CarerModel findCarerByPatientPID(String patientPassportId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Carer transformCarer(CarerModel carerModel) {
		if (carerModel == null) {
			return null;
		}

		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(carerModel, Carer.class);
	}

	@Override
	public CarerModel transformCarer(Carer carer) {
		if (carer == null) {
			return null;
		}
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(carer, CarerModel.class);
	}

	@Override
	public boolean checkPassword(String rawPassword, String encodedPassword) {
		return carerpasswordEncoder.matches(rawPassword, encodedPassword);
	}

	// solo carer
	@Override
	public Carer register(CarerModel carerModel) {
		Carer carer = transformCarer(carerModel);
		carer.setPassword(carerpasswordEncoder().encode(carer.getPassword()));
		carer.setRole("ROLE_CARER");
		List<Patient> patients = null;
		carer.setPatientsCare(patients);
		List<FamilyUnit> family=null;
		carer.setFamilyUnit(family);
		return carerRepository.save(carer);
	}

	@Bean
	PasswordEncoder carerpasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
