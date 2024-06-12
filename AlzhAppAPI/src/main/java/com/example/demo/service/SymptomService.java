package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Patient;
import com.example.demo.entity.Symptom;
import com.example.demo.model.SymptomModel;

public interface SymptomService {

	public abstract Symptom createSymptom(SymptomModel symptom, Patient patient);
    
    public abstract Symptom updateSymptom(SymptomModel symptom);
    
    public abstract int deleteSymptom(int idSymptom);
    
    public abstract List<SymptomModel> listAllSymptoms(); 

    public abstract List<Symptom> listSymptomsByPatient(Patient patient);

    public abstract Symptom transformSymptom(SymptomModel symptomModel);
    
    public abstract SymptomModel transformSymptom(Symptom symptom);

	public abstract void remove(List<Symptom> symptoms);
}
