package com.example.demo.model;

import java.sql.Date;
import java.sql.Time;

import com.example.demo.entity.Patient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SymptomModel {
	private int id;
	private String type;
	private String description;
	private Date date;
	private Time hour;
	private Patient patient;
}
