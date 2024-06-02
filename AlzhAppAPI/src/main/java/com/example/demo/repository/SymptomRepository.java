package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Patient;
import com.example.demo.entity.Symptom;

@Repository("symptomRepository")
public interface SymptomRepository extends JpaRepository<Symptom, Serializable> {
	
	public abstract Symptom findById(int id);

	List<Symptom> findByPatient(Patient patient);
}
