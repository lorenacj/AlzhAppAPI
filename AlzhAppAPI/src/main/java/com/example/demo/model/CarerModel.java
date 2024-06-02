package com.example.demo.model;

import java.util.List;

import com.example.demo.entity.Medicine;
import com.example.demo.entity.Patient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarerModel {
	private int id;
	private String name;
	private String lastname;
	private String username;
	private String password;
	private String telephone;
	private int idFamilyUnit;
	private String role;
	private boolean enabled;
	private List<Patient> patientIds; 
	private List<Medicine> medicineIds;
}
