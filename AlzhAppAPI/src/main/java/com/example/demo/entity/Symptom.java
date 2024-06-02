package com.example.demo.entity;

import java.sql.Date;
import java.sql.Time;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Symptom {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "Type can not be empty")
	@Length(min = 2, max = 20, message = "Type length must be between 2 and 20 characters")
	private String type;

	@NotBlank(message = "Description can not be empty")
	@Length(min = 5, max = 100, message = "Description length must be between 5 and 100 characters")
	private String description;

	@NotNull(message = "Date can not be empty")
	private Date date;

	@NotNull(message = "Hour can not be empty")
	private Time hour;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	@JsonIgnore
	private Patient patient;
}
