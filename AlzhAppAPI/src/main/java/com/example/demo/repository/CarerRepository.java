package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Carer;

@Repository("carerRepository")
public interface CarerRepository extends JpaRepository<Carer, Serializable> {
	public abstract Carer findById(int id);
	public abstract Carer findByUsername(String passportID);
}
