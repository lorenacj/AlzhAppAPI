package com.example.demo.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Medicine;
import com.example.demo.model.MedicineModel;
import com.example.demo.repository.MedicineRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.service.MedicineService;

@Service("medicineService")
public class MedicineServiceImpl implements MedicineService {

    @Autowired
	@Qualifier("medicineRepository")
    private MedicineRepository medicineRepository;
    
    @Autowired
	@Qualifier("patientRepository")
	private PatientRepository patientRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Medicine createMedicine(MedicineModel medicineModel) {
        Medicine medicine = transformMedicine(medicineModel);
        
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine updateMedicine(MedicineModel medicineModel) {
        Medicine medicine = transformMedicine(medicineModel);
        return medicineRepository.save(medicine);
    }

    @Override
    public int deleteMedicine(int idMedicine) {
        medicineRepository.deleteById(idMedicine);
        return idMedicine;
    }

    @Override
    public List<MedicineModel> listAllMedicines() {
        return medicineRepository.findAll().stream()
                .map(this::transformMedicine)
                .collect(Collectors.toList());
    }

    @Override
    public Medicine transformMedicine(MedicineModel medicineModel) {
        if (medicineModel == null) {
            return null;
        }
        return modelMapper.map(medicineModel, Medicine.class);
    }

    @Override
    public MedicineModel transformMedicine(Medicine medicine) {
        if (medicine == null) {
            return null;
        }
        return modelMapper.map(medicine, MedicineModel.class);
    }
}