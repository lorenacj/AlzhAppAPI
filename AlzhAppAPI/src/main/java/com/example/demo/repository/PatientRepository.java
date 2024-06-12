package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Patient;

@Repository("patientRepository")

public interface PatientRepository extends JpaRepository<Patient, Serializable> {
	public abstract Patient findById(int id);
	
}
