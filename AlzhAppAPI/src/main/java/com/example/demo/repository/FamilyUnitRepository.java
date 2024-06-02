package com.example.demo.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.FamilyUnit;

@Repository("familyUnitRepository")
public interface FamilyUnitRepository extends JpaRepository<FamilyUnit, Serializable> {
	public abstract FamilyUnit findById(int id);
	
	public abstract FamilyUnit findByCode(String code);
}
