package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FamilyUnitModel {
	private int id;
	private String code;
	private int patientId;
}
