package com.example.demo.model;

import com.example.demo.entity.Patient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FamilyUnitModel {
	private int id;
	private String code;
	private Patient patientId;
}
