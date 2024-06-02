package com.example.demo.model;

import java.sql.Date;
import java.util.List;

import com.example.demo.entity.Carer;
import com.example.demo.entity.Event;
import com.example.demo.entity.Medicine;
import com.example.demo.entity.Symptom;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientModel {
	private int id;
	private String name;
	private String lastname;
	private Date birthdate;
	private int height;
	private int weight;
	private String disorder;
	private String passportId;
	private boolean enabled;
	private boolean deleted;
	private int familyUnitId;
	private List<Carer> carerIds;
	private List<Medicine> medicineIds;
	private List<Event> eventIds;
	private List<Symptom> symptomIds;
}
