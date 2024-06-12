package com.example.demo.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Patient;
import com.example.demo.entity.Symptom;
import com.example.demo.model.SymptomModel;
import com.example.demo.repository.SymptomRepository;
import com.example.demo.service.SymptomService;

@Service("symptomService")
public class SymptomServiceImpl implements SymptomService {

	@Autowired
	@Qualifier("symptomRepository")
	private SymptomRepository symptomRepository;

	@Override
	public Symptom createSymptom(SymptomModel symptomModel, Patient patient) {
		Symptom symptom = transformSymptom(symptomModel);
		symptom.setPatient(patient);
		return symptomRepository.save(symptom);
	}

	@Override
	public Symptom updateSymptom(SymptomModel symptomModel) {
		Symptom symptom = transformSymptom(symptomModel);
		return symptomRepository.save(symptom);
	}

	@Override
	public int deleteSymptom(int idSymptom) {
		symptomRepository.deleteById(idSymptom);
		return idSymptom;
	}

	@Override
	public List<SymptomModel> listAllSymptoms() {
		return symptomRepository.findAll().stream().map(this::transformSymptom).collect(Collectors.toList());
	}

	@Override
	public List<Symptom> listSymptomsByPatient(Patient patient) {
		return symptomRepository.findByPatient(patient);
	}

	@Override
	public Symptom transformSymptom(SymptomModel symptomModel) {
		if (symptomModel == null) {
			return null;
		}
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(symptomModel, Symptom.class);
	}

	@Override
	public SymptomModel transformSymptom(Symptom symptom) {
		if (symptom == null) {
			return null;
		}
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(symptom, SymptomModel.class);
	}

	@Override
	@Transactional
	public void remove(List<Symptom> symptoms) {
		for (Symptom s : symptoms) {
			symptomRepository.deleteById(s.getId());
		}
	}

}
