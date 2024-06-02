package com.example.demo.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Medicine {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "Name can not be empty")
	@Length(min = 5, max = 50, message = "Name length must be between 5 and 50 characters")
	private String name;

	@NotBlank(message = "Description can not be empty")
	@Length(min = 5, max = 100, message = "Description length must be between 5 and 100 characters")
	private String description;

	@NotBlank(message = "Use can not be empty")
	@Length(min = 5, max = 100, message = "Use length must be between 5 and 100 characters")
	@Column(name = "\"usage\"") // Escapar el nombre de la columna
	private String usage;

	@NotNull(message = "This field can not be empty. Enter how many hours the medication is taken.")
	private int howoften;

	@NotNull(message = "This field can not be empty. Enter how many days the treatment lasts")
	private int howmanydays;

	private boolean deleted;

	@ManyToMany(mappedBy = "medicines")
	private List<Carer> carers;

	@ManyToMany(mappedBy = "medicines")
	@JsonIgnore
	private List<Patient> patients;

	@Override
	public String toString() {
		return "Medicine [id=" + id + ", name=" + name + ", description=" + description + ", usage=" + usage
				+ ", howoften=" + howoften + ", howmanydays=" + howmanydays + ", deleted=" + deleted + "]";
	}

}
