package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Medicine;

@Repository("medicineRepository")
public interface MedicineRepository extends JpaRepository<Medicine, Serializable> {
	public abstract Medicine findById(int id);

}
