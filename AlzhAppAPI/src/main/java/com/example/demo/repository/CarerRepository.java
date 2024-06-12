package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Carer;
import com.example.demo.entity.Patient;

@Repository("carerRepository")
public interface CarerRepository extends JpaRepository<Carer, Serializable> {
	public abstract Carer findById(int id);
	public abstract Carer findByUsername(String passportID);
    List<Carer> findByPatientsCare(Patient patient);
    Carer findByPatientsCare_Passportid(String passportId);
}
