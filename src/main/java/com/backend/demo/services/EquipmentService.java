package com.backend.demo.services;

import com.backend.demo.exceptions.EquipmentNotFoundException;
import com.backend.demo.models.Company;
import com.backend.demo.models.Equipment;
import com.backend.demo.repos.CompanyRepository;
import com.backend.demo.repos.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final CompanyRepository companyRepository;

    @Autowired

    public EquipmentService(EquipmentRepository equipmentRepository, CompanyRepository companyRepository) {
        this.equipmentRepository = equipmentRepository;
        this.companyRepository = companyRepository;
    }

    public Equipment addEquipmentToCompany(Equipment equipment, Long company_id){
       Equipment equipmentFromDb = equipmentRepository.findByLocation(equipment.getLocation());
       Company company = companyRepository.findById(company_id).get();
       if (equipmentFromDb != null){
           return equipmentFromDb;
       } else {
           Equipment newEquipment = new Equipment();
           newEquipment.setName(equipment.getName());
           newEquipment.setLocation(equipment.getLocation());
           newEquipment.setCompany(company);
           equipmentRepository.save(newEquipment);
           return newEquipment;
       }
    }

    public Equipment loadDeviceByLocation(String location) throws EquipmentNotFoundException {
        return equipmentRepository.findByLocation(location);
    }


    public List<Equipment> findByCompany(Company company){
        return equipmentRepository.findByCompany(company);
    }
}
