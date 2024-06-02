package com.example.demo.model;

import java.sql.Date;
import java.sql.Time;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventModel {
	private int id;
    private String name;
    private String type;
    private String description;
    private String status;
    private boolean deleted;
    private Date initialDate;
    private Date finalDate;
    private Time initialHour;
    private Time finalHour;
    private int patientId;
}
