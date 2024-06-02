package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Medicine;
import com.example.demo.model.MedicineModel;

public interface MedicineService {

	public abstract Medicine createMedicine(MedicineModel medicineModel);
    
    public abstract Medicine updateMedicine(MedicineModel medicineModel);
    
    public abstract int deleteMedicine(int idMedicine);
    
    public abstract List<MedicineModel> listAllMedicines();

    public abstract Medicine transformMedicine(MedicineModel medicineModel);
    
    public abstract MedicineModel transformMedicine(Medicine medicine);
}
