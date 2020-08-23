package com.backend.demo.services;

import com.backend.demo.exceptions.EquipmentNotFoundException;
import com.backend.demo.models.Company;
import com.backend.demo.models.Equipment;
import com.backend.demo.repos.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    private EquipmentRepository equipmentRepository;

    @Autowired

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public Equipment addEquipment(Equipment equipment){
       Equipment equipmentFromDb = equipmentRepository.findByLocation(equipment.getLocation());
       if (equipmentFromDb != null){
           return equipmentFromDb;
       } else {
           Equipment newEquipment = new Equipment();
           newEquipment.setName(equipment.getName());
           newEquipment.setLocation(equipment.getLocation());
           //newEquipment.setCompany(equipment.getCompany());
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
