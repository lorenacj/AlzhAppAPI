package com.example.demo.model;

import java.util.List;

import com.example.demo.entity.Carer;
import com.example.demo.entity.Patient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MedicineModel {
	private int id;
	private String name;
	private String description;
	private String usage;
	private int howoften;
	private int howmanydays;
	private boolean deleted;
	private List<Carer> carers;
	private List<Patient> patients;
}
